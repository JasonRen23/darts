package com.rzc.aop.advice;

import java.lang.reflect.Method;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:26
 */
public interface ThrowAdvice extends Advice {
    /**
     * 环绕通知接口
     */
    void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e);
}
