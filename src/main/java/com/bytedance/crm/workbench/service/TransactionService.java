package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.vo.VO_TransactionPageList;

public interface TransactionService {
    String queryList(VO_TransactionPageList vo_transactionPageList);
}
