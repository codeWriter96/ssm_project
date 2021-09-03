package com.bytedance.crm.settings.service.Impl;
import com.bytedance.crm.exception.QueryException;
import com.bytedance.crm.settings.dao.AdminDao;
import com.bytedance.crm.settings.dao.UserDao;
import com.bytedance.crm.settings.domain.Admin;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.settings.service.UserService;
import com.bytedance.crm.settings.vo.LoginUser;
import com.bytedance.crm.untils.WriteJsonUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;
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

    @Override
    public String queryOwners() {
        String json = null;
        Map<String,Object> map =new HashMap<>();
        try{
            List<User> list = userDao.selectUserName();
            if(0==list.size()){
                throw new QueryException("查询失败，无记录");
            }

            map.put("success",true);
            map.put("list",list);
        }catch (QueryException e){
            String msg = e.getMessage();
            map.put("success",true);
            map.put("msg",msg);
        }
        json = WriteJsonUntil.printJsonObj(map);
        return json;
    }
}
