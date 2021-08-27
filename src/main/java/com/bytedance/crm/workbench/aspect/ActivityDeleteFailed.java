package com.bytedance.crm.workbench.aspect;

import com.bytedance.crm.exception.DeleteException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect

public class ActivityDeleteFailed {
    @AfterReturning(value = "execution(* *..Impl.ActivityServiceImpl.removeActivity(..))",returning = "res")
    public void activityDeleteAfterRt(Integer res){
        if(!(res >0)){
            throw new DeleteException("删除失败");
        }
    }

}
