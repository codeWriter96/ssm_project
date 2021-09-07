package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.untils.DateTimeUtil;
import com.bytedance.crm.untils.UUIDUtil;
import com.bytedance.crm.workbench.damain.Tran;
import com.bytedance.crm.workbench.service.CustomerService;
import com.bytedance.crm.workbench.service.TransactionService;
import com.bytedance.crm.workbench.vo.VO_TransactionPageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/workbench/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerService customerService;


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

    @ResponseBody
    @RequestMapping(value = "/getCustomerName.do",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public Object getCustomerName(String name){
        return customerService.queryCustomerName(name);
    }


    @ResponseBody
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object save(Tran tran, HttpServletRequest request){
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        String createBy = ((User)request.getSession().getAttribute("user")).getId();
        tran.setCreateBy(createBy);
        //获取customerId，不存在则创建
        String customerId = customerService.addByTran(tran);
        tran.setCustomerId(customerId);

        return transactionService.save(tran);
    }

    @ResponseBody
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public ModelAndView getDetail(String id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("detail",transactionService.getDetail(id));
        mv.setViewName("/workbench/transaction/detail");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/getHistories.do",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public Object getHistories(String id){
        return transactionService.getHistories(id);
    }
}
