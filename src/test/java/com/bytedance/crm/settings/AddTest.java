package com.bytedance.crm.settings;

import com.bytedance.crm.settings.domain.User;
import org.junit.Test;

import java.lang.reflect.Field;

public class AddTest {
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
        Integer a =null;
        Integer b = null;
        Integer c = a/b;

        System.out.println(12313);
    }
}
