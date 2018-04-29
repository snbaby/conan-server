package com.conan.console.server.rest;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.UserGetBillParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.BillService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class BillController {
	@Autowired
	private BillService billService;
	
	@PostMapping("user_get_bill")
	public ResponseEntity<ResponseSuccessResult> userGetBill(@Valid UserGetBillParameters userGetBillParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
        }
		userGetBillParameters.setBill_date_start(new Date());
		userGetBillParameters.setBill_date_end(new Date());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				billService.getUserBillPages(userGetBillParameters,"1"));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
}
