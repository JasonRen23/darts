package com.rzc.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author JasonRen
 * @since 2018/8/7 上午2:56
 */
public class AdviceChain {

    /**
     * 目标类
     */
    @Getter
    private final Class<?> targetClass;

    /**
     * 目标实例
     */
    @Getter
    private final Object target;

    /**
     * 目标方法
     */
    @Getter
    private final Method method;
    /**
     * 目标方法参数
     */
    @Getter
    protected final Object[] args;

    //代理方法
    private final MethodProxy methodProxy;

    //代理通知列
    private List<ProxyAdvisor> proxyList;

    //代理通知列index
    private int adviceIndex = 0;

    public AdviceChain(final Class<?> targetClass, final Object target, final Method method, final Object[] args, final MethodProxy methodProxy, final List<ProxyAdvisor> proxyList) {
        this.targetClass = targetClass;
        this.target = target;
        this.method = method;
        this.args = args;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }


    /**
     * 递归执行，执行代理通知列
     */
    public Object doAdviceChain() throws Throwable {
        Object result;
        while (adviceIndex < proxyList.size()
            && !proxyList.get(adviceIndex).getPointCut().matches(method)) {
            //如果当前方法不匹配切点，则掠过该代理通知类
            adviceIndex++;
        }
        if (adviceIndex < proxyList.size()) {
            result = proxyList.get(adviceIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(target, args);
        }

        return result;
    }
}
