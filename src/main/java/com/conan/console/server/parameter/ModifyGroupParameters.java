package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ModifyGroupParameters {
	@NotNull(message = "group_id不能为空")
	@NotBlank(message = "group_id不能为空")
	@NotEmpty(message = "group_id不能为空")
	@Size(max = 45, message = "group_id长度最大长度为45")
	private String group_id;
	
	@NotNull(message = "group_name不能为空")
	@NotBlank(message = "group_name不能为空")
	@NotEmpty(message = "group_name不能为空")
	@Size(max = 45, message = "group_name长度最大长度为45")
	private String group_name;
	
	@Size(max = 255, message = "group_comment长度最大长度为255")
	private String group_comment;

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_comment() {
		return group_comment;
	}

	public void setGroup_comment(String group_comment) {
		this.group_comment = group_comment;
	}
	
	
}
