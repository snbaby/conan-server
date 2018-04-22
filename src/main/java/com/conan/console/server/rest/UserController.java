package com.conan.console.server.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.UserLoginParameters;
import com.conan.console.server.parameter.UserRegisterParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.UserService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("user_login")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userLogin(@Valid UserLoginParameters userLoginParameters,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult();
		responseResult.setCode(HttpStatus.OK.value());
		responseResult.setContent(userService.getUserInfo(userLoginParameters.getUser_phone(), userLoginParameters.getUser_passwd()));
		responseResult.setDescription("success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("user_register")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> userRegister(@Valid UserRegisterParameters userRegisterParameters,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		//用户注册
		userService.registerUser(userRegisterParameters.getUser_phone(), userRegisterParameters.getUser_passwd());

		ResponseSuccessResult responseResult = new ResponseSuccessResult();
		responseResult.setCode(HttpStatus.CREATED.value());
		responseResult.setDescription("success");
		return new ResponseEntity<>(responseResult, HttpStatus.CREATED);
	}
}
