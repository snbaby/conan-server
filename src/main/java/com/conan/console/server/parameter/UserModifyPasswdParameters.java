package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModifyPasswdParameters {
	
	@NotNull(message = "用户ID不能为空")
	@NotBlank(message = "用户ID不能为空")
	@NotEmpty(message = "用户ID不能为空")
	@Size(max = 45, message = "用户ID最大长度为45")
	private String user_id;

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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

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
	
	

}
