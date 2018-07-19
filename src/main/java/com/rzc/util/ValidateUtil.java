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
     * 判断Object是否为null
     * @param obj Object
     * @return boolean
     */
    public static boolean isEmpty(Object obj){
        return obj == null;
    }
    /**
     * 判断String是否为null或“”
     * @param obj Object
     * @return boolean
     */
    public static boolean isEmpty(String obj){

        return (obj == null || obj.equals(""));
    }

    public static boolean isEmpty(Object[] obj){
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Map<?,?> obj){
        return obj == null || obj.isEmpty();
    }

    public static boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(String obj){
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Object[] obj){
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Collection<?> obj){
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Map<?,?> obj){
        return !isEmpty(obj);
    }


}
