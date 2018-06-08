package com.conan.console.server.entity.master;

import java.util.Date;

public class CostRecord {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Date created_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Date updated_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.cost_type
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String cost_type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.user_info_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String user_info_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.cost_gold
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double cost_gold;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.remain_gold
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double remain_gold;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cost_record.user_bill_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String user_bill_id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.id
     *
     * @return the value of cost_record.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.id
     *
     * @param id the value for cost_record.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.created_at
     *
     * @return the value of cost_record.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.created_at
     *
     * @param created_at the value for cost_record.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.updated_at
     *
     * @return the value of cost_record.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.updated_at
     *
     * @param updated_at the value for cost_record.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.cost_type
     *
     * @return the value of cost_record.cost_type
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getCost_type() {
        return cost_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.cost_type
     *
     * @param cost_type the value for cost_record.cost_type
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setCost_type(String cost_type) {
        this.cost_type = cost_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.user_info_id
     *
     * @return the value of cost_record.user_info_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getUser_info_id() {
        return user_info_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.user_info_id
     *
     * @param user_info_id the value for cost_record.user_info_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id == null ? null : user_info_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.cost_gold
     *
     * @return the value of cost_record.cost_gold
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getCost_gold() {
        return cost_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.cost_gold
     *
     * @param cost_gold the value for cost_record.cost_gold
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setCost_gold(Double cost_gold) {
        this.cost_gold = cost_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.remain_gold
     *
     * @return the value of cost_record.remain_gold
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getRemain_gold() {
        return remain_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.remain_gold
     *
     * @param remain_gold the value for cost_record.remain_gold
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setRemain_gold(Double remain_gold) {
        this.remain_gold = remain_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cost_record.user_bill_id
     *
     * @return the value of cost_record.user_bill_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getUser_bill_id() {
        return user_bill_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cost_record.user_bill_id
     *
     * @param user_bill_id the value for cost_record.user_bill_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUser_bill_id(String user_bill_id) {
        this.user_bill_id = user_bill_id == null ? null : user_bill_id.trim();
    }
}