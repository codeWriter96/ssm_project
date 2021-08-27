package com.bytedance.crm.workbench.service.Impl;

import com.bytedance.crm.exception.DeleteException;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.dao.ActivityDao;
import com.bytedance.crm.workbench.dao.ActivityRemarkDao;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.vo.VO_Activity;
import com.bytedance.crm.workbench.vo.VO_PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityRemarkDao activityRemarkDao;
    @Autowired
    private ActivityService activityService;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer addActivity(VO_Activity vo_activity) {
        return activityDao.insertActivity(vo_activity);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryActivity(VO_PageList vo_pageList) {
        int pageNo = Integer.parseInt(vo_pageList.getPageNo());
        int pageSize = Integer.parseInt(vo_pageList.getPageSize());
        vo_pageList.setPageSizeInt(pageSize);
        int skipCount =(pageNo-1)*pageSize;
        vo_pageList.setSkipCount(skipCount);


        Map<String, Object> map = new HashMap<>();
        List<VO_Activity> list = activityDao.selectActivity(vo_pageList);
        Integer total = activityDao.countSelectActivity(vo_pageList);
        map.put("VO_Activity",list);
        map.put("total",total);
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryActivityRemark(String[] id) {
        return activityRemarkDao.countSelectActivityRemark(id);
    }





    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<String,Object> removeActivityRemark(Integer res , String[] id) {
        Map<String,Object> map = new HashMap<>();
        map.put("queryActivityRemark",res);
        map.put("deleteActivityRemark",activityRemarkDao.deleteActivityRemark(id));
        return map;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer removeActivity(String[] id) {
        return activityDao.deleteActivity(id);
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String remove(String[] id){
        String json =null;
        try{
            Integer res = activityService.queryActivityRemark(id);
            activityService.removeActivityRemark(res,id);
            activityService.removeActivity(id);

            json = WriteJsonUntil.printJsonFlag(true);
        }catch (DeleteException e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;

}

}
