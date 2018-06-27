package com.conan.console.server.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conan.console.server.entity.master.GmDetail;

public interface GmDetailMapper {
    List<GmDetail> selectByDetectionId( @Param("user_info_id") String user_info_id, @Param("detection_account_id") String detection_account_id);
}