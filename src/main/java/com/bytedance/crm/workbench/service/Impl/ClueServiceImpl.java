package com.bytedance.crm.workbench.service.Impl;

import com.bytedance.crm.exception.ActivityException;
import com.bytedance.crm.exception.AddException;
import com.bytedance.crm.exception.DeleteException;
import com.bytedance.crm.exception.QueryException;
import com.bytedance.crm.untils.DateTimeUtil;
import com.bytedance.crm.untils.UUIDUtil;
import com.bytedance.crm.untils.WriteJsonUntil;
import com.bytedance.crm.workbench.damain.*;
import com.bytedance.crm.workbench.dao.*;
import com.bytedance.crm.workbench.service.ClueService;
import com.bytedance.crm.workbench.vo.VO_ClueList;
import com.bytedance.crm.workbench.vo.VO_ClueTran;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addClue(Clue clue) {
        String json =null;
        try {
            Integer integer = clueDao.insertClue(clue);
            if(!(integer>0)){
                throw new AddException("添加失败");
            }
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

    @Transactional(propagation =Propagation.SUPPORTS)
    @Override
    public String getClueList(VO_ClueList vo_clueList) {
        String json =null;
        Map<String, Object> map = new HashMap<>();
        try {
            int pageNo = Integer.parseInt(vo_clueList.getPageNo());
            int pageSize = Integer.parseInt(vo_clueList.getPageSize());
            vo_clueList.setPageSizeInt(pageSize);
            int skipCount =(pageNo-1)*pageSize;
            vo_clueList.setSkipCount(skipCount);
            List<Clue> clueList = clueDao.selectClue(vo_clueList);
            if(0 == clueList.size()){
                throw new QueryException("查询失败,无结果");
            }

            map.put("success", true);
            map.put("list",clueList);
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

    @Transactional(propagation =Propagation.SUPPORTS)
    @Override
    public Map<String, Object> getdetailClue(String id) {
        Map<String, Object> map = new HashMap<>();
        try{
            Clue clue = clueDao.selectDetail(id);
            if(null==clue){
                throw new QueryException("打开失败，记录不存在");
            }

            map.put("success", true);
            map.put("detail",clue);
        }catch (QueryException e){
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success", false);
            map.put("msg", msg);
        }
        return map;
        }

    @Transactional(propagation =Propagation.SUPPORTS)
    @Override
    public String queryRelation(String clueId) {
        String json =null;
        Map<String, Object> map = new HashMap<>();
        try {
            List<Activity> activity = clueActivityRelationDao.selectRelation(clueId);

            if(0 == activity.size()){
                throw new QueryException("查询失败,无结果");
            }

            map.put("success", true);
            map.put("list",activity);
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

    @Transactional(propagation =Propagation.REQUIRED)
    @Override
    public String deleteRelation(String id) {
        String json =null;
        try {
            Integer integer = clueActivityRelationDao.deleteRelation(id);
            if(!(integer>0)){
                throw new DeleteException("删除失败");
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String quertNoRelation(String name,String clueId) {
        String json =null;
        Map<String, Object> map = new HashMap<>();
        try {
            List<Activity> activity = clueActivityRelationDao.selectNoRelation(name,clueId);

            if(0 == activity.size()){
                throw new QueryException("查询失败,无结果");
            }

            map.put("success", true);
            map.put("list",activity);
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createRelation(String[] activitiesId,String clueId) {
        String json =null;
        try {
            if(!(activitiesId.length>0)){
                throw new AddException("请至少选择一项需要关联的活动");
            }

            for (String activityId : activitiesId) {
                String id = UUIDUtil.getUUID();
                Integer integer = clueActivityRelationDao.addRelation(id, clueId, activityId);
                if (!(integer > 0)) {
                    throw new AddException("关联失败");
                }
            }

            json = WriteJsonUntil.printJsonFlag(true);
        } catch (AddException e) {
            e.printStackTrace();
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
    public String getRelationByName(String name, String clueId) {
        String json =null;
        Map<String, Object> map = new HashMap<>();
        try {
            List<Activity> activity = clueActivityRelationDao.selectRelationByName(name,clueId);

            if(0 == activity.size()){
                throw new QueryException("查询失败,无结果");
            }

            map.put("success", true);
            map.put("list",activity);
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean convert(VO_ClueTran vo_clueTran) {
        boolean flag = true;
        String clueId = vo_clueTran.getClueId();
        String createTime = DateTimeUtil.getSysTime();
        String createBy =vo_clueTran.getCreateBy();
        //获取线索详细信息
        Clue clue = clueDao.getDetail(clueId);
        String company =clue.getCompany();
        //判断customer的公司是否重复
        Customer customer = customerDao.getCustomerByName(company);
        String customerId =null;
        if(null==customer){
            Customer c = new Customer();
            c.setId(UUIDUtil.getUUID());
            c.setOwner(clue.getOwner());
            c.setAddress(clue.getAddress());
            c.setWebsite(clue.getWebsite());
            c.setPhone(clue.getPhone());
            c.setCreateBy(createBy);
            c.setCreateTime(createTime);
            c.setNextContactTime(clue.getNextContactTime());
            c.setName(company);
            c.setContactSummary(clue.getContactSummary());
            c.setDescription(clue.getDescription());
            //获取customerId
            customerId = c.getId();
            //添加客户
            Integer integer = customerDao.add(c);
            if(1!=integer){
                flag =false;
                throw new AddException("转换失败，转换顾客表出错");
            }
        }else {
            //获取customerId
            customerId = customer.getId();
        }

        //创建联系人信息表
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customerId);
        contacts.setFullname(clue.getFullname());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setEmail(clue.getEmail());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setDescription(clue.getDescription());
        Integer integer1 = contactsDao.add(contacts);
        if(1 != integer1){
            flag =false;
            throw new AddException("转换失败，转换联系人表出错");
        }
        //获取contactsId
        String contactsId =contacts.getId();
        //查询clueRemark
        List<ClueRemark> clueRemarkList = clueRemarkDao.getList(clueId);
        //创建顾客备注表、联系人备注表
        if(0 !=clueRemarkList.size()){
            for(ClueRemark c:clueRemarkList){
                //创建顾客备注对象
                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setCustomerId(customerId);
                customerRemark.setCreateTime(createTime);
                customerRemark.setCreateBy(createBy);
                customerRemark.setEditFlag("0");
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setNoteContent(c.getNoteContent());
                //创建顾客备注表
                Integer integer = customerRemarkDao.add(customerRemark);
                if(1 != integer){
                    flag = false;
                    throw new AddException("转换失败，转换顾客备注表出错");
                }
                //创建联系人备注对象
                ContactsRemark contactsRemark = new ContactsRemark();
                contactsRemark.setContactsId(contactsId);
                contactsRemark.setCreateTime(createTime);
                contactsRemark.setCreateBy(createBy);
                contactsRemark.setEditFlag("0");
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setNoteContent(c.getNoteContent());
                //创建联系人备注表
                Integer integer2 = contactsRemarkDao.add(contactsRemark);
                if(1 != integer2){
                    flag = false;
                    throw new AddException("转换失败，转换联系人备注表出错");
                }
            }
        }
        //查询tbl_clue_activity_relation
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getList(clueId);
        if(0 !=clueActivityRelationList.size()){
            for(ClueActivityRelation c :clueActivityRelationList){
                //创建contactsActivityRelation对象
                ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setId(UUIDUtil.getUUID());
                contactsActivityRelation.setActivityId(c.getActivityId());
                contactsActivityRelation.setContactsId(contactsId);
                //创建tbl_contacts_activity_relation表
                Integer integer = contactsActivityRelationDao.add(contactsActivityRelation);
                if(1 !=integer){
                    flag =false;
                    throw new AddException("转换失败，转换联系人和活动关系表出错");
                }
            }
        }
        //如果勾选交易，创建交易
        if( "f".equals(vo_clueTran.getFlag())){
            //创建tran对象
            Tran tran = new Tran();
            tran.setActivityId(vo_clueTran.getActivityId());
            tran.setStage(vo_clueTran.getStage());
            tran.setOwner(clue.getOwner());
            tran.setSource(clue.getSource());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setName(vo_clueTran.getName());
            tran.setMoney(vo_clueTran.getMoney());
            tran.setId(UUIDUtil.getUUID());
            tran.setExpectedDate(vo_clueTran.getExpectedDate());
            tran.setCreateTime(createTime);
            tran.setCreateBy(createBy);
            tran.setActivityId(vo_clueTran.getActivityId());
            tran.setCustomerId(customerId);
            tran.setContactsId(contactsId);
            tran.setType("新业务");
            //创建tbl_tran表
            Integer integer = tranDao.add(tran);
            if(1 !=integer){
                flag =false;
                throw new AddException("转换失败，转换交易表出错");
            }
            String tranId = tran.getId();
            //创建tranHistory对象
            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setTranId(tranId);
            tranHistory.setStage(vo_clueTran.getStage());
            tranHistory.setMoney(vo_clueTran.getMoney());
            tranHistory.setExpectedDate(vo_clueTran.getExpectedDate());
            tranHistory.setId(UUIDUtil.getUUID());
            //创建tbl_tran_history对象
            Integer integer3 = tranHistoryDao.add(tranHistory);
            if(1 !=integer3){
                flag =false;
                throw new AddException("转换失败，转换交易历史表出错");
            }
        }
        //查询线索备注数量
        Integer res = (clueRemarkDao.getList(clueId)).size();
        //删除线索备注
        Integer integer4 = clueRemarkDao.delete(clueId);
        if(!res.equals(integer4)){
            flag =false;
            throw new DeleteException("转换失败，删除线索备注表出错");
        }
        //查询线索和市场活动关系数量
        Integer res1 = (clueActivityRelationDao.getList(clueId)).size();
        //删除线索和市场活动关系
        Integer integer5 =clueActivityRelationDao.deleteRelationByClueId(clueId);
        if(!res1.equals(integer5)){
            flag =false;
            throw new DeleteException("转换失败，删除线索和市场活动关系表出错");
        }
        //删除线索
        Integer integer6 =clueDao.deleteClueById(clueId);
        if(!(integer6>0)){
            flag =false;
            throw new DeleteException("转换失败，删除线索表出错");
        }
        return flag;
    }
}
