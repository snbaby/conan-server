package com.conan.console.server.mapper.master;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;

public interface DetectionAccountMapper {
	
	long batchScanAccounts(@Param("range_date_start") String range_date_start,@Param("range_date_end") String range_date_end);
	
	List<DetectionAccount> selectByUserGetScanHistoryParameters(
			@Param("userGetScanHistoryParameters") UserGetScanHistoryParameters userGetScanHistoryParameters,
			@Param("user_info_id") String user_info_id, @Param("pageSize") int pageSize);

	List<DetectionAccount> selectByUserGetScanHistoryAllParameters(
			@Param("userGetScanHistoryParameters") UserGetScanHistoryParameters userGetScanHistoryParameters,
			@Param("user_info_id") String user_info_id);

	int selectByUserGetScanHistoryTotalParameters(
			@Param("userGetScanHistoryParameters") UserGetScanHistoryParameters userGetScanHistoryParameters,
			@Param("user_info_id") String user_info_id);

	List<DetectionAccount> selectByRecordIdAndUserInfoId(@Param("recordId") String costRecordId,
			@Param("account_name") String account_name, @Param("account_score_start") String account_score_start,
			@Param("account_score_end") String account_score_end, @Param("user_info_id") String user_info_id,
			@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	int selectByRecordIdAndUserInfoIdTotal(@Param("recordId") String costRecordId,
			@Param("account_name") String account_name, @Param("account_score_start") String account_score_start,
			@Param("account_score_end") String account_score_end, @Param("user_info_id") String user_info_id);

	List<DetectionAccount> selectTopDangersByUserInfoId(@Param("user_info_id") String user_info_id,
			@Param("topNum") int topNum, @Param("lastDate") Date lastDate);

	List<DetectionAccount> selectByUserInfoId(@Param("user_info_id") String user_info_id);

	List<DetectionAccount> selectByRecordId(@Param("recordId") String costRecordId, @Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize);
	
	List<DetectionAccount> selectByRecordIdAndyUserInfoId(@Param("recordId") String costRecordId,@Param("user_info_id") String user_info_id);
	
	List<DetectionAccount> selectByParams(@Param("recordId") String costRecordId,@Param("user_info_id") String user_info_id,@Param("account_name") String account_name,@Param("scan_status") int scan_status);

	int selectByRecordIdTotal(@Param("recordId") String costRecordId);

	int insertList(@Param("recordList") List<DetectionAccount> recordList);

	int selectByUserInfoIdTotal(@Param("user_info_id") String user_info_id);

	int selectDangerByUserInfoIdTotal(@Param("user_info_id") String user_info_id);

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