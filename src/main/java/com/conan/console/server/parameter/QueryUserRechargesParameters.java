package com.conan.console.server.parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class QueryUserRechargesParameters {

	@NotNull(message = "current_page不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private int pageNo;

	@NotNull(message = "bill_type不能为空")
	@Min(value = 1, message = "bill_type最小为1")
	@Max(value = 4, message = "bill_type最小为4")
	private int bill_type;

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

	
}
