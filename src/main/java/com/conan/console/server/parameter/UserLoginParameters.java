package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginParameters {
	
	@NotNull(message = "电话号码不能为空")
	@NotBlank(message = "电话号码不能为空")
	@NotEmpty(message = "电话号码不能为空")
	@Size(max = 50, message = "电话号码长度最大长度为50")
	private String user_phone;
	
	@NotNull(message="密码不能为空")
	@NotBlank(message="密码不能为空")
	@NotEmpty(message="密码不能为空")
	@Size (max=255, message="密码最大长度为255")
	private String user_passwd;

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_passwd() {
		return user_passwd;
	}

	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}
	
	
}
