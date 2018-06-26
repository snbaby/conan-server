package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateGroupParameters {
	@NotNull(message = "group_name不能为空")
	@NotBlank(message = "group_name不能为空")
	@NotEmpty(message = "group_name不能为空")
	@Size(max = 45, message = "group_name长度最大长度为45")
	private String group_name;

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	
}
