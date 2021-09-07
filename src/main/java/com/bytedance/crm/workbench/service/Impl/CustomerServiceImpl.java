package com.bytedance.crm.workbench.service.Impl;

import com.bytedance.crm.exception.AddException;
import com.bytedance.crm.untils.UUIDUtil;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.Customer;
import com.bytedance.crm.workbench.damain.Tran;
import com.bytedance.crm.workbench.dao.CustomerDao;
import com.bytedance.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDao customerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addByTran(Tran tran) {
        //判断公司customer的名字是否存在
        String id = customerDao.selectCustomerName(tran.getCustomerId());
        //不存在则创建
        if(null == id){
            id = UUIDUtil.getUUID();
            Customer customer = new Customer();
            customer.setName(tran.getCustomerId());
            customer.setId(id);
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setOwner(tran.getOwner());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setDescription(tran.getDescription());
            Integer integer = customerDao.add(customer);
            if(integer !=1 ){
                throw new AddException("创建失败");
            }
        }
        return id;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryCustomerName(String name) {
        List<String> list = customerDao.selectCustomerNameList(name);
        return WriteJsonUntil.printJsonObj(list);
    }
}
