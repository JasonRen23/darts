package com.rzc.aop;

import com.rzc.bean.DartsController;
import com.rzc.core.BeanContainer;
import com.rzc.ioc.Ioc;
import org.junit.Test;

import static org.junit.Assert.*;

public class AopTest {

    @Test
    public void doAop() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.rzc");
        new Aop().doAop();
        new Ioc().doIoc();
        DartsController controller = (DartsController) beanContainer.getBean(DartsController.class);
        controller.hello();
        controller.helloForAspect();
    }
}