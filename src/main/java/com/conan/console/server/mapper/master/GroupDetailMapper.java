package com.conan.console.server.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.GroupDetail;
import com.conan.console.server.parameter.QueryGroupDetailParameters;

public interface GroupDetailMapper {
    
    List<GroupDetail> selectByQueryGroupDetailParameters(
			@Param("queryGroupDetailParameters") QueryGroupDetailParameters queryGroupDetailParameters,
			@Param("pageSize") int pageSize);
	
	int selectByQueryGroupDetailParametersTotal(
			@Param("queryCostListParameters") QueryGroupDetailParameters queryGroupDetailParameters);
}