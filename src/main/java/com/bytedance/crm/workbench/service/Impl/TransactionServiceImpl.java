package com.bytedance.crm.workbench.service.Impl;

import com.bytedance.crm.exception.QueryException;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.Tran;
import com.bytedance.crm.workbench.dao.TranDao;
import com.bytedance.crm.workbench.service.TransactionService;
import com.bytedance.crm.workbench.vo.VO_TransactionPageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TranDao tranDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryList(VO_TransactionPageList vo_transactionPageList) {
        String json = null;
        Map<String,Object> map = new HashMap<>();
        try{
            List<Tran> list = tranDao.selectList(vo_transactionPageList);
            if(0 == list.size()){
                throw new QueryException("查询失败，无记录");
            }
            map.put("success",true);
            map.put("list",list);
            json = WriteJsonUntil.printJsonObj(map);
        }catch (QueryException e){
            e.printStackTrace();
            String msg =e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }
}
