package com.barton.sbc.service.auth.impl;

import com.barton.sbc.dao.auth.AuthPermissionMapper;
import com.barton.sbc.dao.auth.AuthRolePermissionMapper;
import com.barton.sbc.domain.entity.auth.AuthPermission;
import com.barton.sbc.service.auth.AuthPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {
    @Autowired
    private AuthPermissionMapper authPermissionMapper;
    @Autowired
    private AuthRolePermissionMapper authRolePermissionMapper;
    @Override
    public AuthPermission insert(AuthPermission authPermission) {
        if(authPermissionMapper.insertSelective(authPermission) > 0){
            return authPermission;
        }
        return null;
    }

    @Override
    public AuthPermission updateById(AuthPermission authPermission) {
        if(authPermissionMapper.updateByPrimaryKeySelective(authPermission) > 0){
            return authPermission;
        }
        return null;
    }

    @Override
    @Transactional
    public int deleteById(String id) {
        authRolePermissionMapper.deleteByPermissionId(id);
        return authPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AuthPermission selectById(String id) {
        return authPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AuthPermission> selectByParams(Map<String, Object> params) {
        return authPermissionMapper.selectByParams(params);
    }

    @Override
    public PageInfo<AuthPermission> selectPage(Integer page, Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(page,pageSize);
        PageInfo<AuthPermission> pageInfo = new PageInfo<AuthPermission>(selectByParams(params));
        return pageInfo;
    }

    @Override
    public List<AuthPermission> findAll() {
        return authPermissionMapper.findAll();
    }

    @Override
    public List<AuthPermission> selectByUserId(String userId) {
        return authPermissionMapper.selectByUserId(userId);
    }

    @Override
    public List<AuthPermission> selectByRoleId(String roleId) {
        return authPermissionMapper.selectByRoleId(roleId);
    }

}
