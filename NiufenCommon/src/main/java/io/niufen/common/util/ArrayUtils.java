package io.niufen.common.util;

import io.niufen.common.constant.IntConstants;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * 数组相关工具类
 *
 * @author niufen
 */
public class ArrayUtils {

    /**
     * 数组中元素未找到的下标，值为-1
     */
    public static final int INDEX_NOT_FOUND = IntConstants.ONE_MINUS;

    /**
     * 新建一个空数组
     *
     * @param componentType 数组元素类型
     * @param newSize       数组大小
     * @param <T>           数组元素类型
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 是否为空
     *
     * @param array 数组类型
     * @param <T>   泛型
     * @return true:为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否为空
     *
     * @param array 数组类型
     * @param <T>   泛型
     * @return true:为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 是否包含{@code null}元素
     *
     * @param <T> 数组元素类型
     * @param array 被检查的数组
     * @return 是否包含{@code null}元素
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean hasNull(T... array) {
        if (isNotEmpty(array)) {
            for (T element : array) {
                if (null == element) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 是否为空
     *
     * @param array 数组类型
     * @return true:为空
     */
    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否为空
     *
     * @param array 数组类型
     * @return true:为空
     */
    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组中是否存在指定元素
     *
     * @param arrays 数组
     * @param val    校验的元素
     * @param <T>    数组原始类型
     * @return 是否存在
     */
    public static <T> boolean contains(T[] arrays, T val) {
        if (arrays == null) {
            return false;
        }
        for (T t : arrays) {
            if (Objects.equals(t, val)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return true-包含;false-不包含
     */
    public static boolean contains(char[] array, char value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     */
    public static int indexOf(char[] array, char value) {
        if (null != array) {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 获取数组对象的元素类型
     *
     * @param array 获取数组对象的元素类型
     * @return 元素类型
     */
    public static Class<?> getComponentType(Object array) {
        return null == array ? null : array.getClass().getComponentType();
    }

    /**
     * 根据数组元素类型，获取数组的类型<br/>
     * 方法是通过创建一个空数组从而获取类型
     *
     * @param componentType 数组元素类型
     * @return 数组类型
     */
    public static Class<?> getArrayType(Class<?> componentType) {
        // Array.newInstance(componentType, 0) 可以创建一个 componentType 类型的数组，长度为0
        return Array.newInstance(componentType, 0).getClass();
    }

    /**
     * 数组对象转String
     * 1、如果是基本类型数组，那么调用 Arrays.toString(xxx[] object) 方法
     * Arrays.toString(xxx[] object):
     * Returns a string representation of the contents of the specified array.
     * 返回指定数组内容的字符串表示形式
     * 2、如果是其他类型的数组对象，那么调用 Arrays.deepToString((Object[]) obj) 方法
     * Returns a string representation of the "deep contents" of the specified array.
     * 返回指定数组的"深度内容"的字符串表示形式
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        }
        if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        }
        if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        }
        if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        }
        if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        }
        if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        }
        if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        }
        if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        }
        // 对象数组
        if (ArrayUtils.isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {
                //ignore
            }
        }
        return obj.toString();
    }

    /**
     * 判断对象是否为数组对象，如果传入null，返回false
     *
     * @param object 被检查的对象
     * @return true-数组对象；false-非数组对象\null
     */
    public static boolean isArray(Object object) {
        if (null == object) {
            return Boolean.FALSE;
        }
        return object.getClass().isArray();
    }

    /**
     * 获取数组长度<br>
     * 如果参数为{@code null}，返回0
     *
     * <pre>
     * ArrayUtil.length(null)            = 0
     * ArrayUtil.length([])              = 0
     * ArrayUtil.length([null])          = 1
     * ArrayUtil.length([true, false])   = 2
     * ArrayUtil.length([1, 2, 3])       = 3
     * ArrayUtil.length(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array 数组对象
     * @return 数组长度
     * @throws IllegalArgumentException 如果参数不为数组，抛出此异常
     * @see Array#getLength(Object)
     * @since 3.0.8
     */
    public static int length(Object array) {
        if (null == array) {
            return 0;
        }
        return Array.getLength(array);
    }
}
