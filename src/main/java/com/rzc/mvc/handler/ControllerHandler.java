package com.rzc.mvc.handler;

import com.rzc.core.BeanContainer;
import com.rzc.mvc.ControllerInfo;
import com.rzc.mvc.PathInfo;
import com.rzc.mvc.annotation.RequestMapping;
import com.rzc.mvc.annotation.RequestMethod;
import com.rzc.mvc.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JasonRen
 * @since 2018/8/9 下午12:08
 */

/**
 * Controller分发器
 */
@Slf4j
public class ControllerHandler {

    private Map<PathInfo, ControllerInfo> pathControllerMap = new ConcurrentHashMap<>();

    private BeanContainer beanContainer;

    public ControllerHandler() {
        beanContainer = BeanContainer.getInstance();
        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(RequestMapping.class);
        for (Class<?> clz : classSet) {
            putPathController(clz);
        }
    }

    public ControllerInfo getController(String requestMethod, String requestPath) {
        PathInfo pathInfo = new PathInfo(requestMethod, requestPath);
        return pathControllerMap.get(pathInfo);
    }


    /**
     * 添加信息到requestControllerMap中
     * @param clz 类名
     */
    private void putPathController(Class<?> clz) {
        RequestMapping controllerRequest = clz.getAnnotation(RequestMapping.class);
        String basePath = controllerRequest.value();
        Method[] controllerMethods = clz.getDeclaredMethods();
        //1.遍历Controller中的方法
        for (Method method : controllerMethods)
            if (method.isAnnotationPresent(RequestMapping.class)) {
                //2.获取这个方法的参数名字和参数类型
                Map<String, Class<?>> params = new HashMap<>();
                for (Parameter methodParam : method.getParameters()) {
                    RequestParam requestParam = methodParam.getAnnotation(RequestParam.class);
                    if (null == requestParam) {
                        throw new RuntimeException("必须有RequestParam指定的参数名");
                    }
                    params.put(requestParam.value(), methodParam.getType());
                }
                //3.获取这个方法上的RequestMapping注释
                RequestMapping methodRequest = method.getAnnotation(RequestMapping.class);
                String methodPath = methodRequest.value();
                RequestMethod requestMethod = methodRequest.method();
                PathInfo pathInfo = new PathInfo(requestMethod.toString(), basePath + methodPath);
                if (pathControllerMap.containsKey(pathInfo)) {
                    log.error("url:{}重复注册！", pathInfo.getHttpPath());
                    throw new RuntimeException("url重复注册！");
                }
                //4.生成ControllerInfo并存入Map中
                ControllerInfo controllerInfo = new ControllerInfo(clz, method, params);
                this.pathControllerMap.put(pathInfo, controllerInfo);
                log.info("Add DartsController RequestMethod: {}, RequestPath:{}, DartsController:{}, Method:{}",
                    pathInfo.getHttpMethod(), pathInfo.getHttpPath(),
                    controllerInfo.getControllerClass().getName(), controllerInfo.getInvokeMethod().getName());
            }
    }
}
