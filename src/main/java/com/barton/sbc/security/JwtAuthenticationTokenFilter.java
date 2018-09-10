package com.barton.sbc.security;

import com.barton.sbc.exception.MyAuthenticationException;
import com.barton.sbc.service.auth.AuthUserService;
import com.barton.sbc.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 *
 * Created on 2018/9/10
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.authUserService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }else{
            try {
                // 1. 进行验证码的校验
                //validate(new ServletWebRequest(httpServletRequest));
                throw new MyAuthenticationException("此操作必须先登录");
            } catch (MyAuthenticationException e) {
                // 2. 如果校验不通过，调用SpringSecurity的校验失败处理器
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return ;
            }
        }
        chain.doFilter(request, response);
    }

}
