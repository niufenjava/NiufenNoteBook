package io.niufen.common.util;

/**
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 17:43
 */
public class EnumUtils {

    /**
     * 字符串转枚举，调用{@link Enum#valueOf(Class, String)}
     *
     * @param <E>       枚举类型泛型
     * @param enumClass 枚举类
     * @param index     枚举索引
     * @return 枚举值，null表示无此对应枚举
     * @since 5.1.6
     */
    public static <E extends Enum<E>> E getEnumAt(Class<E> enumClass, int index) {
        final E[] enumConstants = enumClass.getEnumConstants();
        return index < enumConstants.length ? enumConstants[index] : null;
    }
}
