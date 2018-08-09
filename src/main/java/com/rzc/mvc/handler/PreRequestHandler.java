package com.rzc.mvc.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author JasonRen
 * @since 2018/8/9 下午9:09
 */
@Slf4j
public class PreRequestHandler implements Handler {
    @Override
    public boolean handle(final RequestHandlerChain handlerChain) {
        //设置请求编码方式
        handlerChain.getResponse().setCharacterEncoding("UTF-8");
        String requestPath = handlerChain.getRequestPath();
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            handlerChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        log.info("[Darts] {} {}", handlerChain.getRequestMethod(), handlerChain.getRequestPath());
        return true;
    }
}
