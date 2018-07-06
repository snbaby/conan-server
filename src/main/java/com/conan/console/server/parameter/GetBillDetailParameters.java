package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

public class GetBillDetailParameters {
	
	private Integer pageNo;
	
	@NotNull(message="账单ID不能为空")
	@NotBlank(message="账单ID不能为空")
	@NotEmpty(message="账单ID不能为空")
	@Size (max=255, message="账单ID最大长度为45")
	private String bill_id;
	
	@Size (max=255, message="account_name最大长度为255")
	private String account_name;
	
	private String account_score_start;
	
	private String account_score_end;


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

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		if(StringUtils.isBlank(account_name)) {
			this.account_name = null;
		}else {
			this.account_name = account_name;
		}
		
	}

	public String getAccount_score_start() {
		return account_score_start;
	}

	public void setAccount_score_start(String account_score_start) {
		this.account_score_start = account_score_start;
	}

	public String getAccount_score_end() {
		return account_score_end;
	}

	public void setAccount_score_end(String account_score_end) {
		this.account_score_end = account_score_end;
	}

	
}
