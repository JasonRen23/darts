package com.rzc.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author JasonRen
 * @since 2018/8/9 下午12:03
 */

/**
 * ControllerInfo 存储Controller相关信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerInfo {
    /**
     * Controller类
     */
    private Class<?> controllerClass;

    /**
     * 执行的方法
     */
    private Method invokeMethod;

    /**
     * 方法参数名对应参数类型
     */
    private Map<String, Class<?>> methodParameter;


}
