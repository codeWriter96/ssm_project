package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    Integer add(TranHistory tranHistory);

    List<TranHistory> selectHistories(String id);
}
