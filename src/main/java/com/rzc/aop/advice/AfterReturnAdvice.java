package com.rzc.aop.advice;

import java.lang.reflect.Method;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:24
 */
public interface AfterReturnAdvice extends Advice {
    void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable;
}
