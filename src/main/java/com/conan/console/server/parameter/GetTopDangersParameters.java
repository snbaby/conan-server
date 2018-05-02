package com.conan.console.server.parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetTopDangersParameters {
	@NotNull(message = "topNum不能为空")
	@Min(value = 1, message = "topNum最小为1")
	@Max(value = 1000, message = "topNum最大为1000")
	private int topNum;

	@NotNull(message = "lastDays不能为空")
	@Min(value = 0, message = "lastDays最小为0")
	@Max(value = 3650, message = "lastDays最大为3650")//10年
	private int lastDays;

	public int getTopNum() {
		return topNum;
	}

	public void setTopNum(int topNum) {
		this.topNum = topNum;
	}

	public int getLastDays() {
		return lastDays;
	}

	public void setLastDays(int lastDays) {
		this.lastDays = lastDays;
	}

	
}
