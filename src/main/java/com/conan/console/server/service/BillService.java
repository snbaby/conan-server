package com.conan.console.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.CostRecord;
import com.conan.console.server.entity.PageInfo;
import com.conan.console.server.entity.RechargeBill;
import com.conan.console.server.entity.UserBill;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.CostRecordMapper;
import com.conan.console.server.mapper.RechargeBillMapper;
import com.conan.console.server.mapper.UserBillMapper;
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
		
		resultJsonObject.put("page_info",pageInfo);
		
		JSONArray jsonArray = new JSONArray();
		for(UserBill userBill:userBillList) {
			JSONObject userBillJsonObject = new JSONObject();
			userBillJsonObject.put("bill_id", userBill.getId());
			userBillJsonObject.put("created_at", userBill.getCreated_at());
			userBillJsonObject.put("bill_type", userBill.getBill_type());
			userBillJsonObject.put("bill_remain", userBill.getRemain_gold());
			userBillJsonObject.put("bill_digest", userBill.getBill_digest());
			userBillJsonObject.put("bill_status", userBill.getBill_digest());
			if(StringUtils.isNotBlank(userBill.getBill_type())&&userBill.getBill_type().equals("1")) {//充值
				RechargeBill rechargeBill = rechargeBillMapper.selectByPrimaryKey(userBill.getId());//BILL账单 recharge账单 cost账单使用的ID均为同一个ID
				if (rechargeBill == null) {
					throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
				}
				userBillJsonObject.put("bill_amount", rechargeBill.getGold_amount());
			}else if(StringUtils.isNotBlank(userBill.getBill_type())&&userBill.getBill_type().equals("2")) {//支出
				CostRecord costRecord = costRecordMapper.selectByPrimaryKey(userBill.getId());//BILL账单 recharge账单 cost账单使用的ID均为同一个ID
				if (costRecord == null) {
					throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
							ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
				}
				userBillJsonObject.put("bill_amount", costRecord.getCost_gold());
			}else {//异常
				throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}
			jsonArray.add(userBillJsonObject);
		}
		resultJsonObject.put("bills",jsonArray);
		return resultJsonObject;
	}
}
