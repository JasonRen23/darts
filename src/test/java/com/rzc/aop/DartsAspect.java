package com.rzc.aop;

import com.rzc.aop.advice.AroundAdvice;
import com.rzc.aop.annotation.Aspect;
import com.rzc.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:49
 */
@Slf4j
// @Aspect(target = Controller.class)
@Aspect(pointCut = "execution(* com.rzc.bean.DartsController.helloForAspect(..))")
public class DartsAspect implements AroundAdvice {
    @Override
    public void before(final Class<?> clz, final Method method, final Object[] args) {
        log.info("Before DartsAspect -----> class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void afterReturning(final Class<?> clz, final Object returnValue, final Method method, final Object[] args) {
        log.info("After DartsAspect -----> class: {}, method: {}", clz, method.getName());
    }

    @Override
    public void afterThrowing(final Class<?> clz, final Method method, final Object[] args, final Throwable e) {
        log.info("Error DartsAspect -----> class: {}, method: {}, expection: {}", clz, method.getName(), e.getMessage());
    }
}
