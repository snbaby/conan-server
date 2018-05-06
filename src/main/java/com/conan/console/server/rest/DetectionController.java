package com.conan.console.server.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.GetTopDangersParameters;
import com.conan.console.server.parameter.QueryPreCheckParameters;
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
	public ResponseEntity<ResponseSuccessResult> userGetScanHistory(HttpServletRequest request,@RequestBody @Valid UserGetScanHistoryParameters userGetScanHistoryParameters, BindingResult bindingResult) {
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
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				detectionService.getDetectionAccountPages(userGetScanHistoryParameters,userInfoId));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@GetMapping("get_recent_scan_stat")
	public ResponseEntity<ResponseSuccessResult> getRecentScanStat(HttpServletRequest request) {
		String userInfoId  = (String) request.getSession().getAttribute("user_info_id");
		if(StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				detectionService.getRecentScanStat(userInfoId));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@GetMapping("get_top_dangers")
	public ResponseEntity<ResponseSuccessResult> getTopDangers(HttpServletRequest request,@RequestParam(defaultValue = "10") Integer topNum,  @RequestParam(defaultValue = "0") Integer lastDays) {
		String userInfoId  = (String) request.getSession().getAttribute("user_info_id");
		if(StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				detectionService.getTopDangers(topNum, lastDays, userInfoId));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@PostMapping("scan")
	public ResponseEntity<ResponseSuccessResult> scan(HttpServletRequest request,@RequestBody @Valid QueryPreCheckParameters queryPreCheckParameters, BindingResult bindingResult) {
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
		ResponseSuccessResult responseResult = null;
		if(queryPreCheckParameters.getScan_type() == 1) {
			if(StringUtils.isBlank(queryPreCheckParameters.getScan_account())){
				throw new ConanException(ConanExceptionConstants.SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}else {
				responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
						detectionService.scanSingnalAccount(queryPreCheckParameters.getScan_type(), queryPreCheckParameters.getScan_account(), userInfoId));
				return new ResponseEntity<>(responseResult,HttpStatus.OK);
			}
		}
		
		if(queryPreCheckParameters.getScan_type() == 2) {
			if(StringUtils.isBlank(queryPreCheckParameters.getScan_file())) {
				throw new ConanException(ConanExceptionConstants.SCAN_FILE_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.SCAN_FILE_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.SCAN_FILE_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}else {
			 responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
						detectionService.scanMultiAccount(queryPreCheckParameters.getScan_type(), queryPreCheckParameters.getScan_file(), userInfoId));
				 return new ResponseEntity<>(responseResult,HttpStatus.OK);
				
			}
		}
		
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	
}
