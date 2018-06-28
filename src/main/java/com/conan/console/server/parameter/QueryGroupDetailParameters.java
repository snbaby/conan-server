package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

public class QueryGroupDetailParameters {
	private String group_id;
	
	private String account_name;
	
	private Integer scan_status;
	
	private String scan_time_start;
	
	private String scan_time_end;
	
	@NotNull(message = "pageNo不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private int pageNo;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		if(StringUtils.isBlank(group_id)) {
			this.group_id = null;	
		}else {
			this.group_id = group_id;
		}
		
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

	public Integer getScan_status() {
		return scan_status;
	}

	public void setScan_status(Integer scan_status) {
		this.scan_status = scan_status;
	}

	public String getScan_time_start() {
		return scan_time_start;
	}

	public void setScan_time_start(String scan_time_start) {
		if(StringUtils.isBlank(scan_time_start)) {
			this.scan_time_start = null;	
		}else {
			this.scan_time_start = scan_time_start;
		}
	}

	public String getScan_time_end() {
		return scan_time_end;
	}

	public void setScan_time_end(String scan_time_end) {
		if(StringUtils.isBlank(scan_time_end)) {
			this.scan_time_end = null;	
		}else {
			this.scan_time_end = scan_time_end;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
