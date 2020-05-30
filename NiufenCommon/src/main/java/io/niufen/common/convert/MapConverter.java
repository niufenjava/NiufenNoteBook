package io.niufen.common.convert;

import io.niufen.common.util.MapUtils;
import io.niufen.common.util.StringUtils;
import io.niufen.common.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * Map 转换器
 *
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 17:11
 */
public class MapConverter extends AbstractConverter<Map<?, ?>> {

    private static final long serialVersionUID = 2548004232040420769L;

    /**
     * Map类型
     */
    private final Type mapType;

    /**
     * 键类型
     */
    private final Type keyType;

    /**
     * 值类型
     */
    private final Type valueType;

    public MapConverter(Type mapType) {
        this.mapType = mapType;
        this.keyType = TypeUtils.getTypeArgument(mapType, 0);
        this.valueType = TypeUtils.getTypeArgument(mapType, 1);
    }

    /**
     * 构造
     *
     * @param mapType   Map类型
     * @param keyType   键类型
     * @param valueType 值类型
     */
    public MapConverter(Type mapType, Type keyType, Type valueType) {
        this.mapType = mapType;
        this.keyType = keyType;
        this.valueType = valueType;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Map<?, ?> convertInternal(Object value) {
        Map map = null;
        if (value instanceof Map) {
            final Type[] typeArguments = TypeUtils.getTypeArguments(value.getClass());
            if (null != typeArguments && 2 == typeArguments.length
                    && Objects.equals(this.keyType, typeArguments[0])
                    && Objects.equals(this.valueType, typeArguments[1])
            ) {
                return (Map) value;
            }
            map = MapUtils.newMap(TypeUtils.getClass(this.mapType));
            convertMapToMap((Map) value, map);
        } else if (Boolean.FALSE) {
            // todo Bean to map
        } else {
            throw new UnsupportedOperationException(StringUtils.format("Unsupport toMap value type: {}", value.getClass().getName()));
        }
        return map;
    }

    /**
     * Map 转 Map
     *
     * @param srcMap    源Map
     * @param targetMap 目标Map
     */
    private void convertMapToMap(Map<?, ?> srcMap, Map<Object, Object> targetMap) {
        final ConverterRegistry converter = ConverterRegistry.getInstance();
        Object key;
        Object value;
        for (Map.Entry<?, ?> entry : srcMap.entrySet()) {
            key = TypeUtils.isUnknownType(this.keyType) ? entry.getKey() : converter.convert(this.keyType, entry.getKey());
            value = TypeUtils.isUnknownType(this.valueType) ? entry.getValue() : converter.convert(this.valueType, entry.getValue());
            targetMap.put(key, value);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public Class<Map<?, ?>> getTargetType() {
        return (Class<Map<?, ?>>) TypeUtils.getClass(this.mapType);
    }
}
