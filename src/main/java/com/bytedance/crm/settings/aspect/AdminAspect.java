package com.bytedance.crm.settings.aspect;

import com.bytedance.crm.exception.AuthorityException;
import com.bytedance.crm.settings.domain.Admin;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AdminAspect {
    @AfterReturning(value = "execution(* *..Impl.UserServiceImpl.getAdminUser(..))",returning = "admin")
    public void authority(Admin admin){
        if(null == admin){
            throw new AuthorityException("您无权执行此操作");
        }
    }
}
