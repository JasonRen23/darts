package com.rzc.mvc.render;

import com.rzc.mvc.handler.RequestHandlerChain;

/**
 * 渲染结果请求接口
 *
 * @author JasonRen
 * @since 2018/8/9 下午7:42
 */
public interface Render {
    /**
     * 执行渲染
     *
     * @param handlerChain {@link RequestHandlerChain}
     * @throws Exception Exception
     */
    void render(RequestHandlerChain handlerChain) throws Exception;
}
