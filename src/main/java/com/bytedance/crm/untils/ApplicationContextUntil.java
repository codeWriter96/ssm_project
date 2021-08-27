package com.bytedance.crm.untils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUntil {
    public static Object GetApplicationContext(String string){
        String config = "applicationContext.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        return ac.getBean(string);
    }
}
