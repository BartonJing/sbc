package com.barton.sbc.dao.auth;


import com.barton.sbc.domain.entity.auth.AuthRole;

import java.util.List;
import java.util.Map;

public interface AuthRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthRole record);

    int insertSelective(AuthRole record);

    AuthRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthRole record);

    int updateByPrimaryKey(AuthRole record);

    List<AuthRole> selectByParams(Map<String,Object> params);

    List<AuthRole> selectByUserId(String userId);
}