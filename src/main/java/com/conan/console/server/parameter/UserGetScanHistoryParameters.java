package com.conan.console.server.parameter;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserGetScanHistoryParameters {
	@NotNull(message = "current_page不能为空")
	@Min(value = 1, message = "current_page最小为1")
	private int current_page;

	@NotNull(message = "scan_status不能为空")
	@Min(value = 1, message = "scan_status最小为1")
	@Max(value = 3, message = "scan_status最大为3")
	private int scan_status;

	@Size(max = 255, message = "scan_account最大长度为255")
	private String scan_account;
	
	private Date scan_date_start;

	private Date scan_date_end;

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getScan_status() {
		return scan_status;
	}

	public void setScan_status(int scan_status) {
		this.scan_status = scan_status;
	}

	public String getScan_account() {
		return scan_account;
	}

	public void setScan_account(String scan_account) {
		this.scan_account = scan_account;
	}

	public Date getScan_date_start() {
		return scan_date_start;
	}

	public void setScan_date_start(Date scan_date_start) {
		this.scan_date_start = scan_date_start;
	}

	public Date getScan_date_end() {
		return scan_date_end;
	}

	public void setScan_date_end(Date scan_date_end) {
		this.scan_date_end = scan_date_end;
	}
}
