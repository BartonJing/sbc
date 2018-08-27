package com.barton.sbc.dao.auth;


import com.barton.sbc.domain.entity.auth.AuthPermission;

public interface AuthPermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthPermission record);

    int insertSelective(AuthPermission record);

    AuthPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthPermission record);

    int updateByPrimaryKey(AuthPermission record);
}