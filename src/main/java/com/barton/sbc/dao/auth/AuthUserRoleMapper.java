package com.barton.sbc.dao.auth;

import com.barton.sbc.domain.entity.auth.AuthUserRole;

public interface AuthUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthUserRole record);

    int insertSelective(AuthUserRole record);

    AuthUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthUserRole record);

    int updateByPrimaryKey(AuthUserRole record);
}