package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryCostListParameters {
	
	@Size (max=45, message="user_id最大长度为45")
	private String user_id;
	
	@Size (max=45, message="user_phone最大长度为50")
	private String user_phone;
	
	private String created_start_date;
	
	private String created_end_date;
	
	private Integer cost_type;
	
	private Double cost_gold_start;
	
	private Double cost_gold_end;
	
	private String cost_id;
	
	private Integer pageNo;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getCreated_start_date() {
		return created_start_date;
	}

	public void setCreated_start_date(String created_start_date) {
		this.created_start_date = created_start_date;
	}

	public String getCreated_end_date() {
		return created_end_date;
	}

	public void setCreated_end_date(String created_end_date) {
		this.created_end_date = created_end_date;
	}

	public Integer getCost_type() {
		return cost_type;
	}

	public void setCost_type(Integer cost_type) {
		this.cost_type = cost_type;
	}

	public Double getCost_gold_start() {
		return cost_gold_start;
	}

	public void setCost_gold_start(Double cost_gold_start) {
		this.cost_gold_start = cost_gold_start;
	}

	public Double getCost_gold_end() {
		return cost_gold_end;
	}

	public void setCost_gold_end(Double cost_gold_end) {
		this.cost_gold_end = cost_gold_end;
	}

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

