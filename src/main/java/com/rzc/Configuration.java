package com.rzc;

import lombok.Builder;
import lombok.Getter;

/**
 * @author JasonRen
 * @since 2018/8/9 下午3:34
 */
@Builder
@Getter
public class Configuration {

    /**
     * 启动类
     */
    private Class<?> bootClass;

    /**
     * 资源目录
     */
    @Builder.Default
    private String resourcePath = "/src/main/resources/";

    /**
     * jsp目录
     */
    @Builder.Default
    private String viewPath = "/templates/";

    /**
     * 静态文件目录
     */
    @Builder.Default
    private String assetPath = "/static";

    /**
     * 端口号
     */
    @Builder.Default
    private int serverPort = 8888;

    /**
     * tomcat docBase目录
     */
    @Builder.Default
    private String docBase = "";

    /**
     * tomcat contextPath目录
     */
    @Builder.Default
    private String contextPath = "";
}
