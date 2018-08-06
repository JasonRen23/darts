package com.rzc.aop.advice;

import java.lang.reflect.Method;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:23
 */
public interface MethodBeforeAdvice extends Advice {
    /**
     * 前置方法
     */
    void before(Class<?> clz, Method method, Object[] args) throws Throwable;
}
