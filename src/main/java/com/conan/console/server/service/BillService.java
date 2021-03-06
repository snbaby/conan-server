package com.conan.console.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.PageInfo;
import com.conan.console.server.entity.master.CostRecord;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.entity.master.RechargeBill;
import com.conan.console.server.entity.master.UserBill;
import com.conan.console.server.entity.master.UserInfo;
import com.conan.console.server.entity.master.UserRemain;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.CostRecordMapper;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.mapper.master.RechargeBillMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.mapper.master.UserInfoMapper;
import com.conan.console.server.mapper.master.UserRemainMapper;
import com.conan.console.server.parameter.GetBillDetailParameters;
import com.conan.console.server.parameter.UserGetBillParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class BillService {

	@Autowired
	private UserBillMapper userBillMapper;
	@Autowired
	private RechargeBillMapper rechargeBillMapper;
	@Autowired
	private CostRecordMapper costRecordMapper;
	@Autowired
	private DetectionAccountMapper detectionAccountMapper;
	@Autowired
	private UserRemainMapper userRemainMapper;
	@Autowired
	private JsonService jsonService;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private MinioService minioService;

	@Transactional
	public JSONObject getUserBillPages(UserGetBillParameters userGetBillParameters, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		List<UserBill> userBillList = userBillMapper.selectByUserGetBillParameters(userGetBillParameters, user_info_id);

		double recharge_total = 0f;
		double cost_total = 0f;
		JSONArray jsonArray = new JSONArray();
		for (UserBill userBill : userBillList) {
			JSONObject userBillJsonObject = new JSONObject();
			userBillJsonObject.put("bill_id", userBill.getId());
			userBillJsonObject.put("created_at", userBill.getCreated_at());
			userBillJsonObject.put("bill_type", userBill.getBill_type());
			userBillJsonObject.put("bill_remain", userBill.getRemain_gold());
			userBillJsonObject.put("bill_digest", userBill.getBill_digest());
			if (StringUtils.isNotBlank(userBill.getBill_type()) && userBill.getBill_type().equals("1")) {// 充值
				RechargeBill rechargeBill = rechargeBillMapper.selectByPrimaryKey(userBill.getId());// BILL账单 recharge账单
																									// cost账单使用的ID均为同一个ID
				if (rechargeBill == null) {
					throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
				}
				if (rechargeBill.getRecharge_status().equals("1")) {
					recharge_total = recharge_total + rechargeBill.getGold_total();
					userBillJsonObject.put("bill_amount", rechargeBill.getGold_total());
				} else {
					continue;
				}

			} else if (StringUtils.isNotBlank(userBill.getBill_type()) && userBill.getBill_type().equals("2")) {// 支出
				CostRecord costRecord = costRecordMapper.selectByPrimaryKey(userBill.getId());// BILL账单 recharge账单
																								// cost账单使用的ID均为同一个ID
				if (costRecord == null) {
					throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
				}
				cost_total = cost_total + costRecord.getCost_gold();
				userBillJsonObject.put("bill_amount", costRecord.getCost_gold());
			} else {// 异常
				throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}
			jsonArray.add(userBillJsonObject);
		}
		
		List<Object> jsonObjectList = new ArrayList<>();
		int from = (userGetBillParameters.getPageNo() - 1) * ConanApplicationConstants.INIT_PAGE_SIZE;
		int to = userGetBillParameters.getPageNo() * ConanApplicationConstants.INIT_PAGE_SIZE;
		if (jsonArray.size() <= from) {
			//to-do  donothing
		} else if (jsonArray.size() > from && jsonArray.size() < to) {
			jsonObjectList = jsonArray.subList(from, jsonArray.size());
		} else {
			jsonObjectList = jsonArray.subList(from, jsonArray.size());
		}
		resultJsonObject.put("bills", jsonObjectList);

		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(userGetBillParameters.getPageNo());
		pageInfo.setTotal(jsonArray.size());
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		resultJsonObject.put("page_info", pageInfo);
		
		JSONObject statsJsonObject = new JSONObject();
		statsJsonObject.put("recharge_total", recharge_total);
		statsJsonObject.put("cost_total", cost_total);
		resultJsonObject.put("stats", statsJsonObject);
		
		return resultJsonObject;
	}

	@Transactional
	public JSONObject queryUserRecharges(int recharge_status, int pageNo, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		List<RechargeBill> rechargeBillList = rechargeBillMapper.selectByUserIdAndRechargeStatus(recharge_status,
				user_info_id, pageNo, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = rechargeBillMapper.selectByUserIdAndRechargeStatusTotal(recharge_status, user_info_id);

		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user_info_id);// BILL账单 recharge账单
		// cost账单使用的ID均为同一个ID
		if (userInfo == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(pageNo);
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		resultJsonObject.put("page_info", pageInfo);
		JSONArray jsonArray = new JSONArray();
		for (RechargeBill rechargeBill : rechargeBillList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", rechargeBill.getId());
			jsonObject.put("created_at", rechargeBill.getCreated_at());
			jsonObject.put("updated_at", rechargeBill.getUpdated_at());

			jsonObject.put("photo", minioService.presignedGetObject(rechargeBill.getPhoto()));
			jsonObject.put("comment", rechargeBill.getComment());
			jsonObject.put("rmb_amount", rechargeBill.getRmb_amount());
			jsonObject.put("gold_amount", rechargeBill.getGold_amount());
			jsonObject.put("gold_coupon", rechargeBill.getGold_coupon());
			jsonObject.put("gold_total", rechargeBill.getGold_total());
			jsonObject.put("recharge_status", rechargeBill.getRecharge_status());
			jsonObject.put("user_info_id", rechargeBill.getUser_info_id());
			jsonObject.put("phone_no", userInfo.getPhone_no());
			jsonObject.put("verified_time", rechargeBill.getVerified_time());
			jsonObject.put("success_time", rechargeBill.getSuccess_time());
			jsonObject.put("reason", rechargeBill.getReason());
			jsonArray.add(jsonObject);
		}
		resultJsonObject.put("recharges", jsonArray);
		return resultJsonObject;
	}

	@Transactional
	public JSONObject getBillDetail(GetBillDetailParameters getBillDetailParameters, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		UserBill userBill = userBillMapper.selectByPrimaryKey(getBillDetailParameters.getBill_id());
		if (userBill == null || !userBill.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.BILL_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.BILL_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.BILL_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		resultJsonObject.put("bill_id", userBill.getId());
		resultJsonObject.put("created_at", userBill.getCreated_at());
		resultJsonObject.put("bill_type", userBill.getBill_type());
		resultJsonObject.put("bill_remain", userBill.getRemain_gold());
		resultJsonObject.put("bill_digest", userBill.getBill_digest());
		// resultJsonObject.put("bill_status", userBill.getBill_status());
		if (StringUtils.isNotBlank(userBill.getBill_type()) && userBill.getBill_type().equals("1")) {// 充值
			// BILL账单 recharge账单 cost账单使用的ID均为同一个ID
			RechargeBill rechargeBill = rechargeBillMapper.selectByPrimaryKey(userBill.getId());
			if (rechargeBill == null) {
				throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}
			resultJsonObject.put("bill_amount", rechargeBill.getGold_amount());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("recharge_bill_id", rechargeBill.getId());
			jsonObject.put("created_at", rechargeBill.getCreated_at());
			jsonObject.put("verified_at", rechargeBill.getVerified_time());
			jsonObject.put("success_at", rechargeBill.getSuccess_time());
			jsonObject.put("recharge_type", rechargeBill.getRecharge_type());
			jsonObject.put("photo", rechargeBill.getPhoto());
			jsonObject.put("comment", rechargeBill.getComment());
			jsonObject.put("rmb_amount", rechargeBill.getRmb_amount());
			jsonObject.put("gold_amount", rechargeBill.getGold_amount());
			jsonObject.put("gold_coupon", rechargeBill.getGold_coupon());
			jsonObject.put("gold_total", rechargeBill.getGold_total());
			resultJsonObject.put("recharge_detail", jsonObject);
		} else if (StringUtils.isNotBlank(userBill.getBill_type()) && userBill.getBill_type().equals("2")) {// 支出
			CostRecord costRecord = costRecordMapper.selectByPrimaryKey(userBill.getId());// BILL账单 recharge账单
																							// cost账单使用的ID均为同一个ID
			if (costRecord == null) {
				throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}

			resultJsonObject.put("bill_amount", costRecord.getCost_gold());
			resultJsonObject.put("cost_type", costRecord.getCost_type());
			JSONArray jsonArray = new JSONArray();
			List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectByRecordIdAndUserInfoId(
					costRecord.getId(),getBillDetailParameters.getAccount_name(),getBillDetailParameters.getAccount_score_start(),getBillDetailParameters.getAccount_score_end(), user_info_id, getBillDetailParameters.getPageNo(),
					ConanApplicationConstants.INIT_PAGE_SIZE);
			int detectionAccountListTotal = detectionAccountMapper
					.selectByRecordIdAndUserInfoIdTotal(costRecord.getId(),getBillDetailParameters.getAccount_name(),getBillDetailParameters.getAccount_score_start(),getBillDetailParameters.getAccount_score_end(), user_info_id);
			PageInfo pageInfo = new PageInfo();
			pageInfo.setPageNo(getBillDetailParameters.getPageNo());
			pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
			pageInfo.setTotal(detectionAccountListTotal);
			resultJsonObject.put("page_info", pageInfo);
			
			for (DetectionAccount detectionAccount : detectionAccountList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("scan_account_id", detectionAccount.getId());
				jsonObject.put("created_at", detectionAccount.getCreated_at());
				jsonObject.put("account_name", detectionAccount.getAccount_name());
				jsonObject.put("account_score", detectionAccount.getAccount_score());
				jsonObject.put("detail_score0", detectionAccount.getDetail_score0());
				jsonObject.put("detail_score1", detectionAccount.getDetail_score1());
				jsonObject.put("detail_score2", detectionAccount.getDetail_score2());
				jsonObject.put("detail_score3", detectionAccount.getDetail_score3());
				jsonObject.put("detail_score4", detectionAccount.getDetail_score4());
				jsonObject.put("scan_cost", detectionAccount.getCost());
				jsonArray.add(jsonObject);
			}
			resultJsonObject.put("scan_accounts", jsonArray);
		} else {// 异常
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		return resultJsonObject;
	}

	@Transactional
	public JSONObject postRechargeReq(int recharge_type, double recharge_amount, String comment, String capture_id,
			String user_info_id) {
		UserRemain userRemain = userRemainMapper.selectByPrimaryKey(user_info_id);// 用户信息 用户权限 用户金额 所使用的ID 均为同一ID
		if (userRemain == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		String uuid = UUID.randomUUID().toString();// 生成唯一主键
		Date date = new Date();
		UserBill userBill = new UserBill();
		userBill.setId(uuid);
		userBill.setCreated_at(date);
		userBill.setUpdated_at(date);
		userBill.setBill_type("1");
		userBill.setRemain_gold(userRemain.getGold_amount() + userRemain.getGold_coupon());
		userBill.setBill_digest("人民币充值: " + recharge_amount + "元");
		userBill.setUser_info_id(user_info_id);

		RechargeBill rechargeBill = new RechargeBill();
		rechargeBill.setId(uuid);
		rechargeBill.setCreated_at(date);
		rechargeBill.setUpdated_at(date);
		rechargeBill.setRecharge_type(String.valueOf(recharge_type));
		rechargeBill.setPhoto(capture_id);
		rechargeBill.setComment(comment);
		rechargeBill.setRmb_amount(recharge_amount);
		rechargeBill.setGold_amount(recharge_amount*ConanApplicationConstants.RMB_TO_COUPON_RATE);
		rechargeBill.setGold_coupon(0d);

		JSONObject rechargePackageJsonObject = jsonService.getrRechargePackage();
		JSONArray packagesJsonArray = rechargePackageJsonObject.getJSONArray("packages");
		for (int i = 0; i < packagesJsonArray.size(); i++) {
			if (recharge_amount>=packagesJsonArray.getJSONObject(i).getIntValue("package_amount")) {
				rechargeBill.setGold_coupon(packagesJsonArray.getJSONObject(i).getIntValue("package_copon") * 1d);
				userBill.setBill_digest("人民币充值: " + recharge_amount + "元|额外赠送"
						+ packagesJsonArray.getJSONObject(i).getIntValue("package_copon") + "金币");
				break;
			}
		}
		rechargeBill.setGold_total(recharge_amount*ConanApplicationConstants.RMB_TO_COUPON_RATE + rechargeBill.getGold_coupon());
		rechargeBill.setUser_info_id(user_info_id);
		rechargeBill.setUser_bill_id(uuid);
		rechargeBill.setRecharge_status("2");// 未审核
		rechargeBillMapper.insertSelective(rechargeBill);

		userBillMapper.insert(userBill);

		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("bill_id", uuid);
		resultJsonObject.put("created_at", date);
		resultJsonObject.put("bill_type", 1);
		resultJsonObject.put("bill_amount", rechargeBill.getGold_total());
		resultJsonObject.put("bill_remain", userRemain.getGold_amount() + userRemain.getGold_coupon());
		resultJsonObject.put("bill_digest", "");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("recharge_bill_id", uuid);
		jsonObject.put("created_at", date);
		jsonObject.put("verified_at", "");
		jsonObject.put("success_at", "");
		jsonObject.put("recharge_type", recharge_type);
		jsonObject.put("photo", capture_id);
		jsonObject.put("comment", comment);
		jsonObject.put("rmb_amount", recharge_amount);
		jsonObject.put("gold_amount", recharge_amount);
		jsonObject.put("gold_coupon", rechargeBill.getGold_coupon());
		jsonObject.put("gold_total", rechargeBill.getGold_total());
		jsonObject.put("recharge_status", 2);
		resultJsonObject.put("recharge_detail", jsonObject);
		return resultJsonObject;
	}
}
