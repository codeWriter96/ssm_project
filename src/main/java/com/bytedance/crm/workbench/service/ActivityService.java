package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.damain.ActivityRemark;
import com.bytedance.crm.workbench.vo.VO_Activity;
import com.bytedance.crm.workbench.vo.VO_PageList;
import com.bytedance.crm.workbench.vo.VO_UpdateActivity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ActivityService {
    Integer addActivity(VO_Activity vo_activity);

    Map<String, Object> queryActivity(VO_PageList vo_pageList);

    Map<String,Object> removeActivityRemark(Integer res , String[] id);

    Integer removeActivity(String[] id);

    Integer queryActivityRemark(String[] id);

    String remove(String[] id);

    String queryActivityAndUser(String activityId);

    String editActivityAndUser(VO_UpdateActivity vo_updateActivity);

    Map<String, Object> getdetailActivity(String activityId);

    String queryRemark(String activityId);

    @Transactional(propagation = Propagation.REQUIRED)
    String removeRemark(String id);


    String addRemark(ActivityRemark activityRemark);

    String editRemark(ActivityRemark activityRemark);
}
