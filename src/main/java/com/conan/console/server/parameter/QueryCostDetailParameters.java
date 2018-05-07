package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryCostDetailParameters {
	
	@NotNull(message="cost_id不能为空")
	@NotBlank(message="cost_id不能为空")
	@NotEmpty(message="cost_id不能为空")
	@Size (max=45, message="cost_id最大长度为45")
	private String cost_id;
	
	@NotNull(message = "pageNo不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private Integer pageNo;

	public String getCost_id() {
		return cost_id;
	}

	public void setCost_id(String cost_id) {
		this.cost_id = cost_id;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
}

