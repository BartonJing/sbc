package com.barton.sbc.service.auth.impl;

import com.barton.sbc.dao.auth.AuthRoleMapper;
import com.barton.sbc.dao.auth.AuthRolePermissionMapper;
import com.barton.sbc.dao.auth.AuthUserRoleMapper;
import com.barton.sbc.domain.entity.auth.AuthRole;
import com.barton.sbc.domain.entity.auth.AuthRolePermission;
import com.barton.sbc.domain.entity.auth.AuthUserRole;
import com.barton.sbc.service.auth.AuthRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class AuthRoleServiceImpl implements AuthRoleService {
    @Autowired
    private AuthRoleMapper authRoleMapper;
    @Autowired
    private AuthRolePermissionMapper authRolePermissionMapper;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Override
    public AuthRole insert(AuthRole authRole) {
        if(authRoleMapper.insertSelective(authRole) > 0){
            return authRole;
        }
        return null;
    }

    @Override
    public AuthRole updateById(AuthRole authRole) {
        if(authRoleMapper.updateByPrimaryKeySelective(authRole) > 0){
            return authRole;
        }
        return null;
    }

    @Override
    public int deleteById(String id) {
        authRolePermissionMapper.deleteByRoleId(id);
        authUserRoleMapper.deleteByRoleId(id);
        return authRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AuthRole selectById(String id) {
        return authRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AuthRole> selectByParams(Map<String, Object> params) {
        return authRoleMapper.selectByParams(params);
    }

    @Override
    public PageInfo<AuthRole> selectPage(Integer page, Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(page,pageSize);
        PageInfo<AuthRole> pageInfo = new PageInfo<AuthRole>(selectByParams(params));
        return pageInfo;
    }

    @Override
    public int insertRolePermission(AuthRolePermission authRolePermission) {
        return authRolePermissionMapper.insert(authRolePermission);
    }

    @Override
    public int batchInsertRolePermission(List<AuthRolePermission> authRolePermissions) {
        return authRolePermissionMapper.batchInsert(authRolePermissions);
    }

    @Override
    public int deleteRolePermissionById(String id) {
        return authRolePermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AuthRole> selectByUserId(String userId) {
        return authRoleMapper.selectByUserId(userId);
    }

    @Override
    public int deleteRolePermissionByRoleIdAndPermission(String roleId, String permissionId) {
        return authRolePermissionMapper.deleteByPermissionIdAndRoleId(permissionId,roleId);
    }
}
