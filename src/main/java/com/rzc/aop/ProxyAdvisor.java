package com.rzc.aop;

import com.rzc.aop.advice.Advice;
import com.rzc.aop.advice.AfterReturningAdvice;
import com.rzc.aop.advice.MethodBeforeAdvice;
import com.rzc.aop.advice.ThrowsAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:29
 */

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProxyAdvisor {
    /**
     * 通知
     */
    private Advice advice;

    /**
     * AspectJ表达式切点匹配器
     */
    private ProxyPointCut pointCut;

    /**
     * 执行顺序
     */
    private int order;


    // public Object doProxy(Object target, Class<?> targetClass, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    //     if(!pointCut.matches(method)) {
    //         return proxy.invokeSuper(target, args);
    //     }
    //
    //     Object result = null;
    //
    //     if(advice instanceof MethodBeforeAdvice) {
    //         ((MethodBeforeAdvice) advice).before(targetClass, method, args);
    //     }
    //     try{
    //         //执行目标类的方法
    //         result = proxy.invokeSuper(target, args);
    //         if(advice  instanceof AfterReturningAdvice) {
    //             ((AfterReturningAdvice) advice).afterReturning(targetClass, result, method, args);
    //         }
    //     }catch(Exception e){
    //         if (advice instanceof ThrowsAdvice) {
    //             ((ThrowsAdvice) advice).afterThrowing(targetClass, method, args, e);
    //         }else {
    //             throw new Throwable(e);
    //         }
    //     }
    //
    //     return result;
    // }

    /**
     * 执行代理方法
     */
    public Object doProxy(AdviceChain adviceChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = adviceChain.getTargetClass();
        Method method = adviceChain.getMethod();
        Object[] args = adviceChain.getArgs();

        if (advice instanceof MethodBeforeAdvice) {
            ((MethodBeforeAdvice) advice).before(targetClass, method, args);
        }
        try {
            result = adviceChain.doAdviceChain(); //执行代理链方法
            if (advice instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advice).afterReturning(targetClass, result, method, args);
            }
        } catch (Exception e) {
            if (advice instanceof ThrowsAdvice) {
                ((ThrowsAdvice) advice).afterThrowing(targetClass, method, args, e);
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }
}
