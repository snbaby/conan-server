package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetValidationCodeParameters {
	
	@NotNull(message="电话号码不能为空")
	@NotBlank(message="电话号码不能为空")
	@NotEmpty(message="电话号码不能为空")
	@Size(max = 50, message = "电话号码长度最大长度为50")
	private String user_phone;
	

	public String getUser_phone() {
		return user_phone;
	}


	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	

	
}
