package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.exception.ActivityException;
import com.bytedance.crm.exception.DeleteException;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.untils.DateTimeUtil;
import com.bytedance.crm.untils.UUIDUtil;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.vo.VO_Activity;
import com.bytedance.crm.workbench.vo.VO_PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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







    @RequestMapping(value = "/get1.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object editActivity(String id){
        String json =null;
        try {




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

}
