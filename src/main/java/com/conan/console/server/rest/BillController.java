package com.conan.console.server.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.GetBillDetailParameters;
import com.conan.console.server.parameter.PostRechargeReqParameters;
import com.conan.console.server.parameter.QueryUserRechargesParameters;
import com.conan.console.server.parameter.UserGetBillParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.BillService;
import com.conan.console.server.service.JsonService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class BillController {
	@Autowired
	private BillService billService;
	@Autowired
	private JsonService jsonService;

	@PostMapping("user_get_bill")
	public ResponseEntity<ResponseSuccessResult> userGetBill(HttpServletRequest request,
			@RequestBody @Valid UserGetBillParameters userGetBillParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				billService.getUserBillPages(userGetBillParameters, userInfoId));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("get_bill_detail")
	public ResponseEntity<ResponseSuccessResult> getBillDetail(HttpServletRequest request,
			@RequestBody @Valid GetBillDetailParameters getBillDetail, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(), "success",
				billService.getBillDetail(getBillDetail, userInfoId));
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}

	@PostMapping("post_recharge_req")
	public ResponseEntity<ResponseSuccessResult> postRechargeReq(HttpServletRequest request,
			@RequestBody @Valid PostRechargeReqParameters postRechargeReqParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(), "success",
				billService.postRechargeReq(postRechargeReqParameters.getRecharge_type(),
						postRechargeReqParameters.getRecharge_amount(), postRechargeReqParameters.getComment(),
						postRechargeReqParameters.getCapture_id(), userInfoId));
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}

	@GetMapping("get_recharge_package_list")
	public ResponseEntity<ResponseSuccessResult> getRechargePackageList(HttpServletRequest request) {
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				jsonService.getrRechargePackage());
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("queryUserRecharges")
	public ResponseEntity<ResponseSuccessResult> queryUserRecharges(HttpServletRequest request,
			@RequestBody @Valid QueryUserRechargesParameters queryUserRechargesParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(), "success",
				billService.queryUserRecharges(queryUserRechargesParameters.getRecharge_status(), queryUserRechargesParameters.getPageNo(), userInfoId));
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}
	

}
