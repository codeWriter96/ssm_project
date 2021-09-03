package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.workbench.service.TransactionService;
import com.bytedance.crm.workbench.vo.VO_TransactionPageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/workbench/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @ResponseBody
    @RequestMapping(value = "/getList.do",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public Object getList(VO_TransactionPageList vo_transactionPageList){
        int pageNo =vo_transactionPageList.getPageNo();
        int pageSize =vo_transactionPageList.getPageSize();
        Integer skipCount = (pageNo-1)*pageSize;
        vo_transactionPageList.setSkipCount(skipCount);
        vo_transactionPageList.setPageSizeInt(pageSize);

        return transactionService.queryList(vo_transactionPageList);
    }
}
