package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.vo.VO_Activity;
import com.bytedance.crm.workbench.vo.VO_PageList;

import java.util.List;


public interface ActivityDao {
    Integer insertActivity(VO_Activity vo_activity);

    List<VO_Activity> selectActivity(VO_PageList vo_pageList);

    Integer countSelectActivity(VO_PageList vo_pageList);

    Integer deleteActivity(String[] id);
}
