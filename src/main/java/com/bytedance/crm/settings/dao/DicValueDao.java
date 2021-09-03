package com.bytedance.crm.settings.dao;

import com.bytedance.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> selectValue(String code);
}
