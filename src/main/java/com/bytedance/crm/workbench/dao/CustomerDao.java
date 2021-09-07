package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.damain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    Integer add(Customer c);

    String selectCustomerName(String customerId);

    List<String> selectCustomerNameList(String name);
}
