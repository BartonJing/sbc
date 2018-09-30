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
public @interface SysLog {
    //操作描述
    String value() default "";
    //操作类型
    SysLogType type() default SysLogType.SAVE;

    public static enum SysLogType {
        INSERT,
        DELETE,
        UPDATE,
        SELECT,
        SAVE;

        private SysLogType() {
        }
    }
}
