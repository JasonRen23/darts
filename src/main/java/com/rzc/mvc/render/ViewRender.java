package com.rzc.mvc.render;

import com.rzc.Darts;
import com.rzc.mvc.bean.ModelAndView;
import com.rzc.mvc.handler.RequestHandlerChain;
import com.rzc.util.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 渲染页面
 *
 * @author JasonRen
 * @since 2018/8/9 下午8:56
 */
public class ViewRender implements Render {

    private ModelAndView modelAndView;

    public ViewRender(Object modelAndView) {
        if (modelAndView instanceof ModelAndView) {
            this.modelAndView = (ModelAndView) modelAndView;
        } else if (modelAndView instanceof String) {
            this.modelAndView = new ModelAndView().setView((String) modelAndView);
        } else {
            throw new RuntimeException("返回类型不合法");
        }
    }

    @Override
    public void render(final RequestHandlerChain handlerChain) throws ServletException, IOException {
        HttpServletRequest request = handlerChain.getRequest();
        HttpServletResponse response = handlerChain.getResponse();
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        model.forEach(request::setAttribute);
        request.getRequestDispatcher(Darts.getConfiguration().getViewPath() + path).forward(request, response);
    }
}
