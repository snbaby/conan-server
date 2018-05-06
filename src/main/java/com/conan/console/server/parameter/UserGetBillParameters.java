package com.conan.console.server.parameter;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class UserGetBillParameters {
	@NotNull(message = "pageNo不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private int pageNo;

	@Min(value = 0, message = "bill_type最小为0")
	@Max(value = 2, message = "bill_type最大为2")
	private int bill_type;
	
	private String bill_date_start;

	private String bill_date_end;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getBill_type() {
		return bill_type;
	}

	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
	}

	public String getBill_date_start() {
		return bill_date_start;
	}

	public void setBill_date_start(String bill_date_start) {
		this.bill_date_start = bill_date_start;
	}

	public String getBill_date_end() {
		return bill_date_end;
	}

	public void setBill_date_end(String bill_date_end) {
		this.bill_date_end = bill_date_end;
	}

}
