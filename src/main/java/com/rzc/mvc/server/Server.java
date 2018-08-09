package com.rzc.mvc.server;

/**
 * @author JasonRen
 * @since 2018/8/9 下午3:39
 */
public interface Server {

    /**
     * 启动服务器
     */
    void startServer() throws Exception;

    /**
     * 停止服务器
     */
    void stopServer() throws Exception;
}
