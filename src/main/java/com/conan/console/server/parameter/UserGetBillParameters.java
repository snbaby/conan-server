package com.conan.console.server.parameter;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class UserGetBillParameters {
	@NotNull(message = "current_page不能为空")
	@Min(value = 1, message = "current_page最小为1")
	private int current_page;

	@NotNull(message = "bill_status不能为空")
	@Min(value = 1, message = "scan_status最小为1")
	@Max(value = 3, message = "scan_status最大为3")
	private int bill_status;

	@Min(value = 1, message = "bill_type最小为1")
	@Max(value = 2, message = "bill_type最大为2")
	private int bill_type;
	
	private Date bill_date_start;

	private Date bill_date_end;

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getBill_status() {
		return bill_status;
	}

	public void setBill_status(int bill_status) {
		this.bill_status = bill_status;
	}

	public int getBill_type() {
		return bill_type;
	}

	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
	}

	public Date getBill_date_start() {
		return bill_date_start;
	}

	public void setBill_date_start(Date bill_date_start) {
		this.bill_date_start = bill_date_start;
	}

	public Date getBill_date_end() {
		return bill_date_end;
	}

	public void setBill_date_end(Date bill_date_end) {
		this.bill_date_end = bill_date_end;
	}

	
}
