package com.conan.console.server.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.QueryRecharge;
import com.conan.console.server.parameter.QueryRechargeListParameters;

public interface QueryRechargeMapper {
	List<QueryRecharge> selectByQueryRechargeListParameters(
			@Param("queryRechargeListParameters") QueryRechargeListParameters queryRechargeListParameters,
			@Param("pageSize") int pageSize);
	
	int selectByQueryRechargeListParametersTotal(
			@Param("queryRechargeListParameters") QueryRechargeListParameters queryRechargeListParameters);
}