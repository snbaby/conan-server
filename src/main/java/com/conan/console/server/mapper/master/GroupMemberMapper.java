package com.conan.console.server.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.GroupMember;

public interface GroupMemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_member
     *
     * @mbg.generated Wed Jun 27 11:27:54 CST 2018
     */
    int deleteByPrimaryKey(String id);
    
    int deleteByGroupId(@Param("group_id") String group_id);
    int deleteByGroupIdAndDetectionList(@Param("group_id") String group_id,@Param("detectionAccountList") List<String> detectionAccountList);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_member
     *
     * @mbg.generated Wed Jun 27 11:27:54 CST 2018
     */
    int insert(GroupMember record);
    
    int insertList(@Param("recordList") List<GroupMember> recordList);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_member
     *
     * @mbg.generated Wed Jun 27 11:27:54 CST 2018
     */
    int insertSelective(GroupMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_member
     *
     * @mbg.generated Wed Jun 27 11:27:54 CST 2018
     */
    GroupMember selectByPrimaryKey(String id);
    
    List<GroupMember> selectByGroupId(String group_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_member
     *
     * @mbg.generated Wed Jun 27 11:27:54 CST 2018
     */
    int updateByPrimaryKeySelective(GroupMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_member
     *
     * @mbg.generated Wed Jun 27 11:27:54 CST 2018
     */
    int updateByPrimaryKey(GroupMember record);
}