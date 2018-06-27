package com.conan.console.server.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.PageInfo;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.entity.master.Group;
import com.conan.console.server.entity.master.GroupDetail;
import com.conan.console.server.entity.master.GroupMember;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.mapper.master.GroupDetailMapper;
import com.conan.console.server.mapper.master.GroupMapper;
import com.conan.console.server.mapper.master.GroupMemberMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.parameter.QueryGroupDetailParameters;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;
import com.conan.console.server.utils.ConanUtils;

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
	
	@Autowired
	private GroupDetailMapper groupDetailMapper;
	
	@Autowired
	private MinioService minioService;
	
	
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
			throw new ConanException(ConanExceptionConstants.GROUP_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_EXISTS_EXCEPTION_HTTP_STATUS);
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
		
		try {
			groupMapper.updateByPrimaryKeySelective(group);
		} catch (DuplicateKeyException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ConanException(ConanExceptionConstants.GROUP_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_EXISTS_EXCEPTION_HTTP_STATUS);
		}
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
	
	@Transactional
	public void deleteFromGroup(String user_info_id,String delete_from_group_id) {
		
		Group group = groupMapper.selectByPrimaryKey(delete_from_group_id);
		
		if (group == null || !group.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		groupMemberMapper.deleteByGroupId(delete_from_group_id);
	}
	
	@Transactional
	public void deleteFromGroup(String user_info_id,String[] detection_account_id,String delete_from_group_id) {
		
		Group group = groupMapper.selectByPrimaryKey(delete_from_group_id);
		
		if (group == null || !group.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}

		List<String> detectionAccountList = new ArrayList<>();
		Collections.addAll(detectionAccountList,detection_account_id);
		groupMemberMapper.deleteByGroupIdAndDetectionList(delete_from_group_id, detectionAccountList);
	}
	
	@Transactional
	public void deleteFromGroup(String user_info_id,Map<String,Object> query_params,String delete_from_group_id) {
		UserGetScanHistoryParameters userGetScanHistoryParameters = new UserGetScanHistoryParameters();
		userGetScanHistoryParameters.setScan_account(query_params.get("account_name").toString());
		userGetScanHistoryParameters.setScan_date_end(query_params.get("scan_time_end").toString());
		userGetScanHistoryParameters.setScan_status(Integer.parseInt(query_params.get("scan_status").toString()));
		userGetScanHistoryParameters.setScan_date_start(query_params.get("scan_time_end").toString());
		
		List<DetectionAccount> detectionAccountList= detectionAccountMapper.selectByUserGetScanHistoryAllParameters(userGetScanHistoryParameters, user_info_id);
		if(detectionAccountList!=null && detectionAccountList.size()>0) {
			List<String> detectionAccountStrList = new ArrayList<>();
			for(DetectionAccount detectionAccount:detectionAccountList) {
				detectionAccountStrList.add(detectionAccount.getId());
				if(detectionAccountStrList.size()==500) {
					groupMemberMapper.deleteByGroupIdAndDetectionList(delete_from_group_id, detectionAccountStrList);
					detectionAccountStrList.clear();
				}
			}
			
			if(detectionAccountStrList.size()>0) {
				groupMemberMapper.deleteByGroupIdAndDetectionList(delete_from_group_id, detectionAccountStrList);
			}
			
		}
	}
	 
	@Transactional
	public JSONObject queryGroupDetail(String user_info_id,QueryGroupDetailParameters queryGroupDetailParameters) {
		JSONObject resultJsonObject = new JSONObject();
		List<GroupDetail> groupDetailList = groupDetailMapper.selectByQueryGroupDetailParameters(user_info_id,queryGroupDetailParameters, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = groupDetailMapper.selectByQueryGroupDetailParametersTotal(user_info_id,queryGroupDetailParameters);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(queryGroupDetailParameters.getPageNo());
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		resultJsonObject.put("page_info", pageInfo);
		resultJsonObject.put("records", groupDetailList);
		
		
		int totalDanger = groupDetailMapper.selectByQueryGroupDetailParametersScanStatus(user_info_id,1,queryGroupDetailParameters);
		int totalDoubtful = groupDetailMapper.selectByQueryGroupDetailParametersScanStatus(user_info_id,1,queryGroupDetailParameters);
		
		JSONObject statsJsobObject = new JSONObject();
		statsJsobObject.put("total", total);
		statsJsobObject.put("danger_accounts_num", totalDanger);
		statsJsobObject.put("doubtful_accounts_num", totalDoubtful);
		statsJsobObject.put("danger_account_percent", ConanUtils.fix2(totalDanger/total*100));
		resultJsonObject.put("stats", statsJsobObject);
		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject queryGroupList(String user_info_id,int pageNo) {
		JSONObject resultJsonObject = new JSONObject();
		List<Group> groupList = groupMapper.selectByUserInfoId(user_info_id, pageNo, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = groupMapper.selectByUserInfoIdTotal(user_info_id);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(pageNo);
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		resultJsonObject.put("page_info", pageInfo);
		JSONArray jsonArray = new JSONArray();
		for(Group group:groupList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", group.getId());
			jsonObject.put("created_at", group.getCreated_at());
			jsonObject.put("updated_at", group.getUpdated_at());
			jsonObject.put("group_name", group.getGroup_name());
			jsonObject.put("group_comment", group.getGroup_comment());
			
			List<GroupDetail> groupDetailList  = groupDetailMapper.selectByUserInfoIdAndGroupId(user_info_id, group.getId());
			if(groupDetailList==null||groupDetailList.isEmpty()) {
				jsonObject.put("first_detection_time", null);
				jsonObject.put("last_detection_time", null);
				jsonObject.put("danger_percent", 0);
			}else {
				Date minDate = new Date(1000,1,1);
				Date maxDate = new Date(1,1,1);
				int danger = 0;
				for(GroupDetail groupDetail:groupDetailList) {
					Date date = groupDetail.getCreated_at();
					
					if(minDate.after(date)) {
						minDate = date;
					}
					
					if(maxDate.before(date)) {
						maxDate = date;
					}
					if(groupDetail.getAccount_score()>=80) {
						danger ++;
					}
				}
				jsonObject.put("first_detection_time", minDate);
				jsonObject.put("last_detection_time", maxDate);
				jsonObject.put("danger_percent", ConanUtils.fix2(danger/groupDetailList.size()*100));
			}
			jsonArray.add(jsonObject);
		}
		resultJsonObject.put("groups", jsonArray);
		return resultJsonObject;
	}
	@Transactional
	public JSONObject exportGroups(String user_info_id,String[] group_ids) {
		JSONObject resultJsonObject = new JSONObject();
		SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook();
		for(String group_id:group_ids) {
			Group group = groupMapper.selectByPrimaryKey(group_id);
			if (group == null || !group.getUser_info_id().equals(user_info_id)) {
				try {
					if (xssfWorkbook != null) {
						xssfWorkbook.close();
						xssfWorkbook.dispose();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				throw new ConanException(ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.GROUP_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}
			List<GroupDetail> groupDetailList  = groupDetailMapper.selectByUserInfoIdAndGroupId(user_info_id, group.getId());
			
			SXSSFSheet xssfSheet = xssfWorkbook.createSheet(group.getGroup_name());
			SXSSFRow XSSFRow0 = xssfSheet.createRow(0);
			SXSSFCell XSSFCell0_0 = XSSFRow0.createCell(0);
			XSSFCell0_0.setCellValue("账号名称");
			SXSSFCell XSSFCell0_1 = XSSFRow0.createCell(1);
			XSSFCell0_1.setCellValue("账号状态");
			SXSSFCell XSSFCell0_2 = XSSFRow0.createCell(2);
			XSSFCell0_2.setCellValue("分数");
			SXSSFCell XSSFCell0_3 = XSSFRow0.createCell(3);
			XSSFCell0_3.setCellValue("账号基本分数");
			SXSSFCell XSSFCell0_4 = XSSFRow0.createCell(4);
			XSSFCell0_4.setCellValue("账号标签属性");
			SXSSFCell XSSFCell0_5 = XSSFRow0.createCell(5);
			XSSFCell0_5.setCellValue("最近行为轨迹");
			SXSSFCell XSSFCell0_6 = XSSFRow0.createCell(6);
			XSSFCell0_6.setCellValue("交易活跃度");
			SXSSFCell XSSFCell0_7 = XSSFRow0.createCell(7);
			XSSFCell0_7.setCellValue("账号历史");
			SXSSFCell XSSFCell0_8 = XSSFRow0.createCell(8);
			XSSFCell0_8.setCellValue("检测日期");
			
			CreationHelper createHelper = xssfWorkbook.getCreationHelper();
			CellStyle cellDateStyle = xssfWorkbook.createCellStyle();
			cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd hh:mm:ss"));
			
			for (int i = 1; i <= groupDetailList.size(); i++) {
				GroupDetail groupDetail = groupDetailList.get(i - 1);
				SXSSFRow SXSSFRow = xssfSheet.createRow(i);
				SXSSFCell cell0 = SXSSFRow.createCell(0);
				SXSSFCell cell1 = SXSSFRow.createCell(1);
				SXSSFCell cell2 = SXSSFRow.createCell(2);
				SXSSFCell cell3 = SXSSFRow.createCell(3);
				SXSSFCell cell4 = SXSSFRow.createCell(4);
				SXSSFCell cell5 = SXSSFRow.createCell(5);
				SXSSFCell cell6 = SXSSFRow.createCell(6);
				SXSSFCell cell7 = SXSSFRow.createCell(7);
				SXSSFCell cell8 = SXSSFRow.createCell(8);
				cell8.setCellStyle(cellDateStyle);

				cell0.setCellValue(groupDetail.getAccount_name());
				Double score = groupDetail.getAccount_score();
				String scoreMessage = "";
				if (score >= 80) {
					scoreMessage = ConanApplicationConstants.DANGER;
				} else if (score < 80 && score >= 60) {
					scoreMessage = ConanApplicationConstants.SUSPICIOUS;
				} else if (score < 60 && score >= 0) {
					scoreMessage = ConanApplicationConstants.NOT_MATCH_MESSAGE;
				} else {
					scoreMessage = ConanApplicationConstants.NOT_EXIST_MESSAGE;
				}
				cell1.setCellValue(scoreMessage);
				cell2.setCellValue(score);
				cell3.setCellValue(groupDetail.getDetail_score0());
				cell4.setCellValue(groupDetail.getDetail_score1());
				cell5.setCellValue(groupDetail.getDetail_score2());
				cell6.setCellValue(groupDetail.getDetail_score3());
				cell7.setCellValue(groupDetail.getDetail_score4());
				cell8.setCellValue(groupDetail.getCreated_at());
			}
			
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			xssfWorkbook.write(os);
			byte[] content = os.toByteArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			String objectName = user_info_id + "/" + ymd + "/" + UUID.randomUUID().toString() + ".xlsx";
			is = new ByteArrayInputStream(content);
			minioService.uploadFile(is, objectName, "application/octet-stream");
			resultJsonObject.put("export_link", minioService.presignedGetObject(objectName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (xssfWorkbook != null) {
					xssfWorkbook.close();
					xssfWorkbook.dispose();
				}
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultJsonObject;
	}
}
