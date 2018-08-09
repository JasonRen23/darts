package com.rzc.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JasonRen
 * @since 2018/8/9 下午12:05
 */

/**
 * PathInfo 存储http相关信息，请求方法路径和请求方法类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathInfo {
    /**
     * http请求方法类型
     */
    private String httpMethod;

    /**
     * http请求路径
     */
    private String httpPath;


}
