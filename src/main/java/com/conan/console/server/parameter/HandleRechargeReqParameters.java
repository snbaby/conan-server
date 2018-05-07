package com.conan.console.server.parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class HandleRechargeReqParameters {
	
	@NotNull(message="recharge_id不能为空")
	@NotBlank(message="recharge_id不能为空")
	@NotEmpty(message="recharge_id不能为空")
	@Size(max = 45, message = "recharge_id长度最大长度为45")
	private String recharge_id;
	
	@NotNull(message="action不能为空")
	@NotBlank(message="action不能为空")
	@NotEmpty(message="action不能为空")
	@Size(max = 5, message = "action长度最大长度为5")
	private String action;
	
	@NotNull(message="reason不能为空")
	@NotBlank(message="reason不能为空")
	@NotEmpty(message="reason不能为空")
	@Size(max = 255, message = "reason长度最大长度为5")
	private String reason;

	public String getRecharge_id() {
		return recharge_id;
	}

	public void setRecharge_id(String recharge_id) {
		this.recharge_id = recharge_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	
}
