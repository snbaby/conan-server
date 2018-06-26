package com.conan.console.server.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.master.Group;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.GroupMapper;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class GroupService {
	
	@Autowired
	private GroupMapper groupMapper;
	
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
	
	
}
