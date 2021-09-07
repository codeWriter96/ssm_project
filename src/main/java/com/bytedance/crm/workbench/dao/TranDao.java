package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.Tran;
import com.bytedance.crm.workbench.vo.VO_TransactionPageList;

import java.util.List;

public interface TranDao {

    Integer add(Tran tran);

    List<Tran> selectList(VO_TransactionPageList vo_transactionPageList);

    Tran selectDetail(String id);
}
