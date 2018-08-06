package com.rzc.aop;

import com.rzc.aop.advice.Advice;
import com.rzc.aop.advice.AfterReturnAdvice;
import com.rzc.aop.advice.MethodBeforeAdvice;
import com.rzc.aop.advice.ThrowAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;

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
     * 执行代理方法
     */
    public Object doProxy(Object target, Class<?> targetClass, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if(!pointCut.matches(method)) {
            return proxy.invokeSuper(target, args);
        }

        Object result = null;

        if(advice instanceof MethodBeforeAdvice) {
            ((MethodBeforeAdvice) advice).before(targetClass, method, args);
        }
        try{
            //执行目标类的方法
            result = proxy.invokeSuper(target, args);
            if(advice  instanceof AfterReturnAdvice) {
                ((AfterReturnAdvice) advice).afterReturning(targetClass, result, method, args);
            }
        }catch(Exception e){
            if (advice instanceof ThrowAdvice) {
                ((ThrowAdvice) advice).afterThrowing(targetClass, method, args, e);
            }else {
                throw new Throwable(e);
            }
        }

        return result;
    }
}
