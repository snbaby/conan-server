package com.conan.console.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.master.CostRecord;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.CostRecordMapper;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;

@Service
public class DetectionService {
	
	@Autowired
	private DetectionAccountMapper detectionAccountMapper;
	
	@Autowired
	private CostRecordMapper costRecordMapper;
	
	
	@Transactional
	public List<DetectionAccount> getDetectionAccountPages(UserGetScanHistoryParameters userGetScanHistoryParameters,String user_info_id) {
		return detectionAccountMapper.selectByUserGetScanHistoryParameters(userGetScanHistoryParameters, user_info_id,ConanApplicationConstants.INIT_PAGE_SIZE);
	}
	
	@Transactional
	public JSONObject getRecentScanStat(String user_info_id) {
		JSONObject jsonObject = new JSONObject();
		CostRecord costRecord = costRecordMapper.selectLastRecordByUserInfoId(user_info_id);
		if(costRecord == null) {
			return jsonObject;
		}
		 /*"content":{
		    "totalScanNo":1123, //总检测账号个数
		    "dangerScanNo":110, //总危险账号个数
		    "dangerPercent":(110/1123)*100 //危险账号占比
		    "recentScanTime":"2018-04-30 18:23:15", //最近检测时间
		    },*/
		List<DetectionAccount> detectionAccountList =  detectionAccountMapper.selectByRecordId(costRecord.getId());
		if(detectionAccountList == null || detectionAccountList.isEmpty()) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		int dangerScanNo = 0;//危险账号个数
		for(DetectionAccount detectionAccount : detectionAccountList) {
			if(detectionAccount.getAccount_score()>=80&&detectionAccount.getAccount_score()<100) {//80--100危险账号
				dangerScanNo++;
			}
		}
	    jsonObject.put("totalScanNo", detectionAccountList.size());
	    jsonObject.put("dangerScanNo", dangerScanNo);
	    jsonObject.put("dangerPercent", dangerScanNo/detectionAccountList.size());
	    jsonObject.put("recentScanTime", costRecord.getCreated_at());
		return jsonObject;
	}
	
	
}
