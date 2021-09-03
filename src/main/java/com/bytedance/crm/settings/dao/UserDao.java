package com.bytedance.crm.settings.dao;

import com.bytedance.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User loginUser(@Param("loginAct")String loginAct, @Param("loginPwd") String loginPwd);
    List<User> selectUserName();
}
