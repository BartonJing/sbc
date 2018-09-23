package com.barton.sbc.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定401返回值
 *
 * Created on 2018/9/10
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    private String badCredentialsException = "org.springframework.security.authentication.BadCredentialsException";
    private String usernameNotFoundException = "org.springframework.security.core.userdetails.UsernameNotFoundException";
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String message = e.getMessage();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        if(badCredentialsException.equals(e.getClass().getName())){
            message = "密码错误！";
        }else if(usernameNotFoundException.equals(e.getClass().getName())){
            message = "用户名不存在！";
        }

        PrintWriter out = response.getWriter();
        out.write("{\"status\":\""+HttpServletResponse.SC_UNAUTHORIZED +"\",\"msg\":\""+message+"\"}");
        out.flush();
        out.close();
    }

}