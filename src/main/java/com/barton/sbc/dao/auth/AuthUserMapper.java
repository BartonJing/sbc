package com.barton.sbc.dao.auth;


import com.barton.sbc.domain.entity.auth.AuthUser;

import java.util.List;
import java.util.Map;

public interface AuthUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthUser record);

    int insertSelective(AuthUser record);

    AuthUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthUser record);

    int updateByPrimaryKey(AuthUser record);

    List<AuthUser> selectByParams(Map<String,Object> params);
}