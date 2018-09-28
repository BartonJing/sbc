package com.barton.sbc.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

/**
 * 日志注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogAnnotation {
    public final static int INSERT = 1;
    public final static int DELETE = 2;
    public final static int UPDATE = 3;
    public final static int SELECT = 4;
    public final static int SAVE = 5;
    //操作描述
    String value() default "";
    //操作类型
    int type() default 5;

}
