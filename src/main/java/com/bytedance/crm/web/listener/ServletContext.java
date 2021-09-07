package com.bytedance.crm.web.listener;

import com.bytedance.crm.settings.dao.DicTypeDao;
import com.bytedance.crm.settings.dao.DicValueDao;
import com.bytedance.crm.settings.domain.DicType;
import com.bytedance.crm.settings.domain.DicValue;
import com.bytedance.crm.untils.WriteJsonUntil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;


public class ServletContext implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String config = "applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(config);
        DicTypeDao dicTypeDao = (DicTypeDao) context.getBean("dicTypeDao");
        DicValueDao dicValueDao = (DicValueDao) context.getBean("dicValueDao");

        List<DicType> list = dicTypeDao.selectAll();
        javax.servlet.ServletContext servletContext = servletContextEvent.getServletContext();
        for(DicType dicType: list){
            String code = dicType.getCode();
            List<DicValue> value= dicValueDao.selectValue(code);
            servletContext.setAttribute(code,value);
        }

        Map<String,String> map = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();
        while(e.hasMoreElements()){
            String key = e.nextElement();
            String value = rb.getString(key);
            map.put(key,value);
        }
        String json = WriteJsonUntil.printJsonObj(map);
        servletContext.setAttribute("possibility",json);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
