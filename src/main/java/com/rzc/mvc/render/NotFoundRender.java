package com.rzc.mvc.render;

import com.rzc.mvc.handler.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 渲染404
 *
 * @author JasonRen
 * @since 2018/8/9 下午8:26
 */
public class NotFoundRender implements Render{

    @Override
    public void render(final RequestHandlerChain handlerChain) throws IOException {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
