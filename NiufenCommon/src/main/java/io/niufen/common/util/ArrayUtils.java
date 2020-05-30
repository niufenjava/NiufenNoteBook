package io.niufen.common.util;

import io.niufen.common.constant.IntConstants;
import io.niufen.common.exception.UtilException;

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
     * 数组中元素起始的下标，值为 0
     */
    public static final int INDEX_START = IntConstants.ZERO;

    /**
     * 空数组的长度
     */
    public static final int EMPTY_LENGTH = IntConstants.ZERO;


    // -------------------- isEmpty start --------------------

    /**
     * 判断传入的 泛型数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array 数组类型
     * @param <T>   泛型
     * @return true-为空；false-不为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 泛型数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array 数组类型
     * @param <T>   泛型
     * @return true-不为空；false-为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 如果给定的数组为空，返回默认数组
     *
     * @param array        数组
     * @param defaultArray 默认数组
     * @param <T>          数组元素类型
     * @return 非空（empty）的原数组或默认数组
     */
    public static <T> T[] defaultIfEmpty(T[] array, T[] defaultArray) {
        return !isEmpty(array) ? array : defaultArray;
    }


    /**
     * 传入对象类型数组，判断数组是否为空
     * <p>
     * 此方法会匹配单一对象，如果此对象为 null，则返回true
     * 如果此对象为非数组，理解为此对象为数组的第一个元素，返回false
     * 如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
     *
     * @param array 数组
     * @return true-null 或 数组并且长度为0；false-数组长度不为空
     */
    public static boolean isEmpty(Object array) {
        if (null == array) {
            return true;
        }
        if (isArray(array)) {
            return Array.getLength(array) == EMPTY_LENGTH;
        }
        throw new UtilException("Object to provide is not a Array!");
    }

    /**
     * 传入对象类型数组，判断数组是否为非空
     *
     * @param array 数组
     * @return true-非空数组；false-空数组
     */
    public static boolean isNotEmpty(Object array) {
        return !isEmpty(array);
    }


    /**
     * 判断传入的 基本类型字符数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array 字符数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(char[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型字符数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array 字符数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型布尔数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array 布尔数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(boolean[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型布尔数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array 布尔数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型字节数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array 字节数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(byte[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型字节数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array 字节数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型short数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array short数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(short[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型short数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array short数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型int数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array int数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(int[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型int数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array int数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型long数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array long数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(long[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型long数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array long数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型float数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array float数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(float[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型float数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array float数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断传入的 基本类型double数组 是否为空
     * empty 空的定义：
     * 1、传入的数组为 null
     * 或
     * 2、数组长度为 0
     *
     * @param array double数组
     * @return true-为空；false-不为空
     */
    public static boolean isEmpty(double[] array) {
        return null == array || array.length == EMPTY_LENGTH;
    }

    /**
     * 判断传入的 基本类型double数组 是否为非空
     * notEmpty 非空定义：
     * 1、传入的数组不为 null
     * 并且
     * 2、数组长度不为 0
     *
     * @param array double数组
     * @return true-非空；false-空
     */
    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    // -------------------- isEmpty end --------------------


    // -------------------- null element start --------------------

    /**
     * 是否包含{@code null}元素，如果数组本身为空，则返回false
     *
     * @param <T>   数组元素类型
     * @param array 被检查的数组
     * @return true-包含{@code null}元素;false-不包含null元素
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
     * 是否不包含{@code null}元素，如果数组本身为空，则返回true
     *
     * @param <T>   数组元素类型
     * @param array 被检查的数组
     * @return true-不包含{@code null}元素;false-包含null元素
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean notHasNull(T... array) {
        return !hasNull(array);
    }

    /**
     * 返回数组中第一个非空元素
     *
     * @param array 数组
     * @param <T>   数组元素类型
     * @return 非空元素，如果不存在非空元或数组为空，返回null
     */
    public static <T> T firstNotNull(T... array) {
        if (isEmpty(array)) {
            return null;
        }
        for (T t : array) {
            if (null != t) {
                return t;
            }
        }
        return null;
    }

    // -------------------- null element end --------------------


    // -------------------- new Array start --------------------

    /**
     * 根据传入的数组元素类型，初始化大小，新建一个空数组
     *
     * @param componentType 数组元素类型
     * @param newSize       数组大小
     * @param <T>           数组元素类型
     * @return 长度确定的数组，所有元素为空
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 根据传入的初始化大小，新建一个空数组
     *
     * @param newSize 数组大小
     * @return 长度确定的数组，所有元素为空
     */
    public static Object[] newArray(int newSize) {
        return new Object[newSize];
    }

    // -------------------- new Array end --------------------


    // -------------------- Array \ Component Type start --------------------

    /**
     * 获取数组对象的元素类型
     * 如果不是数组，那么返回 null
     *
     * @param array 数组对象
     * @return 数组对象的元素类型
     */
    public static Class<?> getComponentType(Object array) {
        return null == array ? null : array.getClass().getComponentType();
    }


    /**
     * 获取数组对象的元素类型
     * 如果不是数组，那么返回 null
     *
     * @param arrayClass 获取数组对象的元素类型
     * @return 元素类型
     */
    public static Class<?> getComponentType(Class<?> arrayClass) {
        return null == arrayClass ? null : arrayClass.getComponentType();
    }

    /**
     * 根据数组元素类型，获取数组的类型<br/>
     * 方法是通过创建一个空数组从而获取类型.
     * 举例：
     * <code>
     * Class<?> arrayType = ArrayUtils.getArrayType(int.class);
     * assert int[].class == arrayType;
     * </code>
     *
     * @param componentType 数组元素类型
     * @return 数组类型
     */
    public static Class<?> getArrayType(Class<?> componentType) {
        return Array.newInstance(componentType, EMPTY_LENGTH).getClass();
    }

    // -------------------- Array \ Component Type end --------------------


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
