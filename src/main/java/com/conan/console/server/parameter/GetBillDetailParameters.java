package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetBillDetailParameters {
	
	private Integer pageNo;
	
	@NotNull(message="账单ID不能为空")
	@NotBlank(message="账单ID不能为空")
	@NotEmpty(message="账单ID不能为空")
	@Size (max=255, message="账单ID最大长度为45")
	private String bill_id;


	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getBill_id() {
		return bill_id;
	}

	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}

}
