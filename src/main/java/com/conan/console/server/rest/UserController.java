package com.conan.console.server.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.GetUserInfoParameters;
import com.conan.console.server.parameter.UserLoginParameters;
import com.conan.console.server.parameter.UserModifyNickParameters;
import com.conan.console.server.parameter.UserModifyPasswdParameters;
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
	public ResponseEntity<ResponseSuccessResult> userLogin(@Valid UserLoginParameters userLoginParameters,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				userService.getUserInfo(userLoginParameters.getUser_phone(), userLoginParameters.getUser_passwd()));
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
	public ResponseEntity<ResponseSuccessResult> userRegister(@Valid UserRegisterParameters userRegisterParameters,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		// 用户注册
		userService.registerUser(userRegisterParameters.getUser_phone(), userRegisterParameters.getUser_passwd());

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
	public ResponseEntity<ResponseSuccessResult> getUserInfo(@Valid GetUserInfoParameters getUserInfoParameters,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(), "success",
				userService.getUserInfo(getUserInfoParameters.getUser_id()));
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
	public ResponseEntity<ResponseSuccessResult> userModifyPhoto(
			@Valid UserModifyPhotoParameters userModifyPhotoParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		userService.updateUserPhoto(userModifyPhotoParameters.getUser_id(), userModifyPhotoParameters.getPhoto_url());

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
	public ResponseEntity<ResponseSuccessResult> userModifyNick(
			@Valid UserModifyNickParameters userModifyNickParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		userService.updateUserNick(userModifyNickParameters.getUser_id(), userModifyNickParameters.getNew_nick());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	/**
	 * 修改用户密码
	 * @param userModifyPasswdParameters
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("user_modify_passwd")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userModifyPasswd(
			@Valid UserModifyPasswdParameters userModifyPasswdParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		userService.updateUserPaasword(userModifyPasswdParameters.getUser_id(),
				userModifyPasswdParameters.getOld_passwd(), userModifyPasswdParameters.getNew_passwd());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("user_reset_passwd")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userResetPasswd(
			@Valid UserResetPasswdParameters userResetPasswdParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}

		userService.updateUserPaasword(userModifyPasswdParameters.getUser_id(),
				userModifyPasswdParameters.getOld_passwd(), userModifyPasswdParameters.getNew_passwd());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	

}
