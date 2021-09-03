package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    Integer add(Customer c);
}
