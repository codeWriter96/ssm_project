package com.bytedance.crm.settings.web.controller;

import com.bytedance.crm.exception.AuthorityException;
import com.bytedance.crm.exception.LoginException;
import com.bytedance.crm.settings.domain.Admin;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.settings.service.UserService;
import com.bytedance.crm.settings.vo.LoginUser;
import com.bytedance.crm.untils.MD5Util;
import com.bytedance.crm.untils.WriteJsonUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/setting/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/logIn.do",method = POST)
    @ResponseBody
    public Object logIn(String loginAct, String loginPwd, HttpServletRequest request){
        String MD5 = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        LoginUser loginUser = new LoginUser();
        loginUser.setIp(ip);
        loginUser.setLoginAct(loginAct);
        loginUser.setLoginPwd(MD5);
        String json = null;
        try {
            Map<String,Object> map = userService.loginUser(loginUser);
            User user = (User) map.get("user");

            request.getSession().setAttribute("user",user);
            json = WriteJsonUntil.printJsonFlag(true);
        } catch (LoginException e) {
            e.printStackTrace();
            String msg =e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }


    @RequestMapping(value = "/authorityCheck.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object authorityCheck(Admin admin, HttpServletRequest request){
        String s = admin.getOwner();
        String id = ((User)request.getSession().getAttribute("user")).getId();
        String json =null;
        try {
            userService.getAdminUser(id);

            json = WriteJsonUntil.printJsonFlag(true);
        } catch (AuthorityException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }

        return json;
    }

    @RequestMapping(value = "/getOwners.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getOwners(){
        return userService.queryOwners();
    }
}
