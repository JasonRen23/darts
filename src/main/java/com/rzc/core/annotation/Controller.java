package com.rzc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记Controller层的组件
 *
 * @author : JasonRen
 * @date : 2018-07-21 下午12:41
 * @email : zhicheng_ren@163.com
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
