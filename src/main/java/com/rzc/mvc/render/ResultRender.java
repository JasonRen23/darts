package com.rzc.mvc.render;

import com.alibaba.fastjson.JSON;
import com.rzc.Darts;
import com.rzc.core.BeanContainer;
import com.rzc.mvc.ControllerInfo;
import com.rzc.mvc.annotation.ResponseBody;
import com.rzc.mvc.bean.ModelAndView;
import com.rzc.util.CastUtil;
import com.rzc.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author JasonRen
 * @since 2018/8/9 下午1:41
 */
@Slf4j
public class ResultRender {

    private BeanContainer beanContainer;

    public ResultRender() {
        beanContainer = BeanContainer.getInstance();
    }

    public void invokeController(HttpServletRequest request, HttpServletResponse response, ControllerInfo controllerInfo) {
        //1.获取HttpServletRequest所有参数
        Map<String, String> requestParam = getRequestParams(request);
        //2.实例化调用方法要传入的参数值
        List<Object> methodParams = instantiateMethodArgs(controllerInfo.getMethodParameter(), requestParam);

        Object controller = beanContainer.getBean(controllerInfo.getControllerClass());
        Method invokeMethod = controllerInfo.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        //3.通过反射调用方法
        try {
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //4. 解析方法的返回值，选择返回页面或者json
        resultResolver(controllerInfo, result, request, response);
    }

    /**
     * 获取http中的参数
     *
     * @param request Servlet请求
     * @return 参数集合
     */

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        //GET和POST方法是这样获取请求参数的
        request.getParameterMap().forEach((paramName, paramValues) -> {
            if (ValidateUtil.isNotEmpty(paramValues)) {
                paramMap.put(paramName, paramValues[0]);
            }
        });
        return paramMap;
    }

    /**
     * 实例化方法参数
     *
     * @param methodParams  方法参数
     * @param requestParams 请求参数
     * @return 实例化方法参数集合
     */

    private List<Object> instantiateMethodArgs(Map<String, Class<?>> methodParams, Map<String, String> requestParams) {
        return methodParams.keySet().stream().map(paramName -> {
            Class<?> type = methodParams.get(paramName);
            String requestValue = requestParams.get(paramName);
            Object value;
            if (null == requestValue) {
                value = CastUtil.primitiveNull(type);
            } else {
                value = CastUtil.convert(type, requestValue);
            }
            return value;
        }).collect(Collectors.toList());
    }

    /**
     * Controller方法执行后返回值解析
     *
     * @param controllerInfo 控制器信息
     * @param result         结果
     * @param request        请求
     * @param response       响应
     */

    private void resultResolver(ControllerInfo controllerInfo, Object result, HttpServletRequest request, HttpServletResponse response) {
        if (null == result) {
            return;
        }

        boolean isJson = controllerInfo.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            //设置响应头
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            //向响应中写入数据
            try (PrintWriter writer = response.getWriter()) {
                writer.write(JSON.toJSONString(result));
                writer.flush();
            } catch (IOException e) {
                log.error("转发请求失败", e);
            }
        } else {
            String path;
            if (result instanceof ModelAndView) {
                ModelAndView modelAndView = (ModelAndView) result;
                path = modelAndView.getView();
                Map<String, Object> model = modelAndView.getModel();
                if (ValidateUtil.isNotEmpty(model)) {
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
            } else if (result instanceof String) {
                path = (String) result;
            } else {
                throw new RuntimeException("返回类型不合法");
            }
            try {
                // request.getRequestDispatcher("/templates/" + path).forward(request, response);
                Darts.getConfiguration().getResourcePath();
                request.getRequestDispatcher(Darts.getConfiguration().getResourcePath() + path).forward(request, response);
            } catch (Exception e) {
                log.error("转发请求失败", e);
            }
        }
    }
}
