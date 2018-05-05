package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModifyPasswdParameters {
	
	@NotNull(message="电话号码不能为空")
	@NotBlank(message="电话号码不能为空")
	@NotEmpty(message="电话号码不能为空")
	@Size(max = 50, message = "电话号码长度最大长度为50")
	private String user_phone;
	
	@NotNull(message = "验证码不能为空")
	@NotBlank(message = "验证码不能为空")
	@NotEmpty(message = "验证码不能为空")
	@Size(min = 6, max = 6, message = "验证码长度为6位")
	private String validation_code;

	@NotNull(message="原始密码不能为空")
	@NotBlank(message="原始密码不能为空")
	@NotEmpty(message="原始密码不能为空")
	@Size (max=255, message="原始密码最大长度为255")
	private String old_passwd;
	
	@NotNull(message="新密码不能为空")
	@NotBlank(message="新密码不能为空")
	@NotEmpty(message="新密码不能为空")
	@Size (max=255, message="新密码最大长度为255")
	private String new_passwd;

	public String getOld_passwd() {
		return old_passwd;
	}

	public void setOld_passwd(String old_passwd) {
		this.old_passwd = old_passwd;
	}

	public String getNew_passwd() {
		return new_passwd;
	}

	public void setNew_passwd(String new_passwd) {
		this.new_passwd = new_passwd;
	}

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
