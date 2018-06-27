package com.conan.console.server.mapper.master;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.RechargeBill;

public interface RechargeBillMapper {

	List<RechargeBill> selectByUserIdAndRechargeStatus(@Param("recharge_status") int recharge_status,
			@Param("user_info_id") String user_info_id, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	int selectByUserIdAndRechargeStatusTotal(@Param("recharge_status") int recharge_status,
			@Param("user_info_id") String user_info_id);
	
	long selectNewRechargeCntTotal();
	long selectTdRechargeAgreeCntTotal(@Param("td") Date td);
	long selectTdRechargeAgreeAmountTotal(@Param("td") Date td);
	long selectTdRechargeAgreeGoldTotal(@Param("td") Date td);
	
	long selectTtRechargeAgreeAmountTotal();
	long selectTtRechargeAgreeGoldTotal();
	

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table recharge_bill
	 *
	 * @mbg.generated Mon Apr 30 00:35:43 CST 2018
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table recharge_bill
	 *
	 * @mbg.generated Mon Apr 30 00:35:43 CST 2018
	 */
	int insert(RechargeBill record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table recharge_bill
	 *
	 * @mbg.generated Mon Apr 30 00:35:43 CST 2018
	 */
	int insertSelective(RechargeBill record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table recharge_bill
	 *
	 * @mbg.generated Mon Apr 30 00:35:43 CST 2018
	 */
	RechargeBill selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table recharge_bill
	 *
	 * @mbg.generated Mon Apr 30 00:35:43 CST 2018
	 */
	int updateByPrimaryKeySelective(RechargeBill record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table recharge_bill
	 *
	 * @mbg.generated Mon Apr 30 00:35:43 CST 2018
	 */
	int updateByPrimaryKey(RechargeBill record);
}