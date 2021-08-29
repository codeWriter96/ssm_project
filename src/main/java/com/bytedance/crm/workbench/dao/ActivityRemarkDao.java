package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.ActivityRemark;

import java.util.List;


public interface ActivityRemarkDao {
   Integer countSelectActivityRemark(String[] id);

    Integer deleteActivityRemark(String[] id);

    List<ActivityRemark> selectActivityRemark(String activityId);

    Integer deleteRemark(String remarkId);

    Integer addRemark(ActivityRemark activityRemark);

    Integer updateRemark(ActivityRemark activityRemark);
}
