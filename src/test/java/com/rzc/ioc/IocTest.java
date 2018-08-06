package com.rzc.ioc;

import com.rzc.bean.DartsController;
import com.rzc.core.BeanContainer;
import org.junit.Test;

import static org.junit.Assert.*;

public class IocTest {

    @Test
    public void doIoc() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.rzc");
        new Ioc().doIoc();
        DartsController dartsController = (DartsController) beanContainer.getBean(DartsController.class);
        dartsController.hello();
    }
}