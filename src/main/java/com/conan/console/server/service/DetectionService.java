package com.conan.console.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conan.console.server.entity.DetectionAccount;
import com.conan.console.server.mapper.DetectionAccountMapper;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.utils.ConanApplicationConstants;

@Service
public class DetectionService {
	
	@Autowired
	private DetectionAccountMapper detectionAccountMapper;
	
	@Transactional
	public List<DetectionAccount> getDetectionAccountPages(UserGetScanHistoryParameters userGetScanHistoryParameters,String user_info_id) {
		return detectionAccountMapper.selectByUserGetScanHistoryParameters(userGetScanHistoryParameters, user_info_id,ConanApplicationConstants.INIT_PAGE_SIZE);
	}
}
