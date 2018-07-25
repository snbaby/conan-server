package com.conan.console.server.rest;

import java.io.IOException;
import java.util.Map;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.AdminLoginParameters;
import com.conan.console.server.parameter.HandleRechargeReqParameters;
import com.conan.console.server.parameter.QueryCostDetailParameters;
import com.conan.console.server.parameter.QueryCostListParameters;
import com.conan.console.server.parameter.QueryRechargeListParameters;
import com.conan.console.server.parameter.QueryUserListParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.JsonService;
import com.conan.console.server.service.ManageService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "admin/api/v2.0")
public class ManageController {

	@Autowired
	private ManageService manageService;

	@Autowired
	private JsonService jsonService;

	@PostMapping("handleRechargeReq")
	public ResponseEntity<ResponseSuccessResult> handleRechargeReq(HttpServletRequest request,
			@RequestBody @Valid HandleRechargeReqParameters handleRechargeReqParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		if (handleRechargeReqParameters.getAction().equals("agree")) {
			handleRechargeReqParameters.setReason("");
		} else if (handleRechargeReqParameters.getAction().equals("no")) {
			if (StringUtils.isBlank(handleRechargeReqParameters.getReason())) {
				throw new ConanException(ConanExceptionConstants.REASON_PARAMETER_EXCEPTION_CODE,
						ConanExceptionConstants.REASON_PARAMETER_EXCEPTION_MESSAGE,
						ConanExceptionConstants.REASON_PARAMETER_EXCEPTION_HTTP_STATUS);
			}
		} else {
			throw new ConanException(ConanExceptionConstants.ACTION_PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.ACTION_PARAMETER_EXCEPTION_MESSAGE,
					ConanExceptionConstants.ACTION_PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		manageService.handleRechargeReq(handleRechargeReqParameters.getRecharge_id(),
				handleRechargeReqParameters.getAction(), handleRechargeReqParameters.getReason());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("queryUserList")
	public ResponseEntity<ResponseSuccessResult> queryUserList(HttpServletRequest request,
			@RequestBody @Valid QueryUserListParameters queryUserListParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.queryUserList(queryUserListParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("queryUserExcel")
	public ResponseEntity<ResponseSuccessResult> queryUserExcel(HttpServletRequest request,
			@RequestBody @Valid QueryUserListParameters queryUserListParameters, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.queryUserExcel(queryUserListParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("queryRechargeList")
	public ResponseEntity<ResponseSuccessResult> queryRechargeList(HttpServletRequest request,
			@RequestBody @Valid QueryRechargeListParameters queryRechargeListParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.queryRechargeList(queryRechargeListParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("queryRechargeExcel")
	public ResponseEntity<ResponseSuccessResult> queryRechargeExcel(HttpServletRequest request,
			@RequestBody @Valid QueryRechargeListParameters queryRechargeListParameters, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.queryRechargeExcel(queryRechargeListParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("queryCostList")
	public ResponseEntity<ResponseSuccessResult> queryCostList(HttpServletRequest request,
			@RequestBody @Valid QueryCostListParameters queryCostListParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.queryCostList(queryCostListParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("queryCostExcel")
	public ResponseEntity<ResponseSuccessResult> queryCostExcel(HttpServletRequest request,
			@RequestBody @Valid QueryCostListParameters queryCostListParameters, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.queryCostExcel(queryCostListParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("queryCostDetail")
	public ResponseEntity<ResponseSuccessResult> queryCostDetail(HttpServletRequest request,
			@RequestBody @Valid QueryCostDetailParameters queryCostDetailParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", manageService
				.queryCostDetail(queryCostDetailParameters.getCost_id(), queryCostDetailParameters.getPageNo()));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("admin_login")
	public ResponseEntity<ResponseSuccessResult> adminLogin(HttpServletRequest request,
			@RequestBody @Valid AdminLoginParameters adminLoginParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		JSONObject adminLoginJson = jsonService.getAdminLogin();
		JSONArray adminJsonArray = adminLoginJson.getJSONArray("users");
		boolean isAdminExist = false;
		for (int i = 0; i < adminJsonArray.size(); i++) {
			if (adminJsonArray.getJSONObject(i).getString("user_name").equals(adminLoginParameters.getUser_name())
					&& adminJsonArray.getJSONObject(i).getString("user_passwd")
							.equals(adminLoginParameters.getUser_passwd())) {
				isAdminExist = true;
				request.getSession().setAttribute("isAdminLogin", "yes");
				request.getSession().setMaxInactiveInterval(60*30);
			}
		}

		if (!isAdminExist) {
			throw new ConanException(ConanExceptionConstants.USER_LOGIN_EXCEPTION_CODE,
					ConanExceptionConstants.USER_LOGIN_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_LOGIN_EXCEPTION_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@GetMapping("get_stats")
	public ResponseEntity<ResponseSuccessResult> getStats(HttpServletRequest request) {
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.getStats());
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("get_user_stats")
	public ResponseEntity<ResponseSuccessResult> getUserStats(HttpServletRequest request,
			@RequestBody Map<String, String> paramMap) {
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		String range_date_start = paramMap.get("range_date_start");
		String range_date_end = paramMap.get("range_date_end");

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.getUserStats(range_date_start, range_date_end));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("get_recharge_stats")
	public ResponseEntity<ResponseSuccessResult> getRechargeStats(HttpServletRequest request,
			@RequestBody Map<String, String> paramMap) {
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		String range_date_start = paramMap.get("range_date_start");
		String range_date_end = paramMap.get("range_date_end");

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.getRechargeStats(range_date_start, range_date_end));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("get_single_scan_stats")
	public ResponseEntity<ResponseSuccessResult> getSingleScanStats(HttpServletRequest request,
			@RequestBody Map<String, String> paramMap) {
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		String range_date_start = paramMap.get("range_date_start");
		String range_date_end = paramMap.get("range_date_end");

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.getSingleScanStats(range_date_start, range_date_end));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("get_batch_scan_stats")
	public ResponseEntity<ResponseSuccessResult> getBatchScanStats(HttpServletRequest request,
			@RequestBody Map<String, String> paramMap) {
		String isAdminLogin = (String) request.getSession().getAttribute("isAdminLogin");
		if (StringUtils.isBlank(isAdminLogin)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		String range_date_start = paramMap.get("range_date_start");
		String range_date_end = paramMap.get("range_date_end");

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				manageService.getBatchScanStats(range_date_start, range_date_end));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
