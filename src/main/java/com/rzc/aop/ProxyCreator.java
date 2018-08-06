package com.rzc.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:45
 */
public final class ProxyCreator {
    /**
     * 创建代理类
     */
    public static Object createProxy(Class<?> targetClass, ProxyAdvisor proxyAdvisor) {
        return Enhancer.create(targetClass, (MethodInterceptor) (target, method, args, proxy) ->
                                                proxyAdvisor.doProxy(target, targetClass, method, args, proxy));
    }

}
