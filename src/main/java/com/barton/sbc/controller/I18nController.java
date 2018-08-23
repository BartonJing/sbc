package com.barton.sbc.controller;

import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.service.LocaleMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestController
public class I18nController {
    @Autowired
    LocaleMessageService localeMessageService;
    @RequestMapping("/dashboard/sys/changeLanauage")
    public ServerResponse changeSessionLanauage(HttpServletRequest request, HttpServletResponse response, String lang){
        if(StringUtils.isEmpty(lang)){
            return ServerResponse.createByError(localeMessageService.getMessage("sys.check.empty",new String[]{"lang"}));
        }
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        System.out.println(lang);

        if("zh".equals(lang)){

            //代码中即可通过以下方法进行语言设置
            localeResolver.setLocale(request, response, new Locale("zh", "CN"));
            //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));

        }else if("en".equals(lang)){

            //代码中即可通过以下方法进行语言设置
            localeResolver.setLocale(request, response, new Locale("en", "US"));
            //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));

        }

        return ServerResponse.createBySuccessMessage(localeMessageService.getMessage("i18n.change.success"));

    }
}
