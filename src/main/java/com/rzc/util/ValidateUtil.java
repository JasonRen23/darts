package com.rzc.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author : JasonRen
 * @date : 2018/07/18
 * @email : zhicheng_ren@163.com
 */
public class ValidateUtil {
    /**
     * @param obj Object
     * @return boolean
     * @Description: 判断Object是否为null
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * @param obj Object
     * @return boolean
     * @Description: 判断String是否为null或“”
     */
    public static boolean isEmpty(String obj) {

        return (obj == null || obj.equals(""));
    }

    /**
     * @param obj Object
     * @Description: 判断Array是否为null或者size为0
     * @return: boolean
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * @param obj
     * @Description: 判断Collection是否为null或者size为0
     * @return: boolean
     */

    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * @param obj Object
     * @Description: 判断Map是否为null或者size为0
     * @return: boolean
     */

    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * @param obj Object
     * @return boolean
     * @Description: 判断Object是否不为null
     */

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * @param obj Object
     * @return boolean
     * @Description: 判断String是否不为null或“”
     */

    public static boolean isNotEmpty(String obj) {
        return !isEmpty(obj);
    }

    /**
     * @param obj Object
     * @Description: 判断Array是否不为null或者size为0
     * @return: boolean
     */

    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * @param obj
     * @Description: 判断Collection是否不为null或者size为0
     * @return: boolean
     */

    public static boolean isNotEmpty(Collection<?> obj) {
        return !isEmpty(obj);
    }

    /**
     * @param obj Object
     * @Description: 判断Map是否不为null或者size为0
     * @return: boolean
     */

    public static boolean isNotEmpty(Map<?, ?> obj) {
        return !isEmpty(obj);
    }


}
