package com.bytedance.crm.workbench.aspect;

import com.bytedance.crm.exception.DeleteException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
public class ActivityRemarkDeleteFailed {
    @AfterReturning(value = "execution(* *..service.Impl.ActivityServiceImpl.removeActivityRemark(..))",returning = "map")
    public void activityRemarkDeleteAfterRt(Map<String,Object> map){
        Integer queryActivityRemark= (Integer) map.get("queryActivityRemark");
        Integer deleteActivityRemark = (Integer) map.get("deleteActivityRemark");
        if(queryActivityRemark!=deleteActivityRemark){
            throw new DeleteException("删除失败");
        }
    }
}
