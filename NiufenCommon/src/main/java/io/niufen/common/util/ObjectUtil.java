package io.niufen.common.util;


import io.niufen.common.comparator.CompareUtil;
import io.niufen.common.exception.UtilException;
import io.niufen.common.io.FastByteArrayOutputStream;
import io.niufen.common.io.IoUtil;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @author meilin.huang
 * @version 1.0
 * @date 2019-04-18 09:47
 */
public class ObjectUtil {

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * <ol>
     * <li>obj1 == null &amp;&amp; obj2 == null</li>
     * <li>obj1.equals(obj2)</li>
     * </ol>
     * 1. obj1 == null &amp;&amp; obj2 == null 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     * @see Objects#equals(Object, Object)
     */
    public static boolean equal(Object obj1, Object obj2) {
        // return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
        return Objects.equals(obj1, obj2);
    }
    /**
     * 判断指定对象是否为空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param obj 被判断的对象
     * @return 是否为空，如果类型不支持，返回false
     * @since 4.5.7
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }

        if (obj instanceof CharSequence) {
            return StrUtil.isEmpty((CharSequence) obj);
        } else if (obj instanceof Map) {
            return MapUtil.isEmpty((Map) obj);
        } else if (obj instanceof Iterable) {
            return IterUtil.isEmpty((Iterable) obj);
        } else if (obj instanceof Iterator) {
            return IterUtil.isEmpty((Iterator) obj);
        } else if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.isEmpty(obj);
        }

        return false;
    }

    /**
     * 检查对象是否为null<br>
     * 判断标准为：
     *
     * <pre>
     * 1. == null
     * 2. equals(null)
     * </pre>
     *
     * @param obj 对象
     * @return 是否为null
     */
    public static boolean isNull(Object obj) {
        //noinspection ConstantConditions
        return null == obj || obj.equals(null);
    }
    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param <T>          对象类型
     * @param object       被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     * @since 3.0.7
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return (null != object) ? object : defaultValue;
    }

    /**
     * 所有数组元素向上转型
     *
     * @param objs  转换前对象数组
     * @param clazz 转换后数组对象类型
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] cast(Object[] objs, Class<T> clazz) {
        int length = objs.length;
        if (length == 0) {
            return (T[]) new Object[0];
        }
        T[] newArr = (T[]) Array.newInstance(clazz, objs.length);
        for (int i = 0; i < length; i++) {
            newArr[i] = clazz.cast(objs[i]);
        }

        return newArr;
    }

    //---------------------------------------------------------------------
    // 对象类型判断
    //---------------------------------------------------------------------

    public static boolean isCollection(Object obj) {
        return obj instanceof Collection;
    }

    public static boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    public static boolean isNumber(Object obj) {
        return obj instanceof Number;
    }

    public static boolean isBoolean(Object obj) {
        return obj instanceof Boolean;
    }

    public static boolean isEnum(Object obj) {
        return obj instanceof Enum;
    }

    public static boolean isDate(Object obj) {
        return obj instanceof Date || obj instanceof TemporalAccessor;
    }

    public static boolean isCharSequence(Object obj) {
        return obj instanceof CharSequence;
    }

    /**
     * 判断对象是否为八大基本类型包装类除外即(boolean, byte, char, short, int, long, float, and double)<br/>
     *
     * @param obj
     * @return
     */
    public static boolean isPrimitive(Object obj) {
        return obj != null && obj.getClass().isPrimitive();
    }

    /**
     * 判断对象是否为包装类或者非包装类的基本类型
     *
     * @param obj
     * @return
     */
    public static boolean isWrapperOrPrimitive(Object obj) {
        return isPrimitive(obj) || isNumber(obj) || isCharSequence(obj) || isBoolean(obj);
    }

    /**
     * 判断一个对象是否为数组
     *
     * @param obj
     * @return
     */
    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    /**
     * 判断一个对象是否为基本类型数组即(int[], long[], boolean[], double[]....)
     *
     * @param obj
     * @return
     */
    public static boolean isPrimitiveArray(Object obj) {
        return isArray(obj) && obj.getClass().getComponentType().isPrimitive();
    }

    /**
     * {@code null}安全的对象比较
     *
     * @param <T>         被比较对象类型
     * @param c1          对象1，可以为{@code null}
     * @param c2          对象2，可以为{@code null}
     * @param nullGreater 当被比较对象为null时是否排在前面
     * @return 比较结果，如果c1 &lt; c2，返回数小于0，c1==c2返回0，c1 &gt; c2 大于0
     * @see java.util.Comparator#compare(Object, Object)
     * @since 3.0.7
     */
    public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
        return CompareUtil.compare(c1, c2, nullGreater);
    }


    /**
     * {@code null}安全的对象比较，{@code null}对象排在末尾
     *
     * @param <T> 被比较对象类型
     * @param c1  对象1，可以为{@code null}
     * @param c2  对象2，可以为{@code null}
     * @return 比较结果，如果c1 &lt; c2，返回数小于0，c1==c2返回0，c1 &gt; c2 大于0
     * @see java.util.Comparator#compare(Object, Object)
     * @since 3.0.7
     */
    public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
        return CompareUtil.compare(c1, c2);
    }

    /**
     * 克隆对象<br>
     * 如果对象实现Cloneable接口，调用其clone方法<br>
     * 如果实现Serializable接口，执行深度克隆<br>
     * 否则返回<code>null</code>
     *
     * @param <T> 对象类型
     * @param obj 被克隆对象
     * @return 克隆后的对象
     */
    public static <T> T clone(T obj) {
        T result = ArrayUtil.clone(obj);
        if (null == result) {
            if (obj instanceof Cloneable) {
                result = ReflectUtil.invoke(obj, "clone");
            } else {
                result = cloneByStream(obj);
            }
        }
        return result;
    }
    /**
     * 序列化后拷贝流的方式克隆<br>
     * 对象必须实现Serializable接口
     *
     * @param <T> 对象类型
     * @param obj 被克隆对象
     * @return 克隆后的对象
     * @throws UtilException IO异常和ClassNotFoundException封装
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneByStream(T obj) {
        if (false == (obj instanceof Serializable)) {
            return null;
        }
        final FastByteArrayOutputStream byteOut = new FastByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);
            out.flush();
            final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
            return (T) in.readObject();
        } catch (Exception e) {
            throw new UtilException(e);
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 序列化<br>
     * 对象必须实现Serializable接口
     *
     * @param <T> 对象类型
     * @param obj 要被序列化的对象
     * @return 序列化后的字节码
     */
    public static <T> byte[] serialize(T obj) {
        if (false == (obj instanceof Serializable)) {
            return null;
        }
        final FastByteArrayOutputStream byteOut = new FastByteArrayOutputStream();
        IoUtil.writeObjects(byteOut, false, (Serializable) obj);
        return byteOut.toByteArray();
    }

    /**
     * 反序列化<br>
     * 对象必须实现Serializable接口
     *
     * <p>
     * 注意！！！ 此方法不会检查反序列化安全，可能存在反序列化漏洞风险！！！
     * </p>
     *
     * @param <T>   对象类型
     * @param bytes 反序列化的字节码
     * @return 反序列化后的对象
     */
    public static <T> T deserialize(byte[] bytes) {
        return IoUtil.readObj(new ByteArrayInputStream(bytes));
    }

    /**
     * 反序列化<br>
     * 对象必须实现Serializable接口
     *
     * @param <T>   对象类型
     * @param bytes 反序列化的字节码
     * @return 反序列化后的对象
     * @see #deserialize(byte[])
     * @deprecated 请使用 {@link #deserialize(byte[])}
     */
    @Deprecated
    public static <T> T unserialize(byte[] bytes) {
        return deserialize(bytes);
    }

}
