package com.conan.console.server.parameter;

import java.util.Map;

public class DeleteFromGroupParameters {
	private Integer add_method;
	private String[] detection_account_id;
	private String delete_from_group_id;
	private Map<String,Object> query_params;
	public Integer getAdd_method() {
		return add_method;
	}
	public void setAdd_method(Integer add_method) {
		this.add_method = add_method;
	}
	public String[] getDetection_account_id() {
		return detection_account_id;
	}
	public void setDetection_account_id(String[] detection_account_id) {
		this.detection_account_id = detection_account_id;
	}
	public String getDelete_from_group_id() {
		return delete_from_group_id;
	}
	public void setDelete_from_group_id(String delete_from_group_id) {
		this.delete_from_group_id = delete_from_group_id;
	}
	public Map<String, Object> getQuery_params() {
		return query_params;
	}
	public void setQuery_params(Map<String, Object> query_params) {
		this.query_params = query_params;
	}
	
	
}
