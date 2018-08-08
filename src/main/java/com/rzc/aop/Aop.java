package com.rzc.aop;

import com.rzc.aop.advice.Advice;
import com.rzc.aop.annotation.Aspect;
import com.rzc.aop.annotation.Order;
import com.rzc.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        //创建所有的代理通知列表
        List<ProxyAdvisor> proxyList = beanContainer.getClassBySuper(Advice.class)
            .stream()
            .filter(clz -> clz.isAnnotationPresent(Aspect.class))
            .map(this::createProxyAdvisor)
            .collect(Collectors.toList());

        //创建代理类并注入到容器中
        // beanContainer.getClassBySuper(Advice.class)
        //     .stream()
        //     .filter(clz -> clz.isAnnotationPresent(Aspect.class))
        //     .map(this::createProxyAdvisor)
        //     .forEach(proxyAdvisor -> beanContainer.getClasses()
        //             .stream()
        //             .filter(target -> !Advice.class.isAssignableFrom(target))
        //             .filter(target -> !target.isAnnotationPresent(Aspect.class))
        //             .forEach(target -> {
        //                 if(proxyAdvisor.getPointCut().matches(target)) {
        //                     Object proxtBean = ProxyCreator.createProxy(target, proxyAdvisor);
        //                     beanContainer.addBean(target, proxtBean);
        //                 }
        //             }));
        beanContainer.getClasses()
            .stream()
            .filter(clz -> !Advice.class.isAssignableFrom(clz))
            .filter(clz -> !clz.isAnnotationPresent(Aspect.class))
            .forEach(clz -> {
                List<ProxyAdvisor> matchProxies = createMatchProxies(proxyList, clz);
                if (matchProxies.size() > 0) {
                    Object proxyBean = ProxyCreator.creatProxy(clz, matchProxies);
                    beanContainer.addBean(clz, proxyBean);
                }
            });
    }

    /**
     * 通过Aspect切面类创建代理通知类
     *
     * @param aspectClass 切面类
     * @return 代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        int order = 0;
        if (aspectClass.isAnnotationPresent(Order.class)) {
            order = aspectClass.getAnnotation(Order.class).value();
        }
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointCut proxyPointCut = new ProxyPointCut();
        proxyPointCut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointCut, order);
    }

    /**
     * 获取目标类匹配的代理通知
     */
    private List<ProxyAdvisor> createMatchProxies(List<ProxyAdvisor> proxyList, Class<?> targetClass) {
        Object targetBean = beanContainer.getBean(targetClass);
        return proxyList
            .stream()
            .filter(advisor -> advisor.getPointCut().matches(targetBean.getClass()))
            .sorted(Comparator.comparingInt(ProxyAdvisor::getOrder))
            .collect(Collectors.toList());
    }
}
