package com.conan.console.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.PageInfo;
import com.conan.console.server.entity.master.CostRecord;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.entity.master.QueryCost;
import com.conan.console.server.entity.master.QueryRecharge;
import com.conan.console.server.entity.master.RechargeBill;
import com.conan.console.server.entity.master.UserBill;
import com.conan.console.server.entity.master.UserInfo;
import com.conan.console.server.entity.master.UserRemain;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.CostRecordMapper;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.mapper.master.QueryCostMapper;
import com.conan.console.server.mapper.master.QueryRechargeMapper;
import com.conan.console.server.mapper.master.RechargeBillMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.mapper.master.UserInfoMapper;
import com.conan.console.server.mapper.master.UserRemainMapper;
import com.conan.console.server.parameter.QueryCostListParameters;
import com.conan.console.server.parameter.QueryRechargeListParameters;
import com.conan.console.server.parameter.QueryUserListParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;
import com.conan.console.server.utils.ConanUtils;

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
	
	@Autowired
	private QueryRechargeMapper queryRechargeMapper;
	
	@Autowired
	private QueryCostMapper queryCostMapper;
	
	@Autowired
	private DetectionAccountMapper detectionAccountMapper;
	
	@Autowired
	private CostRecordMapper costRecordMapper;
	
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
		List<QueryRecharge> queryRechargeList = queryRechargeMapper.selectByQueryRechargeListParameters(queryRechargeListParameters, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = queryRechargeMapper.selectByQueryRechargeListParametersTotal(queryRechargeListParameters);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(queryRechargeListParameters.getPageNo());
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		for(QueryRecharge queryRecharge:queryRechargeList) {
			queryRecharge.setPhoto(minioService.presignedGetObject(queryRecharge.getPhoto()));
		}
		
		resultJsonObject.put("page_info", pageInfo);
		resultJsonObject.put("recharges", queryRechargeList);
		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject queryCostList(QueryCostListParameters queryCostListParameters) {
		JSONObject resultJsonObject = new JSONObject();
		List<QueryCost> queryCostList = queryCostMapper.selectByQueryCostListParameters(queryCostListParameters, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = queryCostMapper.selectByQueryCostListParametersTotal(queryCostListParameters);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(queryCostListParameters.getPageNo());
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		resultJsonObject.put("page_info", pageInfo);
		resultJsonObject.put("costs", queryCostList);
		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject queryCostDetail(String cost_id,int pageNo) {
		JSONObject resultJsonObject = new JSONObject();
		List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectByRecordId(cost_id, pageNo, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = detectionAccountMapper.selectByRecordIdTotal(cost_id);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(pageNo);
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		
		resultJsonObject.put("page_info", pageInfo);
		resultJsonObject.put("accounts", detectionAccountList);
		return resultJsonObject;
	}
	@Transactional
	public JSONObject getStats() {
		Date td = ConanUtils.getStartTime();
		long new_recharge_cnt =rechargeBillMapper.selectNewRechargeCntTotal();
		long today_register_cnt = userInfoMapper.selectTdRegisterCntTotal(td);
		long today_login_cnt = userInfoMapper.selectTdLoginCntTotal(td);
		long total_registered_cnt = userInfoMapper.selectTtRegisteredCntTotal();
		long today_recharge_agree_cnt =rechargeBillMapper.selectTdRechargeAgreeCntTotal(td);
		long today_recharge_agree_amount = rechargeBillMapper.selectTdRechargeAgreeAmountTotal(td);
		long today_recharge_agree_gold = rechargeBillMapper.selectTdRechargeAgreeGoldTotal(td);
		long total_recharge_agree_amount = rechargeBillMapper.selectTtRechargeAgreeAmountTotal();
		long total_recharge_agree_gold = rechargeBillMapper.selectTtRechargeAgreeGoldTotal();
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("new_recharge_cnt", new_recharge_cnt);
		resultJsonObject.put("today_register_cnt", today_register_cnt);
		resultJsonObject.put("today_login_cnt", today_login_cnt);
		resultJsonObject.put("total_registered_cnt", total_registered_cnt);
		resultJsonObject.put("today_recharge_agree_cnt", today_recharge_agree_cnt);
		resultJsonObject.put("today_recharge_agree_amount", today_recharge_agree_amount);
		resultJsonObject.put("today_recharge_agree_gold", today_recharge_agree_gold);
		resultJsonObject.put("total_recharge_agree_amount", total_recharge_agree_amount);
		resultJsonObject.put("total_recharge_agree_gold", total_recharge_agree_gold);
		return resultJsonObject;
	}
	
	
	@Transactional
	public JSONObject getUserStats(String range_date_start,String range_date_end) {
		long range_register_users_cnt = userInfoMapper.userRegister(range_date_start, range_date_end);
		long total_register_users_cnt = userInfoMapper.selectTtRegisteredCntTotal();
		long range_login_users_cnt = userInfoMapper.userLogin(range_date_start, range_date_end);
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("range_register_users_cnt", range_register_users_cnt);
		resultJsonObject.put("total_register_users_cnt", total_register_users_cnt);
		resultJsonObject.put("range_login_users_cnt", range_login_users_cnt);
		return resultJsonObject;
	}
	
	
	@Transactional
	public JSONObject getRechargeStats(String range_date_start,String range_date_end) {
		long range_recharge_users_cnt = rechargeBillMapper.rechargeUser(range_date_start, range_date_end);
		long range_recharge_records_cnt = rechargeBillMapper.rechargeItems(range_date_start, range_date_end);
		Double range_recharge_rmb_amount = rechargeBillMapper.rechargeRmb(range_date_start, range_date_end);
		Double total_recharge_rmb_amount = rechargeBillMapper.totalRechargeRmb();
		Double range_recharge_gold_amount = rechargeBillMapper.rechargeGold(range_date_start, range_date_end);
		Double total_recharge_gold_amount = rechargeBillMapper.totalRechargeGold();
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("range_recharge_users_cnt", range_recharge_users_cnt);
		resultJsonObject.put("range_recharge_records_cnt", range_recharge_records_cnt);
		resultJsonObject.put("range_recharge_rmb_amount", range_recharge_rmb_amount);
		resultJsonObject.put("total_recharge_rmb_amount", total_recharge_rmb_amount);
		resultJsonObject.put("range_recharge_gold_amount", range_recharge_gold_amount);
		resultJsonObject.put("total_recharge_gold_amount", total_recharge_gold_amount);
		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject getSingleScanStats(String range_date_start,String range_date_end) {
		long range_single_scan_users_cnt = costRecordMapper.singleScanUser(range_date_start, range_date_end);
		long range_single_scan_records_cnt = costRecordMapper.singleScanRecords(range_date_start, range_date_end);
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("range_single_scan_users_cnt", range_single_scan_users_cnt);
		resultJsonObject.put("range_single_scan_records_cnt", range_single_scan_records_cnt);
		return resultJsonObject;
	}
	
	@Transactional
	public JSONObject getBatchScanStats(String range_date_start,String range_date_end) {
		long range_batch_scan_users_cnt = costRecordMapper.batchScanUser(range_date_start, range_date_end);
		long range_batch_scan_records_cnt = costRecordMapper.batchScanRecords(range_date_start, range_date_end);
		long range_batch_scan_accounts_cnt = detectionAccountMapper.batchScanAccounts(range_date_start, range_date_end);
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("range_batch_scan_users_cnt", range_batch_scan_users_cnt);
		resultJsonObject.put("range_batch_scan_records_cnt", range_batch_scan_records_cnt);
		resultJsonObject.put("range_batch_scan_accounts_cnt", range_batch_scan_accounts_cnt);
		return resultJsonObject;
	}
	
	
	
}
