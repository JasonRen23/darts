package com.rzc.mvc.render;

import com.rzc.mvc.handler.RequestHandlerChain;

/**
 * @author JasonRen
 * @since 2018/8/9 下午7:45
 */
public class DefaultRender implements Render {
    @Override
    public void render(final RequestHandlerChain handlerChain) {
        int status = handlerChain.getResponseStatus();
        handlerChain.getResponse().setStatus(status);
    }
}
