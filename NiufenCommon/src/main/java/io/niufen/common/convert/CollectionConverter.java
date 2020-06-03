package io.niufen.common.convert;

import io.niufen.common.util.CollUtil;
import io.niufen.common.util.TypeUtil;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 各种集合类转换器
 * 没有继承抽象转换器
 *
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 16:03
 */
public class CollectionConverter implements IConverter<Collection<?>> {

    private static final long serialVersionUID = 352333625348683753L;

    /**
     * 集合类型
     */
    private final Type collectionType;

    /**
     * 集合元素类型
     */
    private final Type elementType;

    /**
     * 构造
     * 默认集合类型使用 {@link Collection}
     */
    public CollectionConverter() {
        this(Collection.class);
    }

    /**
     * 构造
     *
     * @param collectionType 集合类型
     */
    public CollectionConverter(Type collectionType) {
        this(collectionType, TypeUtil.getTypeArgument(collectionType));
    }

    /**
     * 构造
     *
     * @param collectionType 集合类型
     */
    public CollectionConverter(Class<?> collectionType) {
        this(collectionType, TypeUtil.getTypeArgument(collectionType));
    }

    /**
     * 构造
     *
     * @param collectionType 集合类型
     * @param elementType    集合元素类型
     */
    public CollectionConverter(Type collectionType, Type elementType) {
        this.collectionType = collectionType;
        this.elementType = elementType;
    }

    @Override
    public Collection<?> convert(Object value, Collection<?> defaultValue) throws IllegalArgumentException {
        Collection<?> result;
        try {
            result = convertInternal(value);
        } catch (RuntimeException e) {
            return defaultValue;
        }
        return ((null == result) ? defaultValue : result);
    }

    /**
     * 内部转换
     *
     * @param value 值
     * @return 转换后的结合对象
     */
    protected Collection<?> convertInternal(Object value) {
        final Collection<Object> collection = CollUtil.newCollection(TypeUtil.getClass(this.collectionType));
        return CollUtil.addAll(collection, value, this.elementType);
    }
}
