package com.bytedance.crm.workbench.aspect;

import com.bytedance.crm.exception.ActivityException;
import com.bytedance.crm.workbench.vo.VO_PageList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
public class ActivityQueryFailed {
    @Before(value = "execution(* *..Impl.ActivityServiceImpl.queryActivity(..))")
    public void activityQueryBefore(JoinPoint joinPoint){
        Object[] obj = joinPoint.getArgs();
        VO_PageList vo_pageList= (VO_PageList) obj[0];
        if(null == vo_pageList.getPageNo()||null == vo_pageList.getPageSize()){
            throw new ActivityException("页面显示条数错误");
        }
        if(null != vo_pageList.getStartDate()& !"".equals(vo_pageList.getStartDate()) &
                null != vo_pageList.getEndDate()&!"".equals(vo_pageList.getEndDate())) {
            if(vo_pageList.getStartDate().compareTo(vo_pageList.getEndDate())>0){
                throw new ActivityException("开始、结束日期冲突,查询失败");
            }

        }
    }

    @AfterReturning(value = "execution(* *..Impl.ActivityServiceImpl.queryActivity(..))",returning = "map")
    public void activityQueryAfterRt(Map<String, Object> map){
        if(null== map.get("total")|| (Integer) map.get("total")==0 ){
            throw new ActivityException("查询失败,没有数据");
        }
    }
}
