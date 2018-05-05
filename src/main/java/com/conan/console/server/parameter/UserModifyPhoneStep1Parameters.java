package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModifyPhoneStep1Parameters {

	@NotNull(message="密码不能为空")
	@NotBlank(message="密码不能为空")
	@NotEmpty(message="密码不能为空")
	@Size (max=255, message="密码最大长度为255")
	private String user_passwd;
	
	@NotNull(message = "old_validation_code验证码不能为空")
	@NotBlank(message = "old_validation_code验证码不能为空")
	@NotEmpty(message = "old_validation_code验证码不能为空")
	@Size(min = 6, max = 6, message = "old_validation_code验证码长度为6位")
	private String old_validation_code;
	
	@NotNull(message="电话号码不能为空")
	@NotBlank(message="电话号码不能为空")
	@NotEmpty(message="电话号码不能为空")
	@Size(max = 50, message = "电话号码长度最大长度为50")
	private String old_phone;

	public String getUser_passwd() {
		return user_passwd;
	}

	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}

	public String getOld_validation_code() {
		return old_validation_code;
	}

	public void setOld_validation_code(String old_validation_code) {
		this.old_validation_code = old_validation_code;
	}

	public String getOld_phone() {
		return old_phone;
	}

	public void setOld_phone(String old_phone) {
		this.old_phone = old_phone;
	}
	
}
