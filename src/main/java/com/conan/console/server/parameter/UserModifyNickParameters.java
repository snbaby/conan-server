package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModifyNickParameters {
	
	@NotNull(message = "用户昵称不能为空")
	@NotBlank(message = "用户昵称不能为空")
	@NotEmpty(message = "用户昵称不能为空")
	@Size (max=255, message="用户昵称地址最大长度为255")
	private String new_nick;
	
	

	public String getNew_nick() {
		return new_nick;
	}

	public void setNew_nick(String new_nick) {
		this.new_nick = new_nick;
	}
}
