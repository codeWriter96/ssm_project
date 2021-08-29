package com.bytedance.crm.settings;

import com.bytedance.crm.settings.dao.UserDao;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.workbench.dao.ActivityDao;
import com.bytedance.crm.workbench.dao.ActivityRemarkDao;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.vo.VO_Detail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class AddTest {
    @Autowired
    private ActivityDao activityDao;


    @Test
    public void AddTest01(){
        User user = new User();
        user.setName("钢铁侠");
        Field field = null;
        try {
            field =User.class.getDeclaredField("name");
            field.setAccessible(true);//因为是private修饰的
            String n = (String)field.get(user);
            System.out.println(n);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void AddTest02() throws NullPointerException{
        String activityId ="0b51594541e24359a3a79be473d97342";
        VO_Detail vo_detail = activityDao.selectDetail(activityId);

    }
}
