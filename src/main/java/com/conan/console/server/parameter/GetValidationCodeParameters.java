package com.conan.console.server.parameter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetValidationCodeParameters {
	
	@NotNull(message="电话号码不能为空")
	@Size (min=1, max=50, message="电话号码长度只能在1-50之间")
	private String user_phone;

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	
}
