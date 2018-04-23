package com.conan.console.server.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.UserAuth;
import com.conan.console.server.entity.UserInfo;
import com.conan.console.server.entity.UserRemain;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.UserAuthMapper;
import com.conan.console.server.mapper.UserInfoMapper;
import com.conan.console.server.mapper.UserRemainMapper;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class UserService {

	@Autowired
	UserInfoMapper userInfoMapper;

	@Autowired
	UserAuthMapper userAuthMapper;

	@Autowired
	UserRemainMapper userRemainMapper;

	@Transactional
	public void registerUser(String user_phone, String user_passwd) {
		String uuid = UUID.randomUUID().toString();// 生成唯一主键
		Date date = new Date();// 生成创建时间
		UserInfo userInfo = new UserInfo();// 初始化用户信息
		userInfo.setId(uuid);
		userInfo.setPhone_no(user_phone);
		userInfo.setCreated_at(date);

		UserAuth userAuth = new UserAuth();// 初始化用户权限信息
		userAuth.setId(uuid);
		userAuth.setHashed_passwd(user_passwd);
		userAuth.setUser_info_id(uuid);
		userAuth.setCreated_at(date);

		UserRemain userRemain = new UserRemain();// 初始化用户账户余额信息
		userRemain.setId(uuid);
		userRemain.setUser_info_id(uuid);
		userRemain.setCreated_at(date);
		userRemain.setGold_amount(ConanApplicationConstants.INIT_GOLD_AMOUNT);
		userRemain.setGold_coupon(ConanApplicationConstants.INIT_GOLD_COUPON);

		try {
			userInfoMapper.insertSelective(userInfo);// 保存用户信息
			userAuthMapper.insertSelective(userAuth);// 保存用户权限信息
			userRemainMapper.insertSelective(userRemain);// 保存用户账户月信息
		} catch (DuplicateKeyException e) {
			// TODO: handle exception
			throw new ConanException(ConanExceptionConstants.USER_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_EXISTS_EXCEPTION_HTTP_STATUS);
		}
	}

	@Transactional
	public JSONObject getUserInfo(String user_phone, String user_passwd) {
		UserInfo userInfo = userInfoMapper.selectByPhoneNo(user_phone);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_LOGIN_EXCEPTION_CODE,
					ConanExceptionConstants.USER_LOGIN_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_LOGIN_EXCEPTION_HTTP_STATUS);
		}
		UserAuth userAuth = userAuthMapper.selectByPrimaryKey(userInfo.getId());// 用户信息 用户权限 用户金额 所使用的ID 均为同一ID
		if (userAuth == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		if (!user_passwd.equals(userAuth.getHashed_passwd())) {
			throw new ConanException(ConanExceptionConstants.USER_LOGIN_EXCEPTION_CODE,
					ConanExceptionConstants.USER_LOGIN_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_LOGIN_EXCEPTION_HTTP_STATUS);
		}

		UserRemain userRemain = userRemainMapper.selectByPrimaryKey(userInfo.getId());// 用户信息 用户权限 用户金额 所使用的ID 均为同一ID
		if (userRemain == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		JSONObject resultJsonObject = new JSONObject();
		JSONObject userInfoJsonObject = new JSONObject();
		JSONObject userRemainJsonObject = new JSONObject();
		userRemainJsonObject = (JSONObject) JSON.toJSON(userRemain);
		userRemainJsonObject.put("remain_info_id", userRemain.getId());
		int scan_cnt = (int) (userRemain.getGold_amount() + userRemain.getGold_coupon());// 直接丢掉小数部分
		userRemainJsonObject.put("scan_cnt", scan_cnt);
		userRemainJsonObject.remove("id");
		userRemainJsonObject.remove("user_info_id");

		userInfoJsonObject = (JSONObject) JSON.toJSON(userInfo);
		userInfoJsonObject.put("user_info_id", userInfo.getId());
		userInfoJsonObject.put("user_remain_info", userRemainJsonObject);
		userInfoJsonObject.remove("id");

		resultJsonObject = userInfoJsonObject;

		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject getUserInfo(String user_id) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user_id);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}

		UserRemain userRemain = userRemainMapper.selectByPrimaryKey(user_id);// 用户信息 用户权限 用户金额 所使用的ID 均为同一ID
		if (userRemain == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		JSONObject resultJsonObject = new JSONObject();
		JSONObject userInfoJsonObject = new JSONObject();
		JSONObject userRemainJsonObject = new JSONObject();
		userRemainJsonObject = (JSONObject) JSON.toJSON(userRemain);
		userRemainJsonObject.put("remain_info_id", userRemain.getId());
		int scan_cnt = (int) (userRemain.getGold_amount() + userRemain.getGold_coupon());// 直接丢掉小数部分
		userRemainJsonObject.put("scan_cnt", scan_cnt);
		userRemainJsonObject.remove("id");
		userRemainJsonObject.remove("user_info_id");

		userInfoJsonObject = (JSONObject) JSON.toJSON(userInfo);
		userInfoJsonObject.put("user_info_id", userInfo.getId());
		userInfoJsonObject.put("user_remain_info", userRemainJsonObject);
		userInfoJsonObject.remove("id");

		resultJsonObject = userInfoJsonObject;

		return resultJsonObject;
	}
	
	@Transactional
	public void updateUserPhoto(String user_id,String photo_url) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user_id);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		userInfo.setUser_photo(photo_url);
		userInfo.setUpdated_at(new Date());
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}
	
	@Transactional
	public void updateUserNick(String user_id,String nick_name) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user_id);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		userInfo.setNick_name(nick_name);;
		userInfo.setUpdated_at(new Date());
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}
	
	@Transactional
	public void updateUserPaasword(String user_id,String old_passwd,String new_passwd) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user_id);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		UserAuth userAuth = userAuthMapper.selectByPrimaryKey(userInfo.getId());// 用户信息 用户权限 用户金额 所使用的ID 均为同一ID
		if (userAuth == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		if(!old_passwd.equals(userAuth.getHashed_passwd())) {
			throw new ConanException(ConanExceptionConstants.USER_PASSWD_VALIDATED_EXCEPTION_CODE,
					ConanExceptionConstants.USER_PASSWD_VALIDATED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_PASSWD_VALIDATED_EXCEPTION_HTTP_STATUS);
		}
		
		userAuth.setHashed_passwd(new_passwd);
		userAuth.setUpdated_at(new Date());
		userAuthMapper.updateByPrimaryKeySelective(userAuth);
	}
	
	@Transactional
	public void resetUserPaasword(String user_phone,String new_passwd) {
		UserInfo userInfo = userInfoMapper.selectByPhoneNo(user_phone);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		
		UserAuth userAuth = userAuthMapper.selectByPrimaryKey(userInfo.getId());// 用户信息 用户权限 用户金额 所使用的ID 均为同一ID
		if (userAuth == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		userAuth.setHashed_passwd(new_passwd);
		userAuth.setUpdated_at(new Date());
		userAuthMapper.updateByPrimaryKeySelective(userAuth);
	}
	
	@Transactional
	public void updateUserPhone(String user_id,String new_phone) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user_id);
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		userInfo.setPhone_no(new_phone);
		userInfo.setUpdated_at(new Date());
	}
}
