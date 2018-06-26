package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class QueryGroupListParameters {
	@NotNull(message = "pageNo不能为空")
	@Min(value = 0, message = "pageNo最小为0")
	private int pageNo;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
