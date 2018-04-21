package com.conan.console.server.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.entity.ResponseResult;
import com.conan.console.server.entity.UserLoginParameters;

@RestController
@RequestMapping(value = "api/v2.0")
public class UserController {

	@PostMapping("user_login")
	public ResponseEntity<ResponseResult> userLogin(@Valid UserLoginParameters userLoginParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ResponseResult responseResult = new ResponseResult();
			responseResult.setCode(HttpStatus.BAD_REQUEST.value());
			responseResult.setContent(bindingResult.getFieldError());
            return new ResponseEntity<>(responseResult,HttpStatus.BAD_REQUEST);
        }
		ResponseResult responseResult = new ResponseResult();
		responseResult.setCode(HttpStatus.OK.value());
		
		responseResult.setDescription("登陆成功");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
}
