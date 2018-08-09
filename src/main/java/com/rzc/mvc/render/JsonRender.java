package com.rzc.mvc.render;

import com.alibaba.fastjson.JSON;
import com.rzc.mvc.handler.RequestHandlerChain;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染json
 *
 * @author JasonRen
 * @since 2018/8/9 下午8:56
 */
@Slf4j
public class JsonRender implements Render{

    private Object jsonData;

    public JsonRender(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void render(final RequestHandlerChain handlerChain) throws IOException {
        //设置响应头
        handlerChain.getResponse().setContentType("application/json");
        handlerChain.getResponse().setCharacterEncoding("UTF-8");
        //向响应中写入数据
        try (PrintWriter writer = handlerChain.getResponse().getWriter()) {
            writer.write(JSON.toJSONString(jsonData));
            writer.flush();
        }
    }
}
