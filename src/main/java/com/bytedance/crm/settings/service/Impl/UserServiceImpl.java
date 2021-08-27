package com.bytedance.crm.settings.service.Impl;
import com.bytedance.crm.settings.dao.AdminDao;
import com.bytedance.crm.settings.dao.UserDao;
import com.bytedance.crm.settings.domain.Admin;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.settings.service.UserService;
import com.bytedance.crm.settings.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    AdminDao adminDao;
    @Override
    public Map<String,Object> loginUser(LoginUser loginUser) {
        User user = userDao.loginUser(loginUser.getLoginAct(),loginUser.getLoginPwd());
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("loginUser",loginUser);
        return map;
    }

    @Override
    public Admin getAdminUser(String id) {
        return adminDao.selectAdminUser(id);
    }
}
