package com.rzc.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;


/**
 * 类操作工具类
 *
 * @author : JasonRen
 * @date : 2018/07/18
 * @email : zhicheng_ren@163.com
 */
@Slf4j
public final class ClassUtil {
    /**
     * file形式url协议
     */

    public static final String FILE_PROTOCOL = "file";

    /**
     * jar形式协议
     */

    public static final String JAR_PROTOCOL = "jar";

    /**
     * @Description: 获取ClassLoader
     * @return: 当前ClassLoader
     */

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * @param className class全名
     * @Description: 获取Class
     * @return: Class
     */

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            log.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param className class全名
     * @Description: 实例化Class
     * @return: 类的实例化
     */

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        try {
            Class<?> clazz = loadClass(className);
            return (T) clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param clazz Class
     * @Description: 实例化class
     * @return: 类的实例化
     */

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param field  属性
     * @param target 类实例
     * @param value  值
     * @Description: 设置类的属性值
     * @return:
     */

    public static void setField(Field field, Object target, Object value) {
        setField(field, target, value, true);
    }

    /**
     * @param field      属性
     * @param target     类实例
     * @param value      值
     * @param accessible 是否允许设置私有属性
     * @Description: 设置类的属性值
     * @return:
     */

    public static void setField(Field field, Object target, Object value, boolean accessible) {
        field.setAccessible(accessible);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("setField error", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * @param basePackage 包的路径
     * @Description: 获取包下的集合类
     * @return: 类集合
     */

    public static Set<Class<?>> getPackageClass(final String basePackage) {
        URL url = getClassLoader()
            .getResource(basePackage.replace(".", "/"));
        if (null == url) {
            throw new RuntimeException("无法获取项目路径文件");
        }

        try {
            if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
                File file = new File(url.getFile());
                final Path basePath = file.toPath();
                return Files.walk(basePath)
                    .filter(path -> path.toFile().getName().endsWith(".class"))
                    .map(path -> getClassByPath(path, basePath, basePackage))
                    .collect(Collectors.toSet());
            } else if (url.getProtocol().equalsIgnoreCase(JAR_PROTOCOL)) {
                //若在jar包中，则解析jar中的entry
                JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                return jarURLConnection.getJarFile()
                    .stream()
                    .filter(jarEntry -> jarEntry.getName().endsWith(".class"))
                    .map(ClassUtil::getClassByJar)
                    .collect(Collectors.toSet());
            }
            return Collections.emptySet();
        } catch (IOException e) {
            log.error("load package error", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * @param classPath   类的路径
     * @param basePath    包目录的路径
     * @param basePackage 包名
     * @Description: 从Path获取Class
     * @return: 类
     */

    private static Class<?> getClassByPath(Path classPath, Path basePath, String basePackage) {
        String packageName = classPath.toString().replace(basePath.toString(), "");
        String className = (basePackage + packageName)
            .replace("/", ".")
            .replace("\\", ".")
            .replace(".class", "");

        return loadClass(className);
    }


    /**
     * @param jarEntry jar文件
     * @Description: 从jar包获取Class
     * @return: 类
     */

    private static Class<?> getClassByJar(JarEntry jarEntry) {
        String jarEntryName = jarEntry.getName();
        //获取类名
        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
        return loadClass(className);
    }


}
