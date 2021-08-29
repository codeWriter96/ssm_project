package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.exception.ActivityException;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.untils.DateTimeUtil;
import com.bytedance.crm.untils.UUIDUtil;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.ActivityRemark;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.vo.VO_Activity;
import com.bytedance.crm.workbench.vo.VO_PageList;
import com.bytedance.crm.workbench.vo.VO_UpdateActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/workbench/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;


    @RequestMapping(value = "/save.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object saveActivity(VO_Activity vo_activity, HttpServletRequest request){
        String json =null;
        String creatTime = DateTimeUtil.getSysTime();
        vo_activity.setCreateTime(creatTime);
        String id = UUIDUtil.getUUID();
        vo_activity.setId(id);
        String userId = ((User) request.getSession().getAttribute("user")).getId();
        vo_activity.setUserId(userId);
        vo_activity.setCreateBy(userId);
        try {
            activityService.addActivity(vo_activity);

            json = WriteJsonUntil.printJsonFlag(true);
        } catch (ActivityException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            json = WriteJsonUntil.printJsonObj(map);
        }

        return json;
    }



    @RequestMapping(value = "/pagelist.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getActivity(VO_PageList vo_pageList){
            String json =null;
            try {

                Map<String, Object> res= activityService.queryActivity(vo_pageList);

                Map<String, Object> map = new HashMap<>();
                map.put("success", true);
                map.put("total",res.get("total"));
                map.put("activityList",res.get("VO_Activity"));
                json = WriteJsonUntil.printJsonObj(map);
            } catch (ActivityException e) {
                e.printStackTrace();
                String msg = e.getMessage();
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("msg", msg);
                json = WriteJsonUntil.printJsonObj(map);
            }
            return json;
    }



    @RequestMapping(value = "/delete.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object deleteActivity(HttpServletRequest request){
        String[] id = request.getParameterValues("id");

        return activityService.remove(id);

    }


    @RequestMapping(value = "/get.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getActivityAndUser(String activityId) {
        return activityService.queryActivityAndUser(activityId);
    }


    @RequestMapping(value = "/edit.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object editActivityAndUser(VO_UpdateActivity vo_updateActivity,HttpServletRequest request) {
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        vo_updateActivity.setEditBy(userId);
        return activityService.editActivityAndUser(vo_updateActivity);
    }


    @RequestMapping(value = "/detail.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView detailActivity(String activityId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map= activityService.getdetailActivity(activityId);
        modelAndView.addObject("detail",map.get("detail"));
        if((boolean)map.get("success")){
            modelAndView.setViewName("/workbench/activity/detail");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/getRemark.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getRemark(String activityId) {
        return activityService.queryRemark(activityId);
    }

    @RequestMapping(value = "/removeRemark.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object removeRemark(String remarkId) {
        return activityService.removeRemark(remarkId);
    }

    @RequestMapping(value = "/addRemark.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object addRemark(String noteContent,String activityId,HttpServletRequest request) {
        ActivityRemark activityRemark = new ActivityRemark();
        String id =UUIDUtil.getUUID();
        String creatTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getId();
        String createName = ((User)request.getSession().getAttribute("user")).getName();
        activityRemark.setId(id);
        activityRemark.setEditFlag("0");
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(creatTime);
        activityRemark.setActivityId(activityId);
        activityRemark.setTime(creatTime);
        activityRemark.setCreateName(createName);


        return activityService.addRemark(activityRemark);
    }

    @RequestMapping(value = "/editRemark.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object editRemark(String noteContent,String remarkId,HttpServletRequest request) {
        ActivityRemark activityRemark = new ActivityRemark();
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getId();
        String editName = ((User)request.getSession().getAttribute("user")).getName();

        activityRemark.setId(remarkId);
        activityRemark.setEditFlag("1");
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditTime(editTime);
        activityRemark.setTime(editTime);
        activityRemark.setEditName(editName);

        return activityService.editRemark(activityRemark);
    }
}
