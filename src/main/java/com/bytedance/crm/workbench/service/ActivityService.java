package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.vo.VO_Activity;
import com.bytedance.crm.workbench.vo.VO_PageList;

import java.util.Map;

public interface ActivityService {
    Integer addActivity(VO_Activity vo_activity);

    Map<String, Object> queryActivity(VO_PageList vo_pageList);

    Map<String,Object> removeActivityRemark(Integer res , String[] id);

    Integer removeActivity(String[] id);

    Integer queryActivityRemark(String[] id);

    String remove(String[] id);
}
