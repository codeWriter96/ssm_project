package com.bytedance.crm.settings.dao;

import com.bytedance.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeDao {
    List<DicType> selectAll();
}
