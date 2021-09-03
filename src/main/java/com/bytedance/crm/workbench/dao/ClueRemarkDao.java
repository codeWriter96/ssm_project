package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getList(String clueId);

    Integer delete(String clueId);
}
