package com.rzc.aop.advice;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:27
 */

/**
 * 环绕通知接口
 */
public interface AroundAdvice extends MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
}
