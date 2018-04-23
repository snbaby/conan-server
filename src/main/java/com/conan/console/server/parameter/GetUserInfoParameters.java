package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetUserInfoParameters {
	@NotNull(message = "用户ID不能为空")
	@NotBlank(message = "用户ID不能为空")
	@NotEmpty(message = "用户ID不能为空")
	@Size(max = 45, message = "用户ID最大长度为45")
	private String user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
