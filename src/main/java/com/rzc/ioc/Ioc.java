package com.rzc.ioc;

import com.rzc.core.BeanContainer;
import com.rzc.ioc.annotation.Autowired;
import com.rzc.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author JasonRen
 * @since 2018/8/6 下午11:44
 */
public class Ioc {
    private BeanContainer beanContainer;

    public Ioc() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doIoc() {
        for (Class<?> clz : beanContainer.getClasses()) {
            final Object targetBean = beanContainer.getBean(clz);
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    final Class<?> fieldClass = field.getType();
                    Object fieldValue = getClassInstance(fieldClass);
                    if (null != fieldValue) {
                        ClassUtil.setField(field, targetBean, fieldValue);
                    } else {
                        throw new RuntimeException("无法注入对应的类，目标类型：" + fieldClass.getName());
                    }
                }
            }

        }
    }

    /**
     * 根据类名获取其实例或实现类
     *
     * @param clz 类名
     * @return 实例或实现类
     */
    private Object getClassInstance(final Class<?> clz) {
        return Optional
            .ofNullable(beanContainer.getBean(clz))
            .orElseGet(() -> {
                Class<?> implementClass = getImplementClass(clz);
                if (null != implementClass) {
                    return beanContainer.getBean(implementClass);
                }
                return null;
            });
    }


    /**
     * 获取接口的实现类
     *
     * @param interfaceClass 接口
     * @return 实现类
     */
    private Class<?> getImplementClass(final Class<?> interfaceClass) {
        return beanContainer.getClassBySuper(interfaceClass)
            .stream()
            .findFirst()
            .orElse(null);
    }
}
