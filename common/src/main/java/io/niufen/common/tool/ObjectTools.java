package io.niufen.common.tool;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.AnnotatedType;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @Description Object 对象工具类
 * @Author haijun.zhang@luckincoffee.com
 * @Date 2019-06-02 22:55:33
 **/
public class ObjectTools {

    public static final Boolean TRUE = Boolean.TRUE;

    public static final Boolean FALSE = Boolean.FALSE;

    public static Boolean equalByToString(Object o1,Object o2){
        if(isNull(o1) || isNull(o2)){
            return FALSE;
        }
        return equals(o1.toString(),o2.toString());
    }

    /**
     * 判断 集合是否为空
     * @param c 集合
     * @return TRUE-为空；FALSE-不为空
     */
    public static Boolean isEmpty(Collection c){
        if(isNull(c) || c.isEmpty()){
            return TRUE;
        }
        return FALSE;
    }

    /**
     * 判断 集合是否为空
     * @param c 集合
     * @return TRUE-为空；FALSE-不为空
     */
    public static Boolean isNotEmpty(Collection c){
        return !isEmpty(c);
    }

    /**
     * 判断 map是否为空
     * @param map map
     * @return TRUE-为空；FALSE-不为空
     */
    public static Boolean isEmpty(Map map){
        if(isNull(map) || map.isEmpty()){
            return TRUE;
        }
        return FALSE;
    }

    /**
     * 判断 map是否为空
     * @param map map
     * @return TRUE-不为空；FALSE-为空
     */
    public static Boolean isNotEmpty(Map map){
        return isEmpty(map);
    }

    /**
     * 判断 注解类型数组是否为空
     * @param annotatedTypes 注解类型数组
     * @return TRUE-为空；FALSE-不为空
     */
    public static Boolean isEmpty(AnnotatedType[] annotatedTypes){
        if(isNull(annotatedTypes) || annotatedTypes.length == 0){
            return TRUE;
        }
        return FALSE;
    }

    /**
     * 判断 注解类型数组是否为空
     * @param annotatedTypes 注解类型数组
     * @return TRUE-不为空；FALSE-为空
     */
    public static Boolean isNotEmpty(AnnotatedType[] annotatedTypes){
        return isEmpty(annotatedTypes);
    }

    /**
     * 判断字符串是否为数字，如果不是返回false
     * @param str 判断
     * @return TRUE-数字字符串；FALSE-非数字字符串
     */
    public static Boolean isNumber(String str){
        if(ObjectTools.isBlank(str)){
            return Boolean.FALSE;
        }
        if(StringUtils.isNumeric(str)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 调用Object对象的 toString 方法，返回字符串
     * @param o 对象
     * @return 对象toString 字符串
     */
    public static String toStr(Object o){
        String oStr = null;
        if(ObjectTools.isNotNull(o)){
            oStr = o.toString();
        }
        return oStr;
    }

    /**
     * 判断 Long 类型参数是否为空或0，
     * @param a 参数
     * @return 如果为空或0返回 false，如果不为空或0返回true
     */
    public static Boolean isNotNullAndZero(Long a){
        return !isNullOrZero(a);
    }

    /**
     * 判断 Integer 类型参数是否为空或0，
     * @param a 参数
     * @return 如果为空或0返回 false，如果不为空或0返回true
     */
    public static Boolean isNotNullAndZero(Integer a){
        return !isNullOrZero(a);
    }

    /**
     * 判断 Long 类型参数是否为空或0，
     * @param a 参数
     * @return 如果为空或0返回 true，如果不为空或0返回false
     */
    public static Boolean isNullOrZero(Long a){
        if(isNull(a) || a == 0){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    /**
     * 判断 Integer 类型参数是否为空或0，
     * @param a 参数
     * @return 如果为空或0返回 true，如果不为空或0返回false
     */
    public static Boolean isNullOrZero(Integer a){
        if(isNull(a) || a == 0){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    /**
     * 判断对象是否为空
     * @param o 对象
     * @return TRUE-NULL；TRUE-NOT NULL
     */
    public static Boolean isNull(Object o){
        return o == null;
    }

    /**
     * 判断对象是否为空
     * @param o 对象
     * @return TRUE-NULL；TRUE-NOT NULL
     */
    public static Boolean isBlank(String o){
        return StringUtils.isBlank(o);
    }

    /**
     * 判断对象是否为空
     * @param o 对象
     * @return TRUE-NULL；TRUE-NOT NULL
     */
    public static Boolean isNotBlank(String o){
        return StringUtils.isNotBlank(o);
    }

    /**
     * 判断对象是否为空
     * @param o 对象
     * @return TRUE-NOT NULL；TRUE-NULL
     */
    public static Boolean isNotNull(Object o){
        return !ObjectTools.isNull(o);
    }

    /**
     * 判断两个对象是否相等，主要用户 Byte、Integer 包装类型的 equals 比较
     * 如果其中有一个值为 null 返回false
     * @param a 前一个对象
     * @param b 后一个对象
     * @return 比对结果，相等 true，不相等 false
     */
    public static Boolean equals(Object a, Object b){
        return Objects.equals(a,b);
    }

    /**
     * 判断两个对象是否不相等，主要用户 Byte、Integer 包装类型的 equals 比较
     * 如果其中有一个值为 null 返回false
     * @param a 前一个对象
     * @param b 后一个对象
     * @return 比对结果,不相等 true，相等 false
     */
    public static Boolean notEquals(Object a, Object b){
        return !ObjectTools.equals(a,b);
    }


    /**
     * 将Object转为String
     *
     * @param object 对象
     * @return String
     */
    public static String toString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }



    /**
     * 判断目标对象target是否equals 参数args中的一个，只要其中有一个相等，返回true，都不相等返回false
     * @param target 目标对象
     * @param args 多个参数
     * @return true：target in (a,b) false: target not in (a,b)
     */
    public static Boolean in(Object target, Object... args){
        for (Object arg : args) {
            if(Objects.equals(target,arg)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断目标对象target是否不等于a或b，只有都不相等相等，返回true，其中有一个相等返回false
     * @param target 目标对象
     * @param args 多参数
     * @return true：target not in (a,b) false: target in (a,b)
     */
    public static Boolean notIn(Object target, Object... args){
        return !in(target,args);
    }

    /**
     * 将 Object 类型 转为 Byte
     *
     * @param object
     * @return Byte
     */
    public static Byte toByte(Object object) {
        if (object == null) {
            return null;
        }
        String str = toString(object);
        if (!StringUtils.isNumeric(str)) {
            return null;
        }
        return Byte.valueOf(str);
    }


    /**
     * 将 Object 类型 转为 Integer
     *
     * @param object
     * @return Byte
     */
    public static Integer toInteger(Object object) {
        if (object == null) {
            return null;
        }
        String str = toString(object);
        if (!StringUtils.isNumeric(str)) {
            return null;
        }
        return Integer.valueOf(str);
    }

    /**
     * 判断传入字符串是否全部为汉字
     * @param str 字符串
     * @return TRUE-汉字；FALSE-包含非汉字
     */
    public static boolean isChineseStr(String str)
    {
        if(ObjectTools.isNull(str)){
            return Boolean.FALSE;
        }
        int n = 0;
        for(int i = 0; i < str.length(); i++) {
            n = str.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

}
