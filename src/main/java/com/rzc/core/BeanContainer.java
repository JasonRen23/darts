package com.rzc.core;

import com.rzc.aop.annotation.Aspect;
import com.rzc.core.annotation.Component;
import com.rzc.core.annotation.Controller;
import com.rzc.core.annotation.Repository;
import com.rzc.core.annotation.Service;
import com.rzc.util.ClassUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Bean容器
 *
 * @author JasonRen
 * @since 2018-08-06 上午1:44
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    /**
     * 存放所有Bean的Map
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 是否加载Bean
     */
    private boolean isLoadBean = false;

    /**
     * 获取Bean容器实例
     *
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
        = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    public void loadBeans(String basePackage) {
        if(isLoadBean()){
            log.warn("bean已经加载");
            return;
        }

        Set<Class<?>> classSet = ClassUtil.getPackageClass(basePackage);
        classSet.stream()
                .filter(clz -> {
                    for(Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                        if(clz.isAnnotationPresent(annotation))
                            return true;
                    }
                    return false;
                })
            .forEach(clz -> beanMap.put(clz, ClassUtil.newInstance(clz)));
        isLoadBean = true;
    }

    /**
     *
     * 是否已经加载
     */
    public boolean isLoadBean() {
        return isLoadBean;
    }

    /**
     * 获取单一Bean实例
     *
     * @param clazz 类名
     * @return Bean实例
     */
    public Object getBean(Class<?> clazz) {


        if (null == clazz) {
            return null;
        }
        return beanMap.get(clazz);
    }

    /**
     * 获取所有Bean集合
     *
     * @return Bean集合
     */

    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }


    /**
     * 添加一个Bean实例
     *
     * @param clazz 类名
     * @param bean  bean实例
     * @return 抽象对象
     */

    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 移除一个Bean实例
     *
     * @param clazz 类名
     */
    public void removeBean(Class<?> clazz) {
        beanMap.remove(clazz);
    }

    /**
     * Bean实例数量
     *
     * @return 实例数量
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 所有Bean的Class集合
     *
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * @param annotation 注解
     * @return Class集合
     */

    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        return beanMap.keySet()
            .stream()
            .filter(clz -> clz.isAnnotationPresent(annotation))
            .collect(Collectors.toSet());
    }

    /**
     * @param superClass 父类
     * @return Class集合
     */

    public Set<Class<?>> getClassBySuper(Class<?> superClass) {
        return beanMap.keySet()
            .stream()
            .filter(superClass::isAssignableFrom)
            .filter(clz -> !clz.equals(superClass))
            .collect(Collectors.toSet());
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

}
