package com.bytedance.crm.workbench.service.Impl;

import com.bytedance.crm.exception.AddException;
import com.bytedance.crm.exception.DeleteException;
import com.bytedance.crm.exception.QueryException;
import com.bytedance.crm.exception.UpdateException;
import com.bytedance.crm.untils.DateTimeUtil;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.ActivityRemark;
import com.bytedance.crm.workbench.dao.ActivityDao;
import com.bytedance.crm.workbench.dao.ActivityRemarkDao;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.vo.*;
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryActivityAndUser(String activityId) {
        String json = null;
        Map<String, Object> map = new HashMap<>();
        try {
            VO_UpdateActivity vo_updateActivity =activityDao.selectActivityAndUser(activityId);
            if(null==vo_updateActivity){
                throw new QueryException("该条记录不存在，请重新选择");
            }

            map.put("success", true);
            map.put("res",vo_updateActivity);
            json = WriteJsonUntil.printJsonObj(map);
        } catch (QueryException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }


    @Override
    public String editActivityAndUser(VO_UpdateActivity vo_updateActivity) {
        String json = null;
        try {
            vo_updateActivity.setEditTime(DateTimeUtil.getSysTime());
            Integer integer = activityDao.updateActivityAndUser(vo_updateActivity);
            if(!(integer>0)){
                throw new UpdateException("更新失败，该条记录不存在");
            }

            json = WriteJsonUntil.printJsonFlag(true);
        } catch (DeleteException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }

    @Override
    public Map<String, Object> getdetailActivity(String activityId) {
        Map<String, Object> map = new HashMap<>();
        try{
            VO_Detail vo_detail = activityDao.selectDetail(activityId);
            if(null==vo_detail){
                throw new QueryException("打开失败，记录不存在");
            }
            String createBy= activityDao.selectUserNameCreateBy(vo_detail.getCreateBy());
            String  editBy=activityDao.selectUserNameEditBy(vo_detail.getEditBy());
            vo_detail.setEditBy(editBy);
            vo_detail.setCreateBy(createBy);


            map.put("success", true);
            map.put("detail",vo_detail);
        }catch (QueryException e){
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success", false);
            map.put("msg", msg);
        }
        return map;
    }

    @Override
    public String queryRemark(String activityId) {
        String json = null;
        Map<String, Object> map = new HashMap<>();
        try {
            List<ActivityRemark> remarkList = activityRemarkDao.selectActivityRemark(activityId);
            if(null == remarkList){
                throw new QueryException("无任何备注信息");
            }



            map.put("success", true);
            map.put("remark", remarkList);
            json = WriteJsonUntil.printJsonObj(map);
        } catch (DeleteException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }
        return json;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String removeRemark(String remarkId) {
        String json =null;
        try {
            Integer integer =activityRemarkDao.deleteRemark(remarkId);
            if(!(integer>0)){
                throw new DeleteException("删除失败，记录不存在");
            }
            json = WriteJsonUntil.printJsonFlag(true);
        }catch (DeleteException e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map =new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }

        return json;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addRemark(ActivityRemark activityRemark) {
        String json =null;

        try {
            if(null==activityRemark.getNoteContent()){
                throw new AddException("添加失败,输入的内容为空");
            }
            Integer integer =activityRemarkDao.addRemark(activityRemark);
            if(!(integer>0)){
                throw new AddException("添加失败");
            }
            json = WriteJsonUntil.printJsonFlag(true);
        }catch (AddException e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map =new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }

        return json;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String editRemark(ActivityRemark activityRemark) {
        String json =null;

        try {
            if(null==activityRemark.getNoteContent()){
                throw new AddException("修改失败,输入的内容为空");
            }
            Integer integer =activityRemarkDao.updateRemark(activityRemark);
            if(!(integer>0)){
                throw new AddException("修改失败");
            }
            json = WriteJsonUntil.printJsonFlag(true);
        }catch (AddException e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map =new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }

        return json;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String getCounts() {
        //获取总count
        Integer integer = activityDao.countAll();
        //获取各个name的count
        List<VO_ActivitiyCounts> list =  activityDao.selectNamesAndCount();
        for(int i=0;i<list.size();i++){
            VO_ActivitiyCounts vo_activitiyCounts = new VO_ActivitiyCounts();
            Integer temp1 = list.get(i).getValue();
            String temp2 = list.get(i).getName();
            float res = (float)temp1/integer;
            Integer temp3 = Math.round(res*100) ;
            vo_activitiyCounts.setName(temp2);
            vo_activitiyCounts.setValue(temp3);
            list.set(i,vo_activitiyCounts);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",integer);
        map.put("list",list);
        return  WriteJsonUntil.printJsonObj(map);
    }

}
