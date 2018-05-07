package com.conan.console.server.parameter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QueryRechargeListParameters {
	
	
	@Size (max=45, message="user_id最大长度为45")
	private String user_id;
	
	@Size (max=45, message="user_phone最大长度为50")
	private String user_phone;
	
	private String created_start_date;
	
	private String created_end_date;
	
	private Integer recharge_type;
	
	private Integer recharge_status;
	
	private Float rmb_amount_start;
	
	private Float rmb_amount_end;
	
	private String recharge_id;
	
	@NotNull(message = "pageNo不能为空")
	@Min(value = 1, message = "pageNo最小为1")
	private Integer pageNo;

	
}

