package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.Activity;
import com.bytedance.crm.workbench.damain.Clue;
import com.bytedance.crm.workbench.vo.VO_ClueList;
import com.bytedance.crm.workbench.vo.VO_ClueTran;

import java.util.List;

public interface ClueDao {


    Integer insertClue(Clue clue);

    List<Clue> selectClue(VO_ClueList vo_clueList);

    Clue selectDetail(String id);

    Clue getDetail(String clueId);

    Integer deleteClueById(String clueId);
}
