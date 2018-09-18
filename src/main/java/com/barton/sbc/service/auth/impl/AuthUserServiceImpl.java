package com.barton.sbc.service.auth.impl;

import com.barton.sbc.dao.auth.AuthUserMapper;
import com.barton.sbc.dao.auth.AuthUserRoleMapper;
import com.barton.sbc.domain.entity.AuthExtend;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.domain.entity.auth.AuthUserRole;
import com.barton.sbc.service.auth.AuthUserService;
import com.barton.sbc.utils.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    private AuthUserMapper authUserMapper;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //模拟存数据库等持久化存储中读取用户信息
        AuthUser authUser = new AuthUser();
        authUser = authUser.findUser();
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
        if(authUserMapper.updateByPrimaryKeySelective(authUser) > 0){
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

    @Override
    public int insertUserRole(AuthUserRole authUserRole) {
        return authUserRoleMapper.insert(authUserRole);
    }

    @Override
    public int batchInsertUserRole(List<AuthUserRole> authUserRoles) {
        return authUserRoleMapper.batchInsert(authUserRoles);
    }

    @Override
    public int deleteUserRoleById(String id) {
        return authUserRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteUserRoleByUserIdAndRoleId(String userId,String roleId) {
        return authUserRoleMapper.deleteByUserIdAndRoleId(userId,roleId);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = new AuthUser(username,password);
        return jwtTokenUtil.generateToken(userDetails);
    }
    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @Override
    public String register(AuthUser user) {
        String username = user.getUsername();
        /*if (userRepository.findByUsername(username) != null) {
            return "用户已存在";
        }*/
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        //user.setRoles(roles);
        //userRepository.insert(user);
        return "success";
    }
    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }
}
