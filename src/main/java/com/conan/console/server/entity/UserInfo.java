package com.conan.console.server.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(Include.NON_NULL) 
public class UserInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.phone_no
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String phone_no;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.nick_name
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String nick_name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Date created_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Date updated_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.last_login_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Date last_login_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.activate
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Byte activate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_photo
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String user_photo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.id
     *
     * @return the value of user_info.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.id
     *
     * @param id the value for user_info.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.phone_no
     *
     * @return the value of user_info.phone_no
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getPhone_no() {
        return phone_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.phone_no
     *
     * @param phone_no the value for user_info.phone_no
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no == null ? null : phone_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.nick_name
     *
     * @return the value of user_info.nick_name
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getNick_name() {
        return nick_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.nick_name
     *
     * @param nick_name the value for user_info.nick_name
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name == null ? null : nick_name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.created_at
     *
     * @return the value of user_info.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.created_at
     *
     * @param created_at the value for user_info.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.updated_at
     *
     * @return the value of user_info.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.updated_at
     *
     * @param updated_at the value for user_info.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.last_login_at
     *
     * @return the value of user_info.last_login_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getLast_login_at() {
        return last_login_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.last_login_at
     *
     * @param last_login_at the value for user_info.last_login_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setLast_login_at(Date last_login_at) {
        this.last_login_at = last_login_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.activate
     *
     * @return the value of user_info.activate
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Byte getActivate() {
        return activate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.activate
     *
     * @param activate the value for user_info.activate
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setActivate(Byte activate) {
        this.activate = activate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_photo
     *
     * @return the value of user_info.user_photo
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getUser_photo() {
        return user_photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_photo
     *
     * @param user_photo the value for user_info.user_photo
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo == null ? null : user_photo.trim();
    }
}