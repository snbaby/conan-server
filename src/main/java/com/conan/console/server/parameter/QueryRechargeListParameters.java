package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryRechargeListParameters {
	
	
	@Size (max=45, message="user_id最大长度为45")
	private String user_id;
	
	@Size (max=45, message="user_phone最大长度为50")
	private String user_phone;
	
	private String created_start_date;
	
	private String created_end_date;
	
	private Integer recharge_type;
	
	private Integer recharge_status;
	
	private Float rmb_amount_start;
	
	private Float rmb_amount_end;
	
	private String recharge_id;
	
	@NotNull(message = "pageNo不能为空")
	@Min(value = 1, message = "pageNo最小为1")
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

	public Integer getRecharge_type() {
		return recharge_type;
	}

	public void setRecharge_type(Integer recharge_type) {
		this.recharge_type = recharge_type;
	}

	public Integer getRecharge_status() {
		return recharge_status;
	}

	public void setRecharge_status(Integer recharge_status) {
		this.recharge_status = recharge_status;
	}

	public Float getRmb_amount_start() {
		return rmb_amount_start;
	}

	public void setRmb_amount_start(Float rmb_amount_start) {
		this.rmb_amount_start = rmb_amount_start;
	}

	public Float getRmb_amount_end() {
		return rmb_amount_end;
	}

	public void setRmb_amount_end(Float rmb_amount_end) {
		this.rmb_amount_end = rmb_amount_end;
	}

	public String getRecharge_id() {
		return recharge_id;
	}

	public void setRecharge_id(String recharge_id) {
		this.recharge_id = recharge_id;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	
}

