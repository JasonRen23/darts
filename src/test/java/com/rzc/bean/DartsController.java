package com.rzc.bean;

import com.rzc.core.annotation.Controller;
import com.rzc.ioc.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:04
 */
@Controller
@Slf4j
public class DartsController {
    @Autowired
    private DartsService dartsService;

    public void hello() {
        log.info(dartsService.helloWorld());
    }

    public void helloForAspect() {
        log.info("Hello Aspectj!");
    }
}
