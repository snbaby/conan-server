package com.conan.console.server.mapper;

import com.conan.console.server.entity.RechargeBill;

public interface RechargeBillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recharge_bill
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recharge_bill
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    int insert(RechargeBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recharge_bill
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    int insertSelective(RechargeBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recharge_bill
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    RechargeBill selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recharge_bill
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    int updateByPrimaryKeySelective(RechargeBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recharge_bill
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    int updateByPrimaryKey(RechargeBill record);
}