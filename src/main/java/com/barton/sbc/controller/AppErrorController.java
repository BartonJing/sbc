package com.barton.sbc.controller;

import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.service.LocaleMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by barton on 2018/07/22.
 */
@Controller
public class AppErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);

    private static AppErrorController appErrorController;
    @Autowired
    LocaleMessageService localeMessageService;

    /**
     * Error Attributes in the Application
     */
    @Autowired
    private ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    /**
     * Controller for the Error Controller
     *
     * @param errorAttributes
     * @return
     */
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public AppErrorController() {
        if (appErrorController == null) {
            appErrorController = new AppErrorController(errorAttributes);
        }
    }


    /**
     * Supports other formats like JSON, XML
     *
     * @param request
     * @return
     */
    /*@RequestMapping(value = ERROR_PATH, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }*/
    @RequestMapping(value = ERROR_PATH, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ServerResponse error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return ServerResponse.createByErrorCodeMessage(Integer.parseInt(body.get("status").toString()),localeMessageService.getMessage("sys.error"),new ResponseEntity<Map<String, Object>>(body, status));

    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        //RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        WebRequest re = new ServletWebRequest(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(re, true);

        String URL = request.getRequestURL().toString();
        map.put("URL", URL);
        logger.error("AppErrorController.method [error info]: status-" + map.get("status") + ", request url-" + URL);
        logger.error(map.toString());
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
