package io.niufen.common.convert;

import java.io.Serializable;

/**
 * 抽象转换器，提供通用的转换逻辑，同时通过 convertInternal 实现对应类型的专属逻辑<br>
 * 转换器不会抛出转换异常，转换失败时会返回 {@code null}
 *
 * @author haijun.zhang
 * @date 2020/5/27
 * @time 16:33
 */
public class AbstractConverter<T> implements IConverter<T>, Serializable {

    private static final long serialVersionUID = -5870313247072067947L;

    @Override
    public T convert(Object value, T defaultValue) throws IllegalArgumentException {
        return null;
    }
}
