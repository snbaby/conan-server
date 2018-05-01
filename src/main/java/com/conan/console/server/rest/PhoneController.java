package com.conan.console.server.rest;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.GetValidationCodeParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.utils.ConanExceptionConstants;
import com.conan.console.server.utils.ConanHttpClientUtils;

@RestController
@RequestMapping(value = "api/v2.0")
public class PhoneController {
	@Value("${conan.server.validate-url}")
	private String validateUrl;

	@PostMapping("get_validation_code")
	public ResponseEntity<ResponseSuccessResult> getValidationCode(HttpServletRequest request,@Valid GetValidationCodeParameters getValidationCodeParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
        }
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_phone", getValidationCodeParameters.getUser_phone());
		int random = new Random().nextInt(999999);
		jsonObject.put("validation_code", random>100000?random:random+100000 + "");
		if(ConanHttpClientUtils.httpPostWithJson(jsonObject, validateUrl)) {
			request.getSession().setAttribute("validation_code","123456");
			request.getSession().setAttribute("user_phone",getValidationCodeParameters.getUser_phone());
		};
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(),"success");
		return new ResponseEntity<>(responseResult,HttpStatus.CREATED);
	}
	
}
