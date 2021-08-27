package com.bytedance.crm.settings.dao;

import com.bytedance.crm.settings.domain.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    Admin selectAdminUser(@Param("id") String id);
}
