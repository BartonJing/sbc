package com.barton.sbc.dao.auth;


import com.barton.sbc.domain.entity.auth.AuthRolePermission;

public interface AuthRolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthRolePermission record);

    int insertSelective(AuthRolePermission record);

    AuthRolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthRolePermission record);

    int updateByPrimaryKey(AuthRolePermission record);
}