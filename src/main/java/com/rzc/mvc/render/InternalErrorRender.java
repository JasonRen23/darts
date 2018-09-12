package com.rzc.mvc.render;

import com.rzc.mvc.handler.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染状态码500
 *
 * @author JasonRen
 * @since 2018/8/9 下午7:43
 */
public class InternalErrorRender implements Render {
    @Override
    public void render(final RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
