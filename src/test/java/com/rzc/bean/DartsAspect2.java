package com.rzc.bean;

import com.rzc.aop.advice.AroundAdvice;
import com.rzc.aop.annotation.Aspect;
import com.rzc.aop.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author JasonRen
 * @since 2018/8/8 上午2:08
 */
@Slf4j
@Order(2)
@Aspect(pointcut = "@within(com.rzc.core.annotation.Controller)")
public class DartsAspect2 implements AroundAdvice {
    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable{
        log.info("------------------before DartsAspect2-----------------");
        log.info("Class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable{
        log.info("------------------after DartsAspect2-----------------");
        log.info("Class: {}, method: {}", clz, method.getName());}

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        log.info("------------------error DartsAspect2-----------------");
        log.info("Class: {}, method: {}", clz, method.getName(), e.getMessage());
    }
}
