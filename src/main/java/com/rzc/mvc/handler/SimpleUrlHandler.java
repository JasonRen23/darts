package com.rzc.mvc.handler;

import com.rzc.Darts;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author JasonRen
 * @since 2018/8/9 下午9:13
 */
@Slf4j
public class SimpleUrlHandler implements Handler {
    public static void main(String[] args) {

    }

    /**
     * tomcat默认RequestDispatcher的名称
     * TODO: 其他服务器默认的RequestDispatcher. 如WebLogic为FileServlet
     */
    private static final String TOMCAT_DEFAULT_SERVLET = "default";

    /**
     * 默认的RequestDispatcher,处理静态资源
     */
    private RequestDispatcher defaultServlet;

    public SimpleUrlHandler(ServletContext servletContext) {
        defaultServlet = servletContext.getNamedDispatcher(TOMCAT_DEFAULT_SERVLET);
        if (null == defaultServlet) {
            throw new RuntimeException("没有默认的Servlet");
        }

        log.info("The default servlet for sering static resource is [{}]", TOMCAT_DEFAULT_SERVLET);
    }

    @Override
    public boolean handle(final RequestHandlerChain handlerChain) throws ServletException, IOException {
        if (isStaticResource(handlerChain.getRequestPath())) {
            defaultServlet.forward(handlerChain.getRequest(), handlerChain.getResponse());
            return false;
        }

        return true;
    }

    private boolean isStaticResource(String url) {
        return url.startsWith(Darts.getConfiguration().getAssetPath());
    }
}
