package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryUserListParameters {
	
	
	@Size (max=45, message="user_id最大长度为45")
	private String user_id;
	
	@Size (max=255, message="user_nick最大长度为255")
	private String user_nick;
	
	@Size (max=45, message="user_phone最大长度为50")
	private String user_phone;
	
	private Integer activate;
	
	private String register_start_date;
	
	private String register_end_date;
	
	private String login_start_date;
	
	private String login_end_date;
	
	@NotNull(message = "pageNo不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private Integer pageNo;

	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_nick() {
		return user_nick;
	}

	public void setUser_nick(String user_nick) {
		this.user_nick = user_nick;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public Integer getActivate() {
		return activate;
	}

	public void setActivate(Integer activate) {
		this.activate = activate;
	}

	public String getRegister_start_date() {
		return register_start_date;
	}

	public void setRegister_start_date(String register_start_date) {
		this.register_start_date = register_start_date;
	}

	public String getRegister_end_date() {
		return register_end_date;
	}

	public void setRegister_end_date(String register_end_date) {
		this.register_end_date = register_end_date;
	}

	public String getLogin_start_date() {
		return login_start_date;
	}

	public void setLogin_start_date(String login_start_date) {
		this.login_start_date = login_start_date;
	}

	public String getLogin_end_date() {
		return login_end_date;
	}

	public void setLogin_end_date(String login_end_date) {
		this.login_end_date = login_end_date;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
}
