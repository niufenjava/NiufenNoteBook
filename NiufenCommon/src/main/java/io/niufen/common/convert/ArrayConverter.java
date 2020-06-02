package io.niufen.common.convert;

import io.niufen.common.util.ArrayUtil;

/**
 * 数组转换器，包括原始类型数组
 *
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 09:06
 */
public class ArrayConverter extends AbstractConverter<Object> {

    private static final long serialVersionUID = -1384809443170566736L;

    /**
     * 目标数组类型
     */
    private final Class<?> targetType;

    /**
     * 目标数组中的元素类型
     */
    private final Class<?> targetComponentType;

    /**
     * 构造
     *
     * @param targetType 目标数组类型
     */
    public ArrayConverter(Class<?> targetType) {
        // 1. 如果目标类型为空，创建一个 Object类型数组
        if (null == targetType) {
            targetType = Object[].class;
        }
        // 2. 如果目标类型已经是一个数组
        // 2.1 设置目标数组为  为传入类型targetType
        // 2.2 设置目标数组元素为 传入数组类型中的元素
        if (targetType.isArray()) {
            this.targetType = targetType;
            // Class.getComponentType() 获取这个数组中元素的Class对象
            // @see ClassTest.getComponentType()
            this.targetComponentType = ArrayUtil.getComponentType(targetType);
        } else {
            // 用户传入类为非数组时，按照数组元素类型对象
            this.targetComponentType = targetType;
            // 可以创建一个 componentType 类型的数组，长度为0
            this.targetType = ArrayUtil.getArrayType(targetType);
        }
    }

    @Override
    protected Object convertInternal(Object value) {
        return null;
    }

    /**
     * 数组对目标数组转换
     *
     * @param array 被转换的数组对象
     * @return 转换后的数组
     */
    private Object convertArrayToArray(Object array) {
        final Class<?> valueComponentType = ArrayUtil.getComponentType(array);
        // 如果被转换的数组元素类型 与 目标数组元素类型相同，那还转个屁啊
        if (valueComponentType == targetComponentType) {
            return array;
        }
        final int valueArrayLen = ArrayUtil.length(array);
        // 根据目标类型，创建一个传入数组长度的新数组
        final Object targetArray = ArrayUtil.newArray(targetComponentType, valueArrayLen);

        // TODO
        return null;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Class getTargetType() {
        return this.targetType;
    }
}
