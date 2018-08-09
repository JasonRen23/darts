package com.rzc.mvc;

import com.rzc.mvc.handler.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JasonRen
 * @since 2018/8/9 下午3:21
 */
@Slf4j
public class DisPatcherServlet extends HttpServlet {

    /**
     * 请求执行链
     */
   private final List<Handler> HANDLER = new ArrayList<>();

    /**
     * 初始化Servlet
     * @throws ServletException ServletException
     */
    @Override
    public void init() throws ServletException {
        HANDLER.add(new PreRequestHandler());
        HANDLER.add(new SimpleUrlHandler(getServletContext()));
        HANDLER.add(new JspHandler(getServletContext()));
        HANDLER.add(new ControllerHandler());
    }


    /**
     * 执行请求
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        RequestHandlerChain handlerChain = new RequestHandlerChain(HANDLER.iterator(), req, resp);
        handlerChain.doHandlerChain();
        handlerChain.doRender();
    }
}
