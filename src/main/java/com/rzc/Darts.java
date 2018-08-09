package com.rzc;

import com.rzc.aop.Aop;
import com.rzc.core.BeanContainer;
import com.rzc.ioc.Ioc;
import com.rzc.mvc.server.Server;
import com.rzc.mvc.server.TomcatServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JasonRen
 * @since 2018/8/9 下午4:03
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class Darts {
    /**
     * 全局配置
     */
    @Getter
    private static Configuration configuration = Configuration.builder().build();

    /**
     * 默认服务器
     */
    @Getter
    private static Server server;

    /**
     * 启动
     * @param bootClass 启动类
     */
    public static void run(Class<?> bootClass) {
        run(Configuration.builder().bootClass(bootClass).build());
    }

    /**
     * 启动
     * @param configuration 配置类
     */
    public static void run(Configuration configuration) {
        new Darts().start(configuration);
    }

    /**
     * 初始化
     * @param configuration 配置类
     */
    private void start(Configuration configuration) {
        try{
            Darts.configuration = configuration;
            String basepackage = configuration.getBootClass().getPackage().getName();
            BeanContainer.getInstance().loadBeans(basepackage);
            new Aop().doAop();
            new Ioc().doIoc();

            server = new TomcatServer(configuration);
            server.startServer();
        }catch(Exception e){
            log.error("Darts 启动失败了！", e);
        }
    }


}
