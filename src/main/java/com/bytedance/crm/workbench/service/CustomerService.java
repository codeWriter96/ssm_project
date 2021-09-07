package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.damain.Tran;

public interface CustomerService {
    String addByTran(Tran tran);

    String queryCustomerName(String name);
}
