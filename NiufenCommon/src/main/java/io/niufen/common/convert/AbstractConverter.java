package io.niufen.common.convert;

import io.niufen.common.util.ArrayUtil;
import io.niufen.common.util.CharUtil;
import io.niufen.common.util.ClassUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 抽象转换器，提供通用的转换逻辑，同时通过 convertInternal 实现对应类型的专属逻辑<br>
 * 转换器不会抛出转换异常，转换失败时会返回 {@code null}
 *
 * @author haijun.zhang
 * @date 2020/5/27
 * @time 16:33
 */
public abstract class AbstractConverter<T> implements IConverter<T>, Serializable {

    private static final long serialVersionUID = -5870313247072067947L;

    @Override
    @SuppressWarnings(("unchecked"))
    public T convert(Object value, T defaultValue) throws IllegalArgumentException {
        Class<T> targetType = getTargetType();
        if (null == targetType && null == defaultValue) {
            throw new IllegalArgumentException("[type] and [defaultValue] are both null for Converter [{}], we can not know what type to convert !");
        }
        if (null == targetType) {
            // 目标类型不确定时使用默认值的类型
            targetType = (Class<T>) defaultValue.getClass();
        }
        if (null == value) {
            return defaultValue;
        }

        // targetType.isInstance()  如果 targetType 是一个基本类型，返回 false
        if (null == defaultValue || targetType.isInstance(defaultValue)) {
            if (targetType.isInstance(value) && !Map.class.isAssignableFrom(targetType)) {
                // 除Map外，已经是目标类型，不需要转换（Map类型涉及参数类型，需要单独转换）
                return targetType.cast(value);
            }
            T result = convertInternal(value);
            return ((null == result) ? defaultValue : result);
        } else {
            // StrUtil.format
            throw new IllegalArgumentException("Default value [{}] is not the instance of [{}]");
        }
    }

    /**
     * Object 对象值转为 String，用于内部转换中需要使用String中转的情况<br/>
     * 转换规则为：
     * 1、字符串类型将被强转
     * 2、数组将被转换为逗号分隔符字符串
     * 3、其他类型将调用默认toString()方法
     *
     * @param value 需要被转换的值
     * @return 转换后的字符串
     */
    protected String convertToStr(Object value) {
        if (null == value) {
            return null;
        }
        if (value instanceof CharSequence) {
            return value.toString();
        }
        if (ArrayUtil.isArray(value)) {
            return ArrayUtil.toString(value);
        }
        if (CharUtil.isChar(value)) {
            return CharUtil.toString((char) value);
        }
        return value.toString();
    }


    /**
     * 不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @since 4.5.7
     */
    public T convertQuietly(Object value, T defaultValue) {
        try {
            return convert(value, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 内部转换器，被{@link AbstractConverter#convert(Object, Object)} 调用，实现基本转换逻辑<br/>
     * 内部转换器转换后如果转换失败可以作如下操作，处理结果都为返回默认值
     * 1、返回 {@code null}
     * 2、抛出一个{@link RuntimeException} 异常
     *
     * @param value 值
     * @return 转换后的类型
     */
    protected abstract T convertInternal(Object value);

    /**
     * 获得此类实现类（继承词抽象类）的泛型类型
     *
     * @return 此类的泛型类型，可能为{@code null}
     */
    @SuppressWarnings("unchecked")
    public Class<T> getTargetType() {
        return (Class<T>) ClassUtil.getTypeArgument(getClass());
    }
}
