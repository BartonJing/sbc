package com.barton.sbc.security;

import com.barton.sbc.domain.entity.auth.AuthUser;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * Created by barton on 2018/07/22.
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("requestUrl:"+requestUrl);
        String [] permitAllPath = new String[]{"/","/toLogin","/test1","/test3","/changeSessionLanauage?lang=en"};
        for(String p:permitAllPath){
            if(antPathMatcher.match(p,requestUrl))
                return;
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadCredentialsException("此操作必须登录后才能使用");
        }
        //当前用户信息
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        //用户权限信息
        //当前用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(requestUrl)) {
                return;
            }
        }

        throw new AccessDeniedException("当前账号权限不足，请联系管理员!");
    }
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}