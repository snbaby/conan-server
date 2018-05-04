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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.GetUserInfoParameters;
import com.conan.console.server.parameter.QueryPreCheckParameters;
import com.conan.console.server.parameter.UserLoginParameters;
import com.conan.console.server.parameter.UserModifyNickParameters;
import com.conan.console.server.parameter.UserModifyPasswdParameters;
import com.conan.console.server.parameter.UserModifyPhoneParameters;
import com.conan.console.server.parameter.UserModifyPhotoParameters;
import com.conan.console.server.parameter.UserRegisterParameters;
import com.conan.console.server.parameter.UserResetPasswdParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.UserService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * 用戶登錄
	 * 
	 * @param userLoginParameters
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("user_login")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userLogin(HttpServletRequest request,
			@RequestBody @Valid UserLoginParameters userLoginParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		JSONObject resultJsonObject = userService.getUserInfo(userLoginParameters.getUser_phone(),
				userLoginParameters.getUser_passwd());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				resultJsonObject);
		request.getSession().setAttribute("isLogin", "yes");// 登录成功
		request.getSession().setAttribute("user_info_id", resultJsonObject.getString("user_info_id"));
		request.getSession().setMaxInactiveInterval(30 * 60);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	/**
	 * 用戶註冊
	 * 
	 * @param userRegisterParameters
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("user_register")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userRegister(HttpServletRequest request,
			@RequestBody @Valid UserRegisterParameters userRegisterParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String validationCode = (String) request.getSession().getAttribute("validation_code");
		if (StringUtils.isBlank(validationCode) || !userRegisterParameters.getValidate_code().equals(validationCode)) {
			throw new ConanException(ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_CODE,
					ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_HTTP_STATUS);
		}
		String userPhone = (String) request.getSession().getAttribute("user_phone");
		if (StringUtils.isBlank(userPhone) || !userRegisterParameters.getUser_phone().equals(userPhone)) {
			throw new ConanException(ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_CODE,
					ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_HTTP_STATUS);
		}

		// 用户注册
		userService.registerUser(userRegisterParameters.getUser_phone(), userRegisterParameters.getUser_passwd());
		request.getSession().removeAttribute("validation_code");
		request.getSession().removeAttribute("user_phone");

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}

	/**
	 * 獲取用戶信息
	 * 
	 * @param getUserInfoParameters
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("get_user_info")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> getUserInfo(HttpServletRequest request) {
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(), "success",
				userService.getUserInfo(userInfoId));
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}

	/**
	 * 修改用户的头像
	 * 
	 * @param userModifyPhotoParameters
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("user_modify_photo")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userModifyPhoto(HttpServletRequest request,
			@RequestBody @Valid UserModifyPhotoParameters userModifyPhotoParameters, BindingResult bindingResult) {
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

		userService.updateUserPhoto(userInfoId, userModifyPhotoParameters.getPhoto_url());

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}

	/**
	 * 修改用户昵称
	 * 
	 * @param userModifyNickParameters
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("user_modify_nick")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userModifyNick(HttpServletRequest request,
			@RequestBody @Valid UserModifyNickParameters userModifyNickParameters, BindingResult bindingResult) {
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

		userService.updateUserNick(userInfoId, userModifyNickParameters.getNew_nick());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param userModifyPasswdParameters
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("user_modify_passwd")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userModifyPasswd(HttpServletRequest request,
			@RequestBody @Valid UserModifyPasswdParameters userModifyPasswdParameters, BindingResult bindingResult) {
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

		userService.updateUserPaasword(userInfoId, userModifyPasswdParameters.getOld_passwd(),
				userModifyPasswdParameters.getNew_passwd());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("user_reset_passwd")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userResetPasswd(HttpServletRequest request,
			@RequestBody @Valid UserResetPasswdParameters userResetPasswdParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		String validationCode = (String) request.getSession().getAttribute("validation_code");
		if (StringUtils.isBlank(validationCode)
				|| !userResetPasswdParameters.getValidate_code().equals(validationCode)) {
			throw new ConanException(ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_CODE,
					ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_HTTP_STATUS);
		}
		String userPhone = (String) request.getSession().getAttribute("user_phone");
		if (StringUtils.isBlank(userPhone) || !userResetPasswdParameters.getUser_phone().equals(userPhone)) {
			throw new ConanException(ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_CODE,
					ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_HTTP_STATUS);
		}

		userService.resetUserPaasword(userResetPasswdParameters.getUser_phone(),
				userResetPasswdParameters.getNew_passwd());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("user_modify_phone")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userModifyPhone(HttpServletRequest request,
			@RequestBody @Valid UserModifyPhoneParameters userModifyPhoneParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		String validationCode = (String) request.getSession().getAttribute("validation_code");
		if (StringUtils.isBlank(validationCode)
				|| !userModifyPhoneParameters.getValidate_code().equals(validationCode)) {
			throw new ConanException(ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_CODE,
					ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.VALIDATE_CODE_NOT_MATCHED_EXCEPTION_HTTP_STATUS);
		}

		String userPhone = (String) request.getSession().getAttribute("user_phone");
		if (StringUtils.isBlank(userPhone) || !userModifyPhoneParameters.getNew_phone().equals(userPhone)) {
			throw new ConanException(ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_CODE,
					ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_PHONE_NOT_MATCHED_EXCEPTION_HTTP_STATUS);
		}

		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		userService.updateUserPhone(userModifyPhoneParameters.getUser_id(), userModifyPhoneParameters.getNew_phone());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("query_pre_check")
	public ResponseEntity<ResponseSuccessResult> queryPreCheck(HttpServletRequest request,
			@RequestBody @Valid QueryPreCheckParameters queryPreCheckParameters, BindingResult bindingResult) {
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

		if (queryPreCheckParameters.getScan_type() == 1
				&& StringUtils.isBlank(queryPreCheckParameters.getScan_account())) {
			throw new ConanException(ConanExceptionConstants.SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}

		if (queryPreCheckParameters.getScan_type() == 2 && queryPreCheckParameters.getScan_file() == null) {
			throw new ConanException(ConanExceptionConstants.SCAN_FILE_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.SCAN_FILE_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.SCAN_FILE_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				userService.queryPreCheck(queryPreCheckParameters, userInfoId));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
