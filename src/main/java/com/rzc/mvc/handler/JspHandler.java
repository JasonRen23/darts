package com.rzc.mvc.handler;

import com.rzc.Darts;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author JasonRen
 * @since 2018/8/9 下午9:08
 */
public class JspHandler implements Handler {

    private static final String JSP_SERVLET = "jsp";

    private RequestDispatcher jspServlet;

    public JspHandler(ServletContext servletContext) {
        jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (null == jspServlet) {
            throw new RuntimeException("没有jsp Servlet");
        }
    }

    @Override
    public boolean handle(final RequestHandlerChain handlerChain) throws ServletException, IOException {
        if (isPageView(handlerChain.getRequestPath())) {
            jspServlet.forward(handlerChain.getRequest(), handlerChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否为jsp资源
     *
     * @param url 请求路径
     * @return 是否为jsp资源
     */
    private boolean isPageView(String url) {
        return url.startsWith(Darts.getConfiguration().getViewPath());
    }
}
