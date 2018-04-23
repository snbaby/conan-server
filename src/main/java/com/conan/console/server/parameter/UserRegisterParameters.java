package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterParameters {
	
	@NotBlank
	@NotEmpty
	@Size (max=50, message="电话号码长度只能在1-50之间")
	private String user_phone;
	
	@NotBlank
	@NotEmpty
	@Size (min=6, max=6, message="验证码长度为6位")
	private String validate_code;
	
	@NotBlank
	@NotEmpty
	@Size (min=1, max=255, message="密码长度只能在1-255之间")
	private String user_passwd;

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getValidate_code() {
		return validate_code;
	}

	public void setValidate_code(String validate_code) {
		this.validate_code = validate_code;
	}

	public String getUser_passwd() {
		return user_passwd;
	}

	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}

	
}
