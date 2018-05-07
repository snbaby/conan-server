package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AdminLoginParameters {
	
	@NotNull(message = "user_name不能为空")
	@NotBlank(message = "user_name不能为空")
	@NotEmpty(message = "user_name不能为空")
	@Size(max = 255, message = "user_name最大长度为255")
	private String user_name;
	
	@NotNull(message="密码不能为空")
	@NotBlank(message="密码不能为空")
	@NotEmpty(message="密码不能为空")
	@Size (max=255, message="密码最大长度为255")
	private String user_passwd;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_passwd() {
		return user_passwd;
	}

	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}

}
