package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.Activity;
import com.bytedance.crm.workbench.damain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {


    List<Activity> selectRelation(String clueId);

    Integer deleteRelation(String id);

    List<Activity> selectNoRelation(@Param("name") String name,@Param("clueId")String clueId);

    Integer addRelation(@Param("id") String id,@Param("clueId") String clueId,@Param("activityId") String activityId);

    List<Activity> selectRelationByName(@Param("name")String name,@Param("clueId") String clueId);

    List<ClueActivityRelation> getList(String clueId);

    Integer deleteRelationByClueId(String clueId);
}
