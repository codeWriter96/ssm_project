package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.vo.*;

import java.util.List;


public interface ActivityDao {
    Integer insertActivity(VO_Activity vo_activity);

    List<VO_Activity> selectActivity(VO_PageList vo_pageList);

    Integer countSelectActivity(VO_PageList vo_pageList);

    Integer deleteActivity(String[] id);

    VO_UpdateActivity selectActivityAndUser(String activityId);

    Integer updateActivityAndUser(VO_UpdateActivity vo_updateActivity);

    VO_Detail selectDetail(String activityId);

    String selectUserNameEditBy(String editBy);

    String selectUserNameCreateBy(String createBy);

    Integer countAll();

    List<VO_ActivitiyCounts> selectNamesAndCount();
}
