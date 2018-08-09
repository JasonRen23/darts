package com.rzc.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JasonRen
 * @since 2018/8/9 上午2:08
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    /**
     * 方法参数名
     */
    String value() default "";

    /**
     * 是否必传
     */
    boolean required() default true;
}
