package com.barton.sbc.security;


import com.alibaba.druid.util.StringUtils;
import com.barton.sbc.exception.MyAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码校验filter
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.equals("/login", httpServletRequest.getRequestURI())) {

            try {
                // 1. 进行验证码的校验
                //validate(new ServletWebRequest(httpServletRequest));
                throw new MyAuthenticationException("验证码错误！");
            } catch (MyAuthenticationException e) {
                // 2. 如果校验不通过，调用SpringSecurity的校验失败处理器
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return ;
            }
        }
        // 3. 校验通过，就放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
