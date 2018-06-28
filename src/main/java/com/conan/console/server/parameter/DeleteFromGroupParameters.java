package com.conan.console.server.parameter;

import java.util.Map;

public class DeleteFromGroupParameters {
	private Integer delete_method;
	private String[] detection_account_id;
	private String delete_from_group_id;
	private Map<String,Object> query_params;
	
	
	public Integer getDelete_method() {
		return delete_method;
	}
	public void setDelete_method(Integer delete_method) {
		this.delete_method = delete_method;
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
