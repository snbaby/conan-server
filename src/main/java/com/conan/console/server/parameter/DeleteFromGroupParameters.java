package com.conan.console.server.parameter;

import java.util.Map;

public class DeleteFromGroupParameters {
	private Integer add_method;
	private String bill_id;
	private String[] detection_account_id;
	private String group_id;
	private Map<String,String> query_params;
	public Integer getAdd_method() {
		return add_method;
	}
	public void setAdd_method(Integer add_method) {
		this.add_method = add_method;
	}
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}
	public String[] getDetection_account_id() {
		return detection_account_id;
	}
	public void setDetection_account_id(String[] detection_account_id) {
		this.detection_account_id = detection_account_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public Map<String, String> getQuery_params() {
		return query_params;
	}
	public void setQuery_params(Map<String, String> query_params) {
		this.query_params = query_params;
	}
}
