package com.barton.sbc.controller;

import com.barton.sbc.common.ResponseCode;
import com.barton.sbc.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by barton on 2018/07/22.
 */
@RestController
public class LoginController {

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * <p>
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return
     */
    @RequestMapping("/toLogin")
    public ServerResponse loginPage() {
        return ServerResponse.createByErrorCodeMessage(ResponseCode.LOGINERROR.getCode(),"");
    }





}
