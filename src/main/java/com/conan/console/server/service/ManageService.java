package com.conan.console.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conan.console.server.entity.master.RechargeBill;
import com.conan.console.server.entity.master.UserBill;
import com.conan.console.server.entity.master.UserRemain;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.RechargeBillMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.mapper.master.UserRemainMapper;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class ManageService {
	@Autowired
	private RechargeBillMapper rechargeBillMapper;
	
	@Autowired
	private UserRemainMapper userRemainMapper;
	
	@Autowired
	private UserBillMapper userBillMapper;
	
	@Transactional
	public void handleRechargeReq(String recharge_id,String action,String reason) {
		RechargeBill rechargeBill = rechargeBillMapper.selectByPrimaryKey(recharge_id);
		if (rechargeBill == null) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
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
}
