package com.barton.sbc.service.auth.impl;

import com.barton.sbc.dao.auth.AuthUserMapper;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    private AuthUserMapper authUserMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //模拟存数据库等持久化存储中读取用户信息
        AuthUser authUser = new AuthUser();
        //authUser = authUser.findUSer(s);
        if(authUser == null){
            throw new UsernameNotFoundException("未找到用户!");
        }
        return authUser;
    }

    @Override
    public AuthUser insert(AuthUser authUser) {
        if(authUserMapper.insert(authUser) > 0){
            return authUser;
        }
        return null;
    }

    @Override
    public AuthUser updateById(AuthUser authUser) {
        if(authUserMapper.updateByPrimaryKey(authUser) > 0){
            return authUser;
        }
        return null;
    }

    @Override
    public int deleteById(String id) {
        return authUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AuthUser selectById(String id) {
        return authUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AuthUser> selectByParams(Map<String, Object> params) {
        return authUserMapper.selectByParams(params);
    }

    @Override
    public PageInfo<AuthUser> selectPage(Integer page, Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(page,pageSize);
        PageInfo<AuthUser> pageInfo = new PageInfo<AuthUser>(selectByParams(params));
        return pageInfo;
    }
}
