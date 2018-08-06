package com.rzc.aop;

import com.rzc.aop.advice.Advice;
import com.rzc.aop.annotation.Aspect;
import com.rzc.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:35
 */
@Slf4j
public class Aop {
    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public Aop() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        // beanContainer.getClassBySuper(Advice.class)
        //     .stream()
        //     .filter(clz -> clz.isAnnotationPresent(Aspect.class))
        //     .forEach(clz -> {
        //         final Advice advice = (Advice) beanContainer.getBean(clz);
        //         Aspect aspect = clz.getAnnotation(Aspect.class);
        //         beanContainer.getClassesByAnnotation(aspect.target())
        //             .stream()
        //             .filter(target -> !Advice.class.isAssignableFrom(target))
        //             .filter(target -> !target.isAnnotationPresent(Aspect.class))
        //             .forEach(target -> {
        //                 ProxyAdvisor advisor = new ProxyAdvisor(advice);
        //                 Object proxyBean = ProxyCreator.createProxy(target, advisor);
        //                 beanContainer.addBean(target, proxyBean);
        //             });
        //     });

        beanContainer.getClassBySuper(Advice.class)
            .stream()
            .filter(clz -> clz.isAnnotationPresent(Aspect.class))
            .map(this::createProxyAdvisor)
            .forEach(proxyAdvisor -> beanContainer.getClasses()
                    .stream()
                    .filter(target -> !Advice.class.isAssignableFrom(target))
                    .filter(target -> !target.isAnnotationPresent(Aspect.class))
                    .forEach(target -> {
                        if(proxyAdvisor.getPointCut().matches(target)) {
                            Object proxtBean = ProxyCreator.createProxy(target, proxyAdvisor);
                            beanContainer.addBean(target, proxtBean);
                        }
                    }));
    }

    /**
     * 通过Aspect切面类创建代理通知类
     * @param aspectClass 切面类
     * @return 代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        String expression = aspectClass.getAnnotation(Aspect.class).pointCut();
        ProxyPointCut proxyPointCut = new ProxyPointCut();
        proxyPointCut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointCut);
    }
}
