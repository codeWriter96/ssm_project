package com.bytedance.crm.web.listener;

import com.bytedance.crm.settings.dao.DicTypeDao;
import com.bytedance.crm.settings.dao.DicValueDao;
import com.bytedance.crm.settings.domain.DicType;
import com.bytedance.crm.settings.domain.DicValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;


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

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
