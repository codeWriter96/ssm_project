package com.bytedance.crm.settings.dao;

import com.bytedance.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User loginUser(@Param("loginAct")String loginAct, @Param("loginPwd") String loginPwd);
    User selectUser(@Param("userId")String userId);

}
