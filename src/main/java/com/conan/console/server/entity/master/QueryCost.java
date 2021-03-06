package com.conan.console.server.entity.master;

import java.util.Date;

public class QueryCost {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.id
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.created_at
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private Date created_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.updated_at
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private Date updated_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.cost_type
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private String cost_type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.cost_gold
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private Double cost_gold;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.remain_gold
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private Double remain_gold;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.user_info_id
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private String user_info_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_cost.phone_no
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    private String phone_no;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.id
     *
     * @return the value of query_cost.id
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.id
     *
     * @param id the value for query_cost.id
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.created_at
     *
     * @return the value of query_cost.created_at
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.created_at
     *
     * @param created_at the value for query_cost.created_at
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.updated_at
     *
     * @return the value of query_cost.updated_at
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.updated_at
     *
     * @param updated_at the value for query_cost.updated_at
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.cost_type
     *
     * @return the value of query_cost.cost_type
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public String getCost_type() {
        return cost_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.cost_type
     *
     * @param cost_type the value for query_cost.cost_type
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setCost_type(String cost_type) {
        this.cost_type = cost_type == null ? null : cost_type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.cost_gold
     *
     * @return the value of query_cost.cost_gold
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public Double getCost_gold() {
        return cost_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.cost_gold
     *
     * @param cost_gold the value for query_cost.cost_gold
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setCost_gold(Double cost_gold) {
        this.cost_gold = cost_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.remain_gold
     *
     * @return the value of query_cost.remain_gold
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public Double getRemain_gold() {
        return remain_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.remain_gold
     *
     * @param remain_gold the value for query_cost.remain_gold
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setRemain_gold(Double remain_gold) {
        this.remain_gold = remain_gold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.user_info_id
     *
     * @return the value of query_cost.user_info_id
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public String getUser_info_id() {
        return user_info_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.user_info_id
     *
     * @param user_info_id the value for query_cost.user_info_id
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id == null ? null : user_info_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_cost.phone_no
     *
     * @return the value of query_cost.phone_no
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public String getPhone_no() {
        return phone_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_cost.phone_no
     *
     * @param phone_no the value for query_cost.phone_no
     *
     * @mbg.generated Tue May 08 01:33:39 CST 2018
     */
    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no == null ? null : phone_no.trim();
    }
}