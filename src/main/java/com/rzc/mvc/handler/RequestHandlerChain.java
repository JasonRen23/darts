package com.rzc.mvc.handler;

import com.rzc.mvc.render.DefaultRender;
import com.rzc.mvc.render.InternalErrorRender;
import com.rzc.mvc.render.Render;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * @author JasonRen
 * @since 2018/8/9 下午7:31
 */
@Data
@Slf4j
public class RequestHandlerChain {

    /**
     * Handler迭代器
     * {@link Handler}
     */
    private Iterator<Handler> handlerIterator;

    /**
     * 请求request
     * {@link HttpServletRequest}
     */
    private HttpServletRequest request;

    /**
     * 请求response
     * {@link HttpServletResponse}
     */
    private HttpServletResponse response;

    /**
     * 请求http方法
     */
    private String requestMethod;

    private String requestPath;

    private int responseStatus;

    private Render render;


    public RequestHandlerChain(final Iterator<Handler> handlerIterator, final HttpServletRequest request, final HttpServletResponse response) {
        this.handlerIterator = handlerIterator;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseStatus = HttpServletResponse.SC_OK;
    }

    public void doHandlerChain() {
        try {
            while (handlerIterator.hasNext()) {
                if (!handlerIterator.next().handle(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("doHandlerChain error", e);
            render = new InternalErrorRender();
        }
    }

    public void doRender() {
        if (null == render) {
            render = new DefaultRender();
        }
        try {
            render.render(this);
        } catch (Exception e) {
            log.error("doRender", e);
            throw new RuntimeException(e);
        }
    }

}
