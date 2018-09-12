package com.barton.sbc.dao.auth;


import com.barton.sbc.domain.entity.auth.AuthPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthPermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthPermission record);

    int insertSelective(AuthPermission record);

    AuthPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthPermission record);

    int updateByPrimaryKey(AuthPermission record);

    List<AuthPermission> findAll();

    List<AuthPermission> selectByParams(Map<String,Object> params);

    List<AuthPermission> selectByUserId(String userId);

    List<AuthPermission> selectByRoleId(String roleId);

}