package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CheckValidationCodeParameters {
	
	@NotNull(message = "电话号码不能为空")
	@NotBlank(message = "电话号码不能为空")
	@NotEmpty(message = "电话号码不能为空")
	@Size(max = 50, message = "电话号码长度最大长度为50")
	private String user_phone;

	@NotNull(message = "验证码不能为空")
	@NotBlank(message = "验证码不能为空")
	@NotEmpty(message = "验证码不能为空")
	@Size(min = 6, max = 6, message = "验证码长度为6位")
	private String validation_code;

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getValidation_code() {
		return validation_code;
	}

	public void setValidation_code(String validation_code) {
		this.validation_code = validation_code;
	}

	
}
