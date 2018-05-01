package com.conan.console.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.CostRecord;
import com.conan.console.server.entity.DetectionAccount;
import com.conan.console.server.entity.PageInfo;
import com.conan.console.server.entity.RechargeBill;
import com.conan.console.server.entity.UserBill;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.CostRecordMapper;
import com.conan.console.server.mapper.DetectionAccountMapper;
import com.conan.console.server.mapper.RechargeBillMapper;
import com.conan.console.server.mapper.UserBillMapper;
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

	@Transactional
	public JSONObject getUserBillPages(UserGetBillParameters userGetBillParameters, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		List<UserBill> userBillList = userBillMapper.selectByUserGetBillParameters(userGetBillParameters, user_info_id,
				ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = userBillMapper.selectByUserGetBillParametersTotal(userGetBillParameters, user_info_id);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(userGetBillParameters.getCurrent_page());
		pageInfo.setTotal(total);
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);

		resultJsonObject.put("page_info", pageInfo);

		JSONArray jsonArray = new JSONArray();
		for (UserBill userBill : userBillList) {
			JSONObject userBillJsonObject = new JSONObject();
			userBillJsonObject.put("bill_id", userBill.getId());
			userBillJsonObject.put("created_at", userBill.getCreated_at());
			userBillJsonObject.put("bill_type", userBill.getBill_type());
			userBillJsonObject.put("bill_remain", userBill.getRemain_gold());
			userBillJsonObject.put("bill_digest", userBill.getBill_digest());
			userBillJsonObject.put("bill_status", userBill.getBill_digest());
			if (StringUtils.isNotBlank(userBill.getBill_type()) && userBill.getBill_type().equals("1")) {// 充值
				RechargeBill rechargeBill = rechargeBillMapper.selectByPrimaryKey(userBill.getId());// BILL账单 recharge账单
																									// cost账单使用的ID均为同一个ID
				if (rechargeBill == null) {
					throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
				}
				userBillJsonObject.put("bill_amount", rechargeBill.getGold_amount());
			} else if (StringUtils.isNotBlank(userBill.getBill_type()) && userBill.getBill_type().equals("2")) {// 支出
				CostRecord costRecord = costRecordMapper.selectByPrimaryKey(userBill.getId());// BILL账单 recharge账单
																								// cost账单使用的ID均为同一个ID
				if (costRecord == null) {
					throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
				}
				userBillJsonObject.put("bill_amount", costRecord.getCost_gold());
			} else {// 异常
				throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}
			jsonArray.add(userBillJsonObject);
		}
		resultJsonObject.put("bills", jsonArray);
		return resultJsonObject;
	}

	@Transactional
	public JSONObject getBillDetail(GetBillDetailParameters getBillDetailParameters, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		UserBill userBill = userBillMapper.selectByPrimaryKey(getBillDetailParameters.getBill_id());
		if (userBill == null || userBill.getUser_info_id().equals(user_info_id)) {
			throw new ConanException(ConanExceptionConstants.BILL_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.BILL_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.BILL_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		resultJsonObject.put("bill_id", userBill.getId());
		resultJsonObject.put("created_at", userBill.getCreated_at());
		resultJsonObject.put("bill_type", userBill.getBill_type());
		resultJsonObject.put("bill_remain", userBill.getRemain_gold());
		resultJsonObject.put("bill_digest", userBill.getBill_digest());
		resultJsonObject.put("bill_status", userBill.getBill_status());
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
					costRecord.getId(), user_info_id, getBillDetailParameters.getCurrent_page(),
					ConanApplicationConstants.INIT_PAGE_SIZE);
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
				jsonObject.put("scan_cost", detectionAccount.getAccount_score() < 0 ? 0 : 1);
				jsonArray.add(jsonObject);
			}

		} else {// 异常
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		return resultJsonObject;
	}
}
