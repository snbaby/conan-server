package com.conan.console.server.parameter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

public class PostRechargeReqParameters {
	@NotNull(message = "recharge_type不能为空")
	@Min(value = 1, message = "recharge_type最小为1")
	@Max(value = 2, message = "recharge_type最大为2")
	private int recharge_type;

	@NotNull(message = "recharge_amount不能为空")
	@DecimalMin(value = "1.0", message = "recharge_amount最小为1.0")
	private double recharge_amount;

	@Size(max = 255, message = "comment最大长度为255")
	private String comment;
	
	@NotNull(message = "capture_id不能为空")
	@NotBlank(message = "capture_id不能为空")
	@NotEmpty(message = "capture_id不能为空")
	@Size(max = 255, message = "capture_id最大长度为255")
	private String capture_id;

	public int getRecharge_type() {
		return recharge_type;
	}

	public void setRecharge_type(int recharge_type) {
		this.recharge_type = recharge_type;
	}

	public double getRecharge_amount() {
		return recharge_amount;
	}

	public void setRecharge_amount(double recharge_amount) {
		this.recharge_amount = recharge_amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCapture_id() {
		return capture_id;
	}

	public void setCapture_id(String capture_id) {
		this.capture_id = capture_id;
	}
	
	
}
