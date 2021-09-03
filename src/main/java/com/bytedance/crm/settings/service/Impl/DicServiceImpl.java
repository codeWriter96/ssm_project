package com.bytedance.crm.settings.service.Impl;

import com.bytedance.crm.settings.dao.DicTypeDao;
import com.bytedance.crm.settings.dao.DicValueDao;
import com.bytedance.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;

public class DicServiceImpl implements DicService {
    @Autowired
    private DicValueDao dicValueDao;
    @Autowired
    private DicTypeDao dicTypeDao;


}
