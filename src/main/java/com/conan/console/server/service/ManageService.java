package com.conan.console.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.PageInfo;
import com.conan.console.server.entity.master.RechargeBill;
import com.conan.console.server.entity.master.UserBill;
import com.conan.console.server.entity.master.UserInfo;
import com.conan.console.server.entity.master.UserRemain;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.RechargeBillMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.mapper.master.UserInfoMapper;
import com.conan.console.server.mapper.master.UserRemainMapper;
import com.conan.console.server.parameter.QueryRechargeListParameters;
import com.conan.console.server.parameter.QueryUserListParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class ManageService {
	@Autowired
	private RechargeBillMapper rechargeBillMapper;
	
	@Autowired
	private UserRemainMapper userRemainMapper;
	
	@Autowired
	private UserBillMapper userBillMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private MinioService minioService;
	
	@Transactional
	public void handleRechargeReq(String recharge_id,String action,String reason) {
		RechargeBill rechargeBill = rechargeBillMapper.selectByPrimaryKey(recharge_id);
		if (rechargeBill == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}else if(rechargeBill.getRecharge_status().equals("1")) {
			throw new ConanException(ConanExceptionConstants.BILL_EXAMINE_EXCEPTION_CODE,
					ConanExceptionConstants.BILL_EXAMINE_EXCEPTION_MESSAGE,
					ConanExceptionConstants.BILL_EXAMINE_EXCEPTION_HTTP_STATUS);
		}
		
		UserRemain userRemain = userRemainMapper.selectByPrimaryKey(rechargeBill.getUser_info_id());//user_info_id 就是UserRemain id
		if (userRemain == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		UserBill userBill = userBillMapper.selectByPrimaryKey(rechargeBill.getUser_bill_id());
		if (userBill == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		if(action.equals("agree")) {
			rechargeBill.setRecharge_status("1");//审核通过
			rechargeBill.setReason(reason);
			rechargeBill.setUpdated_at(new Date());
			rechargeBill.setVerified_time(new Date());
			rechargeBill.setSuccess_time(new Date());
			rechargeBillMapper.updateByPrimaryKey(rechargeBill);
			
			userRemain.setGold_amount(userRemain.getGold_amount()+rechargeBill.getGold_amount());
			userRemain.setGold_coupon(userRemain.getGold_coupon()+rechargeBill.getGold_coupon());
			userRemain.setUpdated_at(new Date());
			userRemainMapper.updateByPrimaryKey(userRemain);
			
			userBill.setRemain_gold(userRemain.getGold_amount()+userRemain.getGold_coupon());
			userBill.setUpdated_at(new Date());
			userBillMapper.updateByPrimaryKey(userBill);
			
		}else if(action.equals("no")){
			rechargeBill.setRecharge_status("3");//拒绝
			rechargeBill.setReason(reason);
			rechargeBill.setVerified_time(new Date());
			rechargeBill.setSuccess_time(new Date());
			
			rechargeBillMapper.updateByPrimaryKey(rechargeBill);
		}else {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
	}
	@Transactional
	public JSONObject queryUserList(QueryUserListParameters queryUserListParameters) {
		JSONObject resultJsonObject = new JSONObject();
		List<UserInfo> userInfoList = userInfoMapper.selectByQueryUserListParameters(queryUserListParameters, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = userInfoMapper.selectByQueryUserListParametersTotal(queryUserListParameters);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(queryUserListParameters.getPageNo());
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		resultJsonObject.put("page_info", pageInfo);
		JSONArray jsonArray = new JSONArray();
		for(UserInfo userInfo: userInfoList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userInfo.getId());
			jsonObject.put("created_at", userInfo.getCreated_at());
			jsonObject.put("updated_at", userInfo.getUpdated_at());
			jsonObject.put("last_login_at", userInfo.getLast_login_at());
			jsonObject.put("nick_name", userInfo.getNick_name());
			jsonObject.put("phone_no", userInfo.getPhone_no());
			jsonObject.put("activate", userInfo.getActivate());
			jsonObject.put("user_photo", minioService.presignedGetObject(userInfo.getUser_photo()));
			jsonArray.add(jsonObject);
		}
		resultJsonObject.put("users", jsonArray);
		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject queryRechargeList(QueryRechargeListParameters queryRechargeListParameters) {
		JSONObject resultJsonObject = new JSONObject();
		/*List<UserInfo> userInfoList = userInfoMapper.selectByQueryUserListParameters(queryUserListParameters, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = userInfoMapper.selectByQueryUserListParametersTotal(queryUserListParameters);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(queryUserListParameters.getPageNo());
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		resultJsonObject.put("page_info", pageInfo);
		JSONArray jsonArray = new JSONArray();
		for(UserInfo userInfo: userInfoList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userInfo.getId());
			jsonObject.put("created_at", userInfo.getCreated_at());
			jsonObject.put("updated_at", userInfo.getUpdated_at());
			jsonObject.put("last_login_at", userInfo.getLast_login_at());
			jsonObject.put("nick_name", userInfo.getNick_name());
			jsonObject.put("phone_no", userInfo.getPhone_no());
			jsonObject.put("activate", userInfo.getActivate());
			jsonObject.put("user_photo", minioService.presignedGetObject(userInfo.getUser_photo()));
			jsonArray.add(jsonObject);
		}
		resultJsonObject.put("users", jsonArray);*/
		return resultJsonObject;
	}
	
	
	
}
