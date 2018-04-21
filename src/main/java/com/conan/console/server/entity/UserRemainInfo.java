package com.conan.console.server.entity;

import java.util.Date;

public class UserRemainInfo {
	private int remain_info_id;
	private Float gold_amount;
	private int gold_coupon;
	private Date created_at;
	private Date updated_at;
	private float scan_cnt;

	public int getRemain_info_id() {
		return remain_info_id;
	}

	public void setRemain_info_id(int remain_info_id) {
		this.remain_info_id = remain_info_id;
	}

	public Float getGold_amount() {
		return gold_amount;
	}

	public void setGold_amount(Float gold_amount) {
		this.gold_amount = gold_amount;
	}

	public int getGold_coupon() {
		return gold_coupon;
	}

	public void setGold_coupon(int gold_coupon) {
		this.gold_coupon = gold_coupon;
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

	public Float getScan_cnt() {
		return this.scan_cnt;
	}

	public void setScan_cnt(int gold_coupon, float gold_amount) {
		this.scan_cnt = this.gold_coupon + this.gold_amount;
	}

}
