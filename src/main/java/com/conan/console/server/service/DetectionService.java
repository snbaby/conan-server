package com.conan.console.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.master.CostRecord;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.CostRecordMapper;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;
import com.conan.console.server.utils.ConanUtils;

@Service
public class DetectionService {

	@Autowired
	private DetectionAccountMapper detectionAccountMapper;

	@Autowired
	private CostRecordMapper costRecordMapper;

	@Transactional
	public List<DetectionAccount> getDetectionAccountPages(UserGetScanHistoryParameters userGetScanHistoryParameters,
			String user_info_id) {
		return detectionAccountMapper.selectByUserGetScanHistoryParameters(userGetScanHistoryParameters, user_info_id,
				ConanApplicationConstants.INIT_PAGE_SIZE);
	}

	@Transactional
	public JSONObject getRecentScanStat(String user_info_id) {
		JSONObject jsonObject = new JSONObject();
		CostRecord costRecord = costRecordMapper.selectLastRecordByUserInfoId(user_info_id);
		if (costRecord == null) {
			return jsonObject;
		}
		List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectByRecordId(costRecord.getId());
		if (detectionAccountList == null || detectionAccountList.isEmpty()) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		int dangerScanNo = 0;// 危险账号个数
		for (DetectionAccount detectionAccount : detectionAccountList) {
			if (detectionAccount.getAccount_score() >= 80 && detectionAccount.getAccount_score() < 100) {// 80--100危险账号
				dangerScanNo++;
			}
		}
		jsonObject.put("totalScanNo", detectionAccountList.size());
		jsonObject.put("dangerScanNo", dangerScanNo);
		jsonObject.put("dangerPercent", dangerScanNo / detectionAccountList.size());
		jsonObject.put("recentScanTime", costRecord.getCreated_at());
		return jsonObject;
	}

	@Transactional
	public JSONObject getTopDangers(int topNum, int lastDays, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectTopDangersByUserInfoId(user_info_id, topNum, ConanUtils.getLastDay(lastDays));
		if (detectionAccountList == null || detectionAccountList.isEmpty()) {
			resultJsonObject.put("topDangers", new JSONArray());
			return resultJsonObject;
		}
		JSONArray jsonArray = new JSONArray();  
		for (DetectionAccount detectionAccount : detectionAccountList) {
			JSONObject jsonObject = new JSONObject();
			/*"account_name":"kaka臣",
	        "account_score":95,
	        "detection_time":"2018-04-11 11:23:56",*/
			jsonObject.put("account_name", detectionAccount.getAccount_name());
			jsonObject.put("account_score", detectionAccount.getAccount_score());
			jsonObject.put("detection_time", detectionAccount.getCreated_at());
			jsonArray.add(jsonObject);
		}
		resultJsonObject.put("topDangers", jsonArray);
		return resultJsonObject;
	}
}
