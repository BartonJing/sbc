package com.barton.sbc.utils;

import com.barton.sbc.domain.entity.auth.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 当前登录用户信息
 */
public class CurrentUserUtil {

    /**
     *  当前登录用户信息
     * @return
     */
    public static AuthUser getAuthUser(){
        Authentication authentication = (Authentication)SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            if(authentication.getDetails() != null){
                AuthUser authUser = (AuthUser)authentication.getDetails();
                authUser.setPassword(null);
                return authUser;
            }
        }
        AuthUser authUser = new AuthUser();
        /*authUser.setId("aaaaaaaaaaa");*/
        return authUser;
    }
}
