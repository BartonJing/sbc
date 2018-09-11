package com.barton.sbc.controller;

import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.LocaleMessageService;
import com.barton.sbc.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {
    @Autowired
    LocaleMessageService localeMessageService;
    /*@GetMapping("/login")
    public String login(){
        return "start login";
    }*/


    @GetMapping("/index")
    public String index(HttpServletResponse response){
        //response.setHeader("aaaaaa","eeeeeeeeeeeeeeeeeee");
        AuthUser authUser = CurrentUserUtil.getAuthUser();
        return "index success";
    }


    @GetMapping("/test1")
    public String test1(){
        //System.out.println(localeMessageService.getMessage("username",new String[]{"你好","账单"}));
        return "test1";
    }

    @GetMapping("/test3")
    public String test3(){
        //System.out.println(localeMessageService.getMessage("username",new String[]{"你好","账单"}));
        Integer.parseInt("aaa");
        return "test1";
    }
    @GetMapping("/test2")
    public String test2(){

        return "test2";
    }

    @GetMapping("/loginError")
    public String loginError(HttpServletRequest request){
        HttpSession session = request.getSession();


        return "loginError";
    }


}
