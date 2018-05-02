package com.conan.console.server.mapper.master;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;

public interface DetectionAccountMapper {
	List<DetectionAccount> selectByUserGetScanHistoryParameters(
			@Param("userGetScanHistoryParameters") UserGetScanHistoryParameters userGetScanHistoryParameters,
			@Param("user_info_id") String user_info_id, @Param("pageSize") int pageSize);

	List<DetectionAccount> selectByRecordIdAndUserInfoId(@Param("costRecordId") String costRecordId,
			@Param("user_info_id") String user_info_id, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	List<DetectionAccount> selectTopDangersByUserInfoId(@Param("user_info_id") String user_info_id, @Param("topNum")int topNum,
			@Param("lastDate")Date lastDate);

	List<DetectionAccount> selectByRecordId(@Param("recordId") String recordId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table detection_account
	 *
	 * @mbg.generated Mon Apr 23 00:59:28 CST 2018
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table detection_account
	 *
	 * @mbg.generated Mon Apr 23 00:59:28 CST 2018
	 */
	int insert(DetectionAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table detection_account
	 *
	 * @mbg.generated Mon Apr 23 00:59:28 CST 2018
	 */
	int insertSelective(DetectionAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table detection_account
	 *
	 * @mbg.generated Mon Apr 23 00:59:28 CST 2018
	 */
	DetectionAccount selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table detection_account
	 *
	 * @mbg.generated Mon Apr 23 00:59:28 CST 2018
	 */
	int updateByPrimaryKeySelective(DetectionAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table detection_account
	 *
	 * @mbg.generated Mon Apr 23 00:59:28 CST 2018
	 */
	int updateByPrimaryKey(DetectionAccount record);
}