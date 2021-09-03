package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.damain.Clue;
import com.bytedance.crm.workbench.vo.VO_ClueList;
import com.bytedance.crm.workbench.vo.VO_ClueTran;

import java.util.Map;

public interface ClueService {

    String addClue(Clue clue);

    String getClueList(VO_ClueList vo_clueList);

    Map<String, Object> getdetailClue(String id);

    String queryRelation(String clueId);

    String deleteRelation(String id);

    String quertNoRelation(String name,String clueId);

    String createRelation(String[] id,String clueId);

    String getRelationByName(String name, String clueId);

    boolean convert(VO_ClueTran vo_clueTran);
}
