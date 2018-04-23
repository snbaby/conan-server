package com.conan.console.server.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.GetValidationCodeParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class PhoneController {

	@PostMapping("get_validation_code")
	public ResponseEntity<ResponseSuccessResult> getValidationCode(@Valid GetValidationCodeParameters getValidationCodeParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
        }
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.CREATED.value(),"success");
		return new ResponseEntity<>(responseResult,HttpStatus.CREATED);
	}
	
}
