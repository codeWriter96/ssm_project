package com.bytedance.crm.workbench.service.Impl;

import com.bytedance.crm.exception.AddException;
import com.bytedance.crm.exception.QueryException;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.Tran;
import com.bytedance.crm.workbench.damain.TranHistory;
import com.bytedance.crm.workbench.dao.TranDao;
import com.bytedance.crm.workbench.dao.TranHistoryDao;
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
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

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
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String save(Tran tran) {
        String json = null;
        try{
            Integer integer = tranDao.add(tran);
            if(1 != integer){
                throw new AddException("保存失败");
            }
            json = WriteJsonUntil.printJsonFlag(true);
        }catch (AddException e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Object getDetail(String id) {
        Object obj = null;
        try{
             Tran tran= tranDao.selectDetail(id);
            if(null == tran){
                throw new QueryException("打开失败，无记录");
            }
           obj = tran;
        }catch (QueryException e){
            e.printStackTrace();
            obj= e.getMessage();
        }
        return obj;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String getHistories(String id) {
        String json = null;
        Map<String,Object> map = new HashMap<>();
        try{
            List<TranHistory> list = tranHistoryDao.selectHistories(id);
            if(0 == list.size()){
                throw new QueryException("查询失败，无记录");
            }
            map.put("success",true);
            map.put("list",list);
            json = WriteJsonUntil.printJsonObj(map);
        }catch (QueryException e){
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }
}
