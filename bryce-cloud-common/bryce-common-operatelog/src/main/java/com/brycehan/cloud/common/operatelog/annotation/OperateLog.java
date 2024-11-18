package com.brycehan.cloud.common.operatelog.annotation;

import java.lang.annotation.*;

/**
 * 操作日志
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {

    /**
     * 模块名
     */
    String moduleName() default "";

    /**
     * 操作名
     */
    String name() default "";

    /**
     * 操作类型
     */
    OperatedType type() default OperatedType.OTHER;

}
