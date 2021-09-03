package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.exception.ActivityException;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.untils.DateTimeUtil;
import com.bytedance.crm.untils.UUIDUtil;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.ActivityRemark;
import com.bytedance.crm.workbench.damain.Clue;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.service.ClueService;
import com.bytedance.crm.workbench.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueService;


    @RequestMapping(value = "/save.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object saveClue(Clue clue, HttpServletRequest request){
        String creatTime = DateTimeUtil.getSysTime();
        clue.setCreateTime(creatTime);
        String id = UUIDUtil.getUUID();
        clue.setId(id);
        String userName = ((User) request.getSession().getAttribute("user")).getName();
        String userId = ((User) request.getSession().getAttribute("user")).getId();
        clue.setOwner(userId);
        clue.setCreateBy(userName);

        return clueService.addClue(clue);

    }


    @RequestMapping(value = "/getList.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getClueList(VO_ClueList vo_clueList){
        return clueService.getClueList(vo_clueList);

    }

    @RequestMapping(value = "/detail.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView detailActivity(String id) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map= clueService.getdetailClue(id);
        modelAndView.addObject("detail",map.get("detail"));
        if((boolean)map.get("success")){
            modelAndView.setViewName("/workbench/clue/detail");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/getRelation.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getRelation(String clueId){
        return clueService.queryRelation(clueId);
    }

    @RequestMapping(value = "/deleteRelation.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object deleteRelation(String id){
        return clueService.deleteRelation(id);
    }

    @RequestMapping(value = "/getNoRelation.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getNoRelation(String name ,String clueId){
        return clueService.quertNoRelation(name,clueId);
    }

    @RequestMapping(value = "/bulidRelation.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object createRelation(String clueId, String[] activitiesId){
        return clueService.createRelation(activitiesId,clueId);
    }

    @RequestMapping(value = "/getRelationByName.do",method = GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getRelationByName(String name ,String clueId){
        return clueService.getRelationByName(name,clueId);
    }

    @RequestMapping(value = "/convert.do",method = POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView convert(VO_ClueTran vo_clueTran,HttpServletRequest request){

        String createBy =((User)request.getSession().getAttribute("user")).getName();
        vo_clueTran.setCreateBy(createBy);
        ModelAndView mv = new ModelAndView();


        if(clueService.convert(vo_clueTran)){
            mv.setViewName("/workbench/clue/index");
        }else {
            mv.setViewName("/workbench/clue/index");
        }
        return mv;
    }
}
