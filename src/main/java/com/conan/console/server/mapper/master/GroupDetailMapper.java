package com.conan.console.server.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.GroupDetail;
import com.conan.console.server.parameter.QueryGroupDetailParameters;

public interface GroupDetailMapper {
    
    List<GroupDetail> selectByQueryGroupDetailParameters(
    		@Param("user_info_id") String user_info_id,
			@Param("queryGroupDetailParameters") QueryGroupDetailParameters queryGroupDetailParameters,
			@Param("pageSize") int pageSize);
	
	int selectByQueryGroupDetailParametersTotal(
			@Param("user_info_id") String user_info_id,
			@Param("queryCostListParameters") QueryGroupDetailParameters queryGroupDetailParameters);
	
	int selectByQueryGroupDetailParametersScanStatus(
			@Param("user_info_id") String user_info_id,
			@Param("scan_status") int scan_status,
			@Param("queryCostListParameters") QueryGroupDetailParameters queryGroupDetailParameters);
	
	List<GroupDetail> selectByUserInfoIdAndGroupId(
    		@Param("user_info_id") String user_info_id,
			@Param("group_id") String group_id);
}