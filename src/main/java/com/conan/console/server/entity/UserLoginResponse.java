package com.conan.console.server.entity;

import java.util.Date;

public class UserLoginResponse {
	private int user_info_id;
	private String phone_no;
	private String nick_name;
	private Date created_at;
	private Date updated_at;
	private Date last_login_at;
	private int activate;
	private String user_photo;
	private UserRemainInfo user_remain_info;

	public int getUser_info_id() {
		return user_info_id;
	}

	public void setUser_info_id(int user_info_id) {
		this.user_info_id = user_info_id;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getLast_login_at() {
		return last_login_at;
	}

	public void setLast_login_at(Date last_login_at) {
		this.last_login_at = last_login_at;
	}

	public int getActivate() {
		return activate;
	}

	public void setActivate(int activate) {
		this.activate = activate;
	}

	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

	public UserRemainInfo getUser_remain_info() {
		return user_remain_info;
	}

	public void setUser_remain_info(UserRemainInfo user_remain_info) {
		this.user_remain_info = user_remain_info;
	}

}
