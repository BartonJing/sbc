package com.barton.sbc.aspect;

import com.alibaba.fastjson.JSON;
import com.barton.sbc.annotation.SysLog;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.utils.CurrentUserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志处理 aspect
 */
@Aspect
@Component
public class SysLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //controller 包下的所有注解(SysLog)
    @Pointcut("execution(public * com.barton.sbc.controller.*.*(..)) && @annotation(com.barton.sbc.annotation.SysLog)")
    public void cut(){}

    @Before("cut() && @annotation(sysLog)")
    public void before(JoinPoint joinPoint, SysLog sysLog) {
        AuthUser currentUser = CurrentUserUtil.getAuthUser();
        System.out.print("打印：" + sysLog.value());
        System.out.print("打印：" + sysLog.type());
        System.out.print("打印：" + currentUser.getId());
        System.out.print("打印：" + getMethodArguments(joinPoint));
    }

    /**
     * 获取参数列表
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private String getMethodArguments(JoinPoint joinPoint){
        //返回目标对象
        //Object target = joinPoint.getTarget();
        //String targetName = target.getClass().getName();
        //返回当前连接点签名
        //String methodName = joinPoint.getSignature().getName();
        //获得参数列表
        Object[] arguments = joinPoint.getArgs();
        return JSON.toJSONString(arguments);
        //Class targetClass = Class.forName(targetName);
    }

}
