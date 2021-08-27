package com.bytedance.crm.settings.service;

import com.bytedance.crm.settings.domain.Admin;
import com.bytedance.crm.settings.vo.LoginUser;

import java.util.Map;

public interface UserService {
    Map<String,Object> loginUser(LoginUser loginUser);

    Admin getAdminUser(String id);
}
