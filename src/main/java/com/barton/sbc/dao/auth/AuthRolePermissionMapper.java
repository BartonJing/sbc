package com.barton.sbc.dao.auth;


import com.barton.sbc.domain.entity.auth.AuthRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthRolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthRolePermission record);

    int insertSelective(AuthRolePermission record);

    AuthRolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthRolePermission record);

    int updateByPrimaryKey(AuthRolePermission record);

    int batchInsert(List<AuthRolePermission> records);

    int deleteByPermissionIdAndRoleId(@Param("permissionId") String permissionId, @Param("roleId") String roleId);

    int deleteByPermissionId(String permissionId);

    int deleteByRoleId(String roleId);
}