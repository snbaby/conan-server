package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryGroupDetailParameters {
	@NotNull(message = "group_id不能为空")
	@NotBlank(message = "group_id不能为空")
	@NotEmpty(message = "group_id不能为空")
	@Size(max = 45, message = "group_id长度最大长度为45")
	private String group_id;
	
	@NotNull(message = "account_name不能为空")
	@NotBlank(message = "account_name不能为空")
	@NotEmpty(message = "account_name不能为空")
	@Size(max = 45, message = "account_name长度最大长度为255")
	private String account_name;
	
	private Integer scan_status;
	
	private String scan_time_start;
	
	private String scan_time_end;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public Integer getScan_status() {
		return scan_status;
	}

	public void setScan_status(Integer scan_status) {
		this.scan_status = scan_status;
	}

	public String getScan_time_start() {
		return scan_time_start;
	}

	public void setScan_time_start(String scan_time_start) {
		this.scan_time_start = scan_time_start;
	}

	public String getScan_time_end() {
		return scan_time_end;
	}

	public void setScan_time_end(String scan_time_end) {
		this.scan_time_end = scan_time_end;
	}
}
