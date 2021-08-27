package com.bytedance.crm.workbench.aspect;

import com.bytedance.crm.exception.ActivityException;
import com.bytedance.crm.workbench.vo.VO_Activity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ActivitySaveFailed {

    @Before(value = "execution(* *..workbench..ActivityServiceImpl.addActivity(..))")
    public void activityQueryBefore(JoinPoint joinPoint){
        Object[] obj =joinPoint.getArgs();
        VO_Activity vo_activity = (VO_Activity) obj[0];
        if(vo_activity.getStartDate().compareTo(vo_activity.getEndDate())>0) {
            throw new ActivityException("开始、结束日期冲突,创建失败");
        }
    }
    @AfterReturning(value = "execution(* *..workbench..ActivityServiceImpl.addActivity(..))",returning = "integer")
    public void activitySaveAfterRt(Integer integer){
        if(null==integer || 1!=integer){
            throw new ActivityException("创建失败");
        }
    }
}
