package com.conan.console.server.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.QueryCost;
import com.conan.console.server.parameter.QueryCostListParameters;

public interface QueryCostMapper {
	List<QueryCost> selectByQueryCostListParameters(
			@Param("queryCostListParameters") QueryCostListParameters queryCostListParameters,
			@Param("pageSize") int pageSize);
	
	List<QueryCost> selectByQueryCostExcelParameters(
			@Param("queryCostListParameters") QueryCostListParameters queryCostListParameters);
	
	int selectByQueryCostListParametersTotal(
			@Param("queryCostListParameters") QueryCostListParameters queryCostListParameters);
}