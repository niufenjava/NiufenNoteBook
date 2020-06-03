package io.niufen.common.core.convert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基本变量类型的枚举<br>
 * 基本类型枚举包括原始类型和包装器类型
 *
 * @author haijun.zhang
 * @date 2020/5/27
 * @time 14:39
 */
public enum BasicType {

    /**
     * 定义枚举类型
     */
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    CHAR,
    BOOLEAN;

    /**
     * 本类使用的MAP的初始容量大小
     */
    public static final int MAP_INITIAL_CAPACITY = 8;

    /**
     * 包装类型为Key
     * 原始类型为Value
     * 例如： Integer.class =》 int.class.
     */
    public static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP = new ConcurrentHashMap<>(MAP_INITIAL_CAPACITY);

    /**
     * 原始类型为Key
     * 包装器类型为Value
     * 例如：int.class =》Integer.class
     */
    public static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new ConcurrentHashMap<>(MAP_INITIAL_CAPACITY);

    /*
     * 静态代码块，在项目启动之前，进行MAP设置
     */
    static {
        WRAPPER_PRIMITIVE_MAP.put(Byte.class, byte.class);
        WRAPPER_PRIMITIVE_MAP.put(Short.class, short.class);
        WRAPPER_PRIMITIVE_MAP.put(Integer.class, int.class);
        WRAPPER_PRIMITIVE_MAP.put(Long.class, long.class);
        WRAPPER_PRIMITIVE_MAP.put(Float.class, float.class);
        WRAPPER_PRIMITIVE_MAP.put(Double.class, double.class);
        WRAPPER_PRIMITIVE_MAP.put(Character.class, char.class);
        WRAPPER_PRIMITIVE_MAP.put(Boolean.class, boolean.class);

        for (Map.Entry<Class<?>, Class<?>> entry : WRAPPER_PRIMITIVE_MAP.entrySet()) {
            PRIMITIVE_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 原始类型转换为包装器类型，非原始类型返回原类
     *
     * @param clazz 原始类型
     * @return 包装器类型
     */
    public static Class<?> wrap(Class<?> clazz) {
        if (null == clazz || !clazz.isPrimitive()) {
            return clazz;
        }
        Class<?> result = PRIMITIVE_WRAPPER_MAP.get(clazz);
        return (null == result) ? clazz : result;
    }


    /**
     * 包装类转为原始类，非包装类返回原类
     *
     * @param clazz 包装器类型
     * @return 原始类型
     */
    public static Class<?> unWrap(Class<?> clazz) {
        if (null == clazz || clazz.isPrimitive()) {
            return clazz;
        }
        Class<?> result = WRAPPER_PRIMITIVE_MAP.get(clazz);
        return (null == result) ? clazz : result;
    }
}
