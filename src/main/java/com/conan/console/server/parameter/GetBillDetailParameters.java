package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetBillDetailParameters {
	
	private Integer current_page;
	
	@NotNull(message="账单ID不能为空")
	@NotBlank(message="账单ID不能为空")
	@NotEmpty(message="账单ID不能为空")
	@Size (max=255, message="账单ID最大长度为45")
	private String bill_id;

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public String getBill_id() {
		return bill_id;
	}

	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}

}
