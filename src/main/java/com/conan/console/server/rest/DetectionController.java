package com.conan.console.server.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.DetectionService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class DetectionController {
	@Autowired
	private DetectionService detectionService;
	
	@PostMapping("user_get_scan_history")
	public ResponseEntity<ResponseSuccessResult> userGetScanHistory(HttpServletRequest request,@Valid UserGetScanHistoryParameters userGetScanHistoryParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
        }
		String userInfoId  = (String) request.getSession().getAttribute("user_info_id");
		if(StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		userGetScanHistoryParameters.setScan_date_start(new Date());
		userGetScanHistoryParameters.setScan_date_end(new Date());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(),"success",
				detectionService.getDetectionAccountPages(userGetScanHistoryParameters,userInfoId));
		return new ResponseEntity<>(responseResult,HttpStatus.CREATED);
	}
	
}
