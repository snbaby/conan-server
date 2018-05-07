package com.conan.console.server.parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class QueryUserRechargesParameters {

	@NotNull(message = "current_page不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private int pageNo;

	@NotNull(message = "recharge_status不能为空")
	@Min(value = 1, message = "recharge_status最小为1")
	@Max(value = 4, message = "recharge_status最小为4")
	private int recharge_status;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRecharge_status() {
		return recharge_status;
	}

	public void setRecharge_status(int recharge_status) {
		this.recharge_status = recharge_status;
	}


	
}
