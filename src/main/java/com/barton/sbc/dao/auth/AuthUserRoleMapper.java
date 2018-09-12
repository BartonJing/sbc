package com.barton.sbc.dao.auth;

import com.barton.sbc.domain.entity.auth.AuthUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthUserRole record);

    int insertSelective(AuthUserRole record);

    AuthUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthUserRole record);

    int updateByPrimaryKey(AuthUserRole record);

    int batchInsert(List<AuthUserRole> records);

    int deleteByUserIdAndRoleId(@Param("userId") String useId, @Param("roleId") String roleId);

    int deleteByUserId(String userId);

    int deleteByRoleId(String roleId);
}