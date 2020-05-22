package io.niufen.common.annotation;

import java.lang.annotation.*;

/**
 * 系统请求日志自动记录注解
 * @author niufen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 日志内容
     */
    String value() default "";

    /**
     * 日志类型
     */
    int type() default 0;

    /**
     * 操作类型
     */
    int operateType() default 0;
}
