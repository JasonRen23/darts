package com.rzc.aop.annotation;

import java.lang.annotation.*;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    // /**
    //  * 目标代理类的范围
    //  */
    // Class<? extends Annotation> target();

    /**
     * 切点表达式
     */
    String pointCut() default "";
}

