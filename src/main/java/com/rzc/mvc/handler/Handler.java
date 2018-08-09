package com.rzc.mvc.handler;

/**
 * 请求执行器
 * @author JasonRen
 * @since 2018/8/9 下午7:30
 */
public interface Handler {


    boolean handle(final RequestHandlerChain handlerChain) throws Exception;
}
