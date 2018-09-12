package com.rzc.bean;

import com.rzc.core.annotation.Service;

/**
 * @author JasonRen
 * @since 2018/8/7 上午12:04
 */
@Service
public class DartsServiceImpl implements DartsService {
    @Override
    public String helloWorld() {
        return "hello world!";
    }
}
