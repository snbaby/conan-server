package com.conan.console.server.parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryPreCheckParameters {
	@NotNull(message = "scan_type不能为空")
	@Min(value = 1, message = "scan_type最小为1")
	@Max(value = 2, message = "scan_type最大为2")
	private int scan_type;
	
	
	@Size(max = 255, message = "scan_account长度最大长度为255")
	private String scan_account;
	
	@Size(max = 255, message = "scan_file长度最大长度为255")
	private String scan_file;

	public int getScan_type() {
		return scan_type;
	}

	public void setScan_type(int scan_type) {
		this.scan_type = scan_type;
	}

	public String getScan_account() {
		return scan_account;
	}

	public void setScan_account(String scan_account) {
		this.scan_account = scan_account;
	}

	public String getScan_file() {
		return scan_file;
	}

	public void setScan_file(String scan_file) {
		this.scan_file = scan_file;
	}

	
}
