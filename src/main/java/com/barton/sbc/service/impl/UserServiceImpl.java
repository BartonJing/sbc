package com.barton.sbc.service.impl;

import com.barton.sbc.domian.AuthUser;
import com.barton.sbc.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * create by barton on 2018-7-2
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //模拟存数据库等持久化存储中读取用户信息
        AuthUser authUser = new AuthUser();
        authUser = authUser.findUSer(s);
        if(authUser == null){
            throw new UsernameNotFoundException("未找到用户!");
        }
        return authUser;
    }

}
