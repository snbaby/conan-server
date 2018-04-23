package com.conan.console.server.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.parameter.CheckValidationCodeParameters;
import com.conan.console.server.parameter.UserLoginParameters;
import com.conan.console.server.response.ResponseSuccessResult;

@RestController
@RequestMapping(value = "api/v2.0")
public class PhoneController {

	@PostMapping("get_validation_code")
	public ResponseEntity<ResponseSuccessResult> getValidationCode(@Valid UserLoginParameters getValidationCodeParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ResponseSuccessResult responseResult = new ResponseSuccessResult();
			responseResult.setCode(HttpStatus.BAD_REQUEST.value());
			responseResult.setContent(bindingResult.getFieldError());
            return new ResponseEntity<>(responseResult,HttpStatus.BAD_REQUEST);
        }
		ResponseSuccessResult responseResult = new ResponseSuccessResult();
		responseResult.setCode(HttpStatus.OK.value());
		
		responseResult.setDescription("登陆成功");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	@PostMapping("check_validation_code")
	public ResponseEntity<ResponseSuccessResult> checkValidationCode(@Valid CheckValidationCodeParameters checkValidationCodeParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ResponseSuccessResult responseResult = new ResponseSuccessResult();
			responseResult.setCode(HttpStatus.BAD_REQUEST.value());
			responseResult.setContent(bindingResult.getFieldError());
            return new ResponseEntity<>(responseResult,HttpStatus.BAD_REQUEST);
        }
		ResponseSuccessResult responseResult = new ResponseSuccessResult();
		responseResult.setCode(HttpStatus.OK.value());
		
		responseResult.setDescription("登陆成功");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
}
