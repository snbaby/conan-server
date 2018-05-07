package com.conan.console.server.entity.master;

import java.util.Date;

public class QueryRecharge {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.id
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.created_at
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Date created_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.updated_at
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Date updated_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.photo
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private String photo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.comment
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.rmb_amount
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Float rmb_amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.gold_amount
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Float gold_amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.gold_coupon
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Float gold_coupon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.gold_total
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Float gold_total;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.recharge_status
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private String recharge_status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.user_info_id
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private String user_info_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.phone_no
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private String phone_no;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.verified_time
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Date verified_time;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column query_recharge.success_time
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    private Date success_time;
    
    private String recharge_type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.id
     *
     * @return the value of query_recharge.id
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.id
     *
     * @param id the value for query_recharge.id
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.created_at
     *
     * @return the value of query_recharge.created_at
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.created_at
     *
     * @param created_at the value for query_recharge.created_at
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.updated_at
     *
     * @return the value of query_recharge.updated_at
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.updated_at
     *
     * @param updated_at the value for query_recharge.updated_at
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.photo
     *
     * @return the value of query_recharge.photo
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.photo
     *
     * @param photo the value for query_recharge.photo
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.comment
     *
     * @return the value of query_recharge.comment
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.comment
     *
     * @param comment the value for query_recharge.comment
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.rmb_amount
     *
     * @return the value of query_recharge.rmb_amount
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Float getRmb_amount() {
        return rmb_amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.rmb_amount
     *
     * @param rmb_amount the value for query_recharge.rmb_amount
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setRmb_amount(Float rmb_amount) {
        this.rmb_amount = rmb_amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.gold_amount
     *
     * @return the value of query_recharge.gold_amount
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Float getGold_amount() {
        return gold_amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.gold_amount
     *
     * @param gold_amount the value for query_recharge.gold_amount
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setGold_amount(Float gold_amount) {
        this.gold_amount = gold_amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.gold_coupon
     *
     * @return the value of query_recharge.gold_coupon
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Float getGold_coupon() {
        return gold_coupon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.gold_coupon
     *
     * @param gold_coupon the value for query_recharge.gold_coupon
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setGold_coupon(Float gold_coupon) {
        this.gold_coupon = gold_coupon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.gold_total
     *
     * @return the value of query_recharge.gold_total
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Float getGold_total() {
        return gold_total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.gold_total
     *
     * @param gold_total the value for query_recharge.gold_total
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setGold_total(Float gold_total) {
        this.gold_total = gold_total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.recharge_status
     *
     * @return the value of query_recharge.recharge_status
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public String getRecharge_status() {
        return recharge_status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.recharge_status
     *
     * @param recharge_status the value for query_recharge.recharge_status
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setRecharge_status(String recharge_status) {
        this.recharge_status = recharge_status == null ? null : recharge_status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.user_info_id
     *
     * @return the value of query_recharge.user_info_id
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public String getUser_info_id() {
        return user_info_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.user_info_id
     *
     * @param user_info_id the value for query_recharge.user_info_id
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id == null ? null : user_info_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.phone_no
     *
     * @return the value of query_recharge.phone_no
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public String getPhone_no() {
        return phone_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.phone_no
     *
     * @param phone_no the value for query_recharge.phone_no
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no == null ? null : phone_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.verified_time
     *
     * @return the value of query_recharge.verified_time
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Date getVerified_time() {
        return verified_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.verified_time
     *
     * @param verified_time the value for query_recharge.verified_time
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setVerified_time(Date verified_time) {
        this.verified_time = verified_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column query_recharge.success_time
     *
     * @return the value of query_recharge.success_time
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public Date getSuccess_time() {
        return success_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column query_recharge.success_time
     *
     * @param success_time the value for query_recharge.success_time
     *
     * @mbg.generated Tue May 08 00:49:28 CST 2018
     */
    public void setSuccess_time(Date success_time) {
        this.success_time = success_time;
    }

	public String getRecharge_type() {
		return recharge_type;
	}

	public void setRecharge_type(String recharge_type) {
		this.recharge_type = recharge_type;
	}
}