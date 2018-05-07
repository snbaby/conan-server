package com.conan.console.server.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.HandleRechargeReqParameters;
import com.conan.console.server.parameter.QueryCostDetailParameters;
import com.conan.console.server.parameter.QueryCostListParameters;
import com.conan.console.server.parameter.QueryRechargeListParameters;
import com.conan.console.server.parameter.QueryUserListParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.ManageService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "admin/api/v2.0")
public class ManageController {
	
	@Autowired
	private ManageService manageService;
	
	@PostMapping("handleRechargeReq")
	public ResponseEntity<ResponseSuccessResult> handleRechargeReq(HttpServletRequest request,@RequestBody @Valid HandleRechargeReqParameters handleRechargeReqParameters, BindingResult bindingResult) {
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
		
		if(handleRechargeReqParameters.getAction().equals("agree")&&handleRechargeReqParameters.getAction().equals("no")) {
			throw new ConanException(ConanExceptionConstants.ACTION_PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.ACTION_PARAMETER_EXCEPTION_MESSAGE,
					ConanExceptionConstants.ACTION_PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		manageService.handleRechargeReq(handleRechargeReqParameters.getRecharge_id(), handleRechargeReqParameters.getAction(), handleRechargeReqParameters.getReason());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@PostMapping("queryUserList")
	public ResponseEntity<ResponseSuccessResult> queryUserList(HttpServletRequest request,@RequestBody @Valid QueryUserListParameters queryUserListParameters, BindingResult bindingResult) {
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
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",manageService.queryUserList(queryUserListParameters));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@PostMapping("queryRechargeList")
	public ResponseEntity<ResponseSuccessResult> queryRechargeList(HttpServletRequest request,@RequestBody @Valid QueryRechargeListParameters queryRechargeListParameters, BindingResult bindingResult) {
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
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",manageService.queryRechargeList(queryRechargeListParameters));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@PostMapping("queryCostList")
	public ResponseEntity<ResponseSuccessResult> queryCostList(HttpServletRequest request,@RequestBody @Valid QueryCostListParameters queryCostListParameters, BindingResult bindingResult) {
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
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",manageService.queryCostList(queryCostListParameters));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@PostMapping("queryCostDetail")
	public ResponseEntity<ResponseSuccessResult> queryCostDetail(HttpServletRequest request,@RequestBody @Valid QueryCostDetailParameters queryCostDetailParameters, BindingResult bindingResult) {
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
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",manageService.queryCostDetail(queryCostDetailParameters.getCost_id(),queryCostDetailParameters.getPageNo() ));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	
}
