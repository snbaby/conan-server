package com.conan.console.server.entity.slave;

public class FinalResult {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column final_result.nick_hash
     *
     * @mbg.generated Wed May 02 15:49:53 CST 2018
     */
    private String nick_hash;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column final_result.result
     *
     * @mbg.generated Wed May 02 15:49:53 CST 2018
     */
    private Short result;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column final_result.nick_hash
     *
     * @return the value of final_result.nick_hash
     *
     * @mbg.generated Wed May 02 15:49:53 CST 2018
     */
    public String getNick_hash() {
        return nick_hash;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column final_result.nick_hash
     *
     * @param nick_hash the value for final_result.nick_hash
     *
     * @mbg.generated Wed May 02 15:49:53 CST 2018
     */
    public void setNick_hash(String nick_hash) {
        this.nick_hash = nick_hash == null ? null : nick_hash.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column final_result.result
     *
     * @return the value of final_result.result
     *
     * @mbg.generated Wed May 02 15:49:53 CST 2018
     */
    public Short getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column final_result.result
     *
     * @param result the value for final_result.result
     *
     * @mbg.generated Wed May 02 15:49:53 CST 2018
     */
    public void setResult(Short result) {
        this.result = result;
    }
}