package com.conan.console.server.entity.master;

import java.util.Date;

public class DetectionAccount {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Date created_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Date updated_at;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.cost_record_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String cost_record_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.account_name
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String account_name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.account_score
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double account_score;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.detail_score0
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double detail_score0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.detail_score1
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double detail_score1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.detail_score2
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double detail_score2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.detail_score3
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double detail_score3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.detail_score4
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private Double detail_score4;
    
    private Double register_info_score;
    private Double identify_info_score;
    private Double background_info_score;
    private Double trade_process_score;
    private Double trade_habit_score;
    private Double logistics_character_score;
    private Double account_history_score;
    private Double account_growup_score;
    private Double trade_frequency_score;
    private Double like_info_score;
    private Double activity_score0;
    private Double activity_score1;
    private Double activity_score2;
    
    
    
    private Double cost;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column detection_account.user_info_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    private String user_info_id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.id
     *
     * @return the value of detection_account.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getId() {
        return id;
    }

    
    
    public Double getRegister_info_score() {
		return register_info_score;
	}



	public void setRegister_info_score(Double register_info_score) {
		this.register_info_score = register_info_score;
	}



	public Double getIdentify_info_score() {
		return identify_info_score;
	}



	public void setIdentify_info_score(Double identify_info_score) {
		this.identify_info_score = identify_info_score;
	}



	public Double getBackground_info_score() {
		return background_info_score;
	}



	public void setBackground_info_score(Double background_info_score) {
		this.background_info_score = background_info_score;
	}



	public Double getTrade_process_score() {
		return trade_process_score;
	}



	public void setTrade_process_score(Double trade_process_score) {
		this.trade_process_score = trade_process_score;
	}



	public Double getTrade_habit_score() {
		return trade_habit_score;
	}



	public void setTrade_habit_score(Double trade_habit_score) {
		this.trade_habit_score = trade_habit_score;
	}



	public Double getLogistics_character_score() {
		return logistics_character_score;
	}



	public void setLogistics_character_score(Double logistics_character_score) {
		this.logistics_character_score = logistics_character_score;
	}



	public Double getAccount_history_score() {
		return account_history_score;
	}



	public void setAccount_history_score(Double account_history_score) {
		this.account_history_score = account_history_score;
	}



	public Double getAccount_growup_score() {
		return account_growup_score;
	}



	public void setAccount_growup_score(Double account_growup_score) {
		this.account_growup_score = account_growup_score;
	}



	public Double getTrade_frequency_score() {
		return trade_frequency_score;
	}



	public void setTrade_frequency_score(Double trade_frequency_score) {
		this.trade_frequency_score = trade_frequency_score;
	}



	public Double getLike_info_score() {
		return like_info_score;
	}



	public void setLike_info_score(Double like_info_score) {
		this.like_info_score = like_info_score;
	}



	/**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.id
     *
     * @param id the value for detection_account.id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.created_at
     *
     * @return the value of detection_account.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.created_at
     *
     * @param created_at the value for detection_account.created_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.updated_at
     *
     * @return the value of detection_account.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.updated_at
     *
     * @param updated_at the value for detection_account.updated_at
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.cost_record_id
     *
     * @return the value of detection_account.cost_record_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getCost_record_id() {
        return cost_record_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.cost_record_id
     *
     * @param cost_record_id the value for detection_account.cost_record_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setCost_record_id(String cost_record_id) {
        this.cost_record_id = cost_record_id == null ? null : cost_record_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.account_name
     *
     * @return the value of detection_account.account_name
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getAccount_name() {
        return account_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.account_name
     *
     * @param account_name the value for detection_account.account_name
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setAccount_name(String account_name) {
        this.account_name = account_name == null ? null : account_name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.account_score
     *
     * @return the value of detection_account.account_score
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getAccount_score() {
        return account_score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.account_score
     *
     * @param account_score the value for detection_account.account_score
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setAccount_score(Double account_score) {
        this.account_score = account_score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.detail_score0
     *
     * @return the value of detection_account.detail_score0
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getDetail_score0() {
        return detail_score0;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.detail_score0
     *
     * @param detail_score0 the value for detection_account.detail_score0
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setDetail_score0(Double detail_score0) {
        this.detail_score0 = detail_score0;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.detail_score1
     *
     * @return the value of detection_account.detail_score1
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getDetail_score1() {
        return detail_score1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.detail_score1
     *
     * @param detail_score1 the value for detection_account.detail_score1
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setDetail_score1(Double detail_score1) {
        this.detail_score1 = detail_score1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.detail_score2
     *
     * @return the value of detection_account.detail_score2
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getDetail_score2() {
        return detail_score2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.detail_score2
     *
     * @param detail_score2 the value for detection_account.detail_score2
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setDetail_score2(Double detail_score2) {
        this.detail_score2 = detail_score2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.detail_score3
     *
     * @return the value of detection_account.detail_score3
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getDetail_score3() {
        return detail_score3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.detail_score3
     *
     * @param detail_score3 the value for detection_account.detail_score3
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setDetail_score3(Double detail_score3) {
        this.detail_score3 = detail_score3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.detail_score4
     *
     * @return the value of detection_account.detail_score4
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public Double getDetail_score4() {
        return detail_score4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.detail_score4
     *
     * @param detail_score4 the value for detection_account.detail_score4
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setDetail_score4(Double detail_score4) {
        this.detail_score4 = detail_score4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column detection_account.user_info_id
     *
     * @return the value of detection_account.user_info_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public String getUser_info_id() {
        return user_info_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column detection_account.user_info_id
     *
     * @param user_info_id the value for detection_account.user_info_id
     *
     * @mbg.generated Mon Apr 23 00:59:28 CST 2018
     */
    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id == null ? null : user_info_id.trim();
    }

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}



	public Double getActivity_score0() {
		return activity_score0;
	}



	public void setActivity_score0(Double activity_score0) {
		this.activity_score0 = activity_score0;
	}



	public Double getActivity_score1() {
		return activity_score1;
	}



	public void setActivity_score1(Double activity_score1) {
		this.activity_score1 = activity_score1;
	}



	public Double getActivity_score2() {
		return activity_score2;
	}



	public void setActivity_score2(Double activity_score2) {
		this.activity_score2 = activity_score2;
	}
    
    
}