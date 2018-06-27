package com.conan.console.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.entity.master.Group;
import com.conan.console.server.entity.master.GroupMember;
import com.conan.console.server.entity.master.UserBill;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.mapper.master.GroupMapper;
import com.conan.console.server.mapper.master.GroupMemberMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class GroupService {
	
	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private GroupMemberMapper groupMemberMapper;
	
	@Autowired
	private DetectionAccountMapper detectionAccountMapper;
	
	@Autowired
	private UserBillMapper userBillMapper;
	
	@Transactional
	public JSONObject createGroup(String user_info_id,String group_name) {
		String uuid = UUID.randomUUID().toString();// 生成唯一主键
		Date date = new Date();//创建时间
		Group group = new Group();
		group.setId(uuid);
		group.setCreated_at(date);
		group.setGroup_comment("备注");
		group.setGroup_name(group_name);
		group.setUser_info_id(user_info_id);
		try {
			groupMapper.insertSelective(group);//保存组信息
		} catch (DuplicateKeyException e) {
			// TODO: handle exception
			throw new ConanException(ConanExceptionConstants.USER_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", uuid);
		jsonObject.put("created_at", date);
		jsonObject.put("updated_at", date);
		jsonObject.put("group_name", group_name);
		jsonObject.put("group_comment", group.getGroup_comment());
		
		return jsonObject;
	}
	
	@Transactional
	public JSONObject modifyGroup(String user_info_id,String group_id,String group_name,String group_comment) {
		Group group = groupMapper.selectByPrimaryKey(group_id);
		if (group == null || !group.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		if(StringUtils.isNoneBlank(group_name)) {
			group.setGroup_name(group_name);
		}
		if(StringUtils.isNoneBlank(group_comment)) {
			group.setGroup_comment(group_comment);
		}
		group.setUpdated_at(new Date());
		groupMapper.updateByPrimaryKeySelective(group);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id",group.getId());
		jsonObject.put("created_at", group.getCreated_at());
		jsonObject.put("updated_at", group.getUpdated_at());
		jsonObject.put("group_name", group.getGroup_name());
		jsonObject.put("group_comment", group.getGroup_comment());
		
		return jsonObject;
	}
	
	@Transactional
	public void deleteGroups(String user_info_id,String[] group_id_array) {
		for(String group_id:group_id_array) {
			Group group = groupMapper.selectByPrimaryKey(group_id);
			if (group == null || !group.getUser_info_id().equals(user_info_id)) {
				throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}
			groupMapper.deleteByPrimaryKey(group_id);
			groupMemberMapper.deleteByGroupId(group_id);
			
		}
	}
	
	@Transactional
	public void addIntoGroupByGroup_id(String user_info_id,String group_id,String target_group_id) {
		
		Group sourceGroup = groupMapper.selectByPrimaryKey(group_id);
		
		Group targetGroup = groupMapper.selectByPrimaryKey(target_group_id);
		
		if (sourceGroup == null || !sourceGroup.getUser_info_id().equals(user_info_id) || targetGroup == null || !targetGroup.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		List<GroupMember> sourceGroupMemberList = groupMemberMapper.selectByGroupId(group_id);
		List<GroupMember> targetGroupMemberList = groupMemberMapper.selectByGroupId(target_group_id);
		List<String> targetDectionList = new ArrayList<>();
		for(GroupMember groupMember:targetGroupMemberList) {
			targetDectionList.add(groupMember.getDetection_id());
		}
		List<GroupMember> resultGroupMemberList = new ArrayList<>();
		for(GroupMember groupMember:sourceGroupMemberList) {
			if(!targetDectionList.contains(groupMember.getDetection_id())) {
				GroupMember resultGroupMember = new GroupMember();
				resultGroupMember.setId(UUID.randomUUID().toString());
				resultGroupMember.setDetection_id(groupMember.getDetection_id());
				resultGroupMember.setGroup_id(target_group_id);
				resultGroupMemberList.add(resultGroupMember);
			}
			if(resultGroupMemberList.size()==500) {
				groupMemberMapper.insertList(resultGroupMemberList);
				resultGroupMemberList.clear();
			}
		}
		if(resultGroupMemberList.size()>0) {
			groupMemberMapper.insertList(resultGroupMemberList);
		}
	}
	
	@Transactional
	public void addIntoGroupByDetection_account_id(String user_info_id,String[] detection_account_id,String target_group_id) {
		for(String detectionId:detection_account_id) {
			DetectionAccount detectionAccount= detectionAccountMapper.selectByPrimaryKey(detectionId);
			if ( detectionAccount == null || !detectionAccount.getUser_info_id().equals(user_info_id)) {
				throw new ConanException(ConanExceptionConstants.DETECTION_ACCOUNT_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.DETECTION_ACCOUNT_NOT_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.DETECTION_ACCOUNT_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}
		}
		
		Group targetGroup = groupMapper.selectByPrimaryKey(target_group_id);
		
		if ( targetGroup == null || !targetGroup.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		List<GroupMember> targetGroupMemberList = groupMemberMapper.selectByGroupId(target_group_id);
		List<String> targetDectionList = new ArrayList<>();
		for(GroupMember groupMember:targetGroupMemberList) {
			targetDectionList.add(groupMember.getDetection_id());
		}
		List<GroupMember> resultGroupMemberList = new ArrayList<>();
		for(String detectionId:detection_account_id) {
			if(!targetDectionList.contains(detectionId)) {
				GroupMember resultGroupMember = new GroupMember();
				resultGroupMember.setId(UUID.randomUUID().toString());
				resultGroupMember.setDetection_id(detectionId);
				resultGroupMember.setGroup_id(target_group_id);
				resultGroupMemberList.add(resultGroupMember);
			}
			if(resultGroupMemberList.size()==500) {
				groupMemberMapper.insertList(resultGroupMemberList);
				resultGroupMemberList.clear();
			}
		}
		if(resultGroupMemberList.size()>0) {
			groupMemberMapper.insertList(resultGroupMemberList);
		}
	}
	
	@Transactional
	public void addIntoGroupByQuery_params(String user_info_id,Map<String,Object> query_params,String target_group_id) {
		UserGetScanHistoryParameters userGetScanHistoryParameters = new UserGetScanHistoryParameters();
		userGetScanHistoryParameters.setScan_account(query_params.get("account_name").toString());
		userGetScanHistoryParameters.setScan_date_end(query_params.get("scan_time_end").toString());
		userGetScanHistoryParameters.setScan_status(Integer.parseInt(query_params.get("scan_status").toString()));
		userGetScanHistoryParameters.setScan_date_start(query_params.get("scan_time_end").toString());
		
		List<DetectionAccount> detectionAccountList= detectionAccountMapper.selectByUserGetScanHistoryAllParameters(userGetScanHistoryParameters, user_info_id);
		Group targetGroup = groupMapper.selectByPrimaryKey(target_group_id);
		
		if ( targetGroup == null || !targetGroup.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		List<GroupMember> targetGroupMemberList = groupMemberMapper.selectByGroupId(target_group_id);
		List<String> targetDectionList = new ArrayList<>();
		for(GroupMember groupMember:targetGroupMemberList) {
			targetDectionList.add(groupMember.getDetection_id());
		}
		List<GroupMember> resultGroupMemberList = new ArrayList<>();
		for(DetectionAccount detectionAccount:detectionAccountList) {
			if(!targetDectionList.contains(detectionAccount.getId())) {
				GroupMember resultGroupMember = new GroupMember();
				resultGroupMember.setId(UUID.randomUUID().toString());
				resultGroupMember.setDetection_id(detectionAccount.getId());
				resultGroupMember.setGroup_id(target_group_id);
				resultGroupMemberList.add(resultGroupMember);
			}
			if(resultGroupMemberList.size()==500) {
				groupMemberMapper.insertList(resultGroupMemberList);
				resultGroupMemberList.clear();
			}
		}
		if(resultGroupMemberList.size()>0) {
			groupMemberMapper.insertList(resultGroupMemberList);
		}
	}
	
	@Transactional
	public void addIntoGroupByBill_id(String user_info_id,String bill_id,String target_group_id) {
		List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectByRecordIdAndyUserInfoId(bill_id, user_info_id);
		Group targetGroup = groupMapper.selectByPrimaryKey(target_group_id);
		
		if ( targetGroup == null || !targetGroup.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		List<GroupMember> targetGroupMemberList = groupMemberMapper.selectByGroupId(target_group_id);
		List<String> targetDectionList = new ArrayList<>();
		for(GroupMember groupMember:targetGroupMemberList) {
			targetDectionList.add(groupMember.getDetection_id());
		}
		List<GroupMember> resultGroupMemberList = new ArrayList<>();
		for(DetectionAccount detectionAccount:detectionAccountList) {
			if(!targetDectionList.contains(detectionAccount.getId())) {
				GroupMember resultGroupMember = new GroupMember();
				resultGroupMember.setId(UUID.randomUUID().toString());
				resultGroupMember.setDetection_id(detectionAccount.getId());
				resultGroupMember.setGroup_id(target_group_id);
				resultGroupMemberList.add(resultGroupMember);
			}
			if(resultGroupMemberList.size()==500) {
				groupMemberMapper.insertList(resultGroupMemberList);
				resultGroupMemberList.clear();
			}
		}
		if(resultGroupMemberList.size()>0) {
			groupMemberMapper.insertList(resultGroupMemberList);
		}
	}
	
	
}
