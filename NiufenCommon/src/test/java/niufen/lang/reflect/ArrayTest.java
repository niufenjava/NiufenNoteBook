package niufen.lang.reflect;

import org.junit.Test;

import java.lang.reflect.Array;

/**
 * {@link java.lang.reflect.Array}
 * The Array class provides static methods to dynamically create and access Java arrays.
 * Array 类提供了一些静态方法去动态的创建和访问Java数组。
 * <p>
 * Array permits widening conversions to occur during a get or set operation,
 * but throws an IllegalArgumentException if a narrowing conversion would occur.
 * Array 运行在进行 get 和 set 操作期间进行加宽转换
 * 单如果发生缩小转换，则抛出 IllegalArgumentException
 *
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 09:18
 */
public class ArrayTest {

    /**
     * {@link Array#newInstance(Class, int)}
     * Creates a new array with the specified component type and length.
     * Invoking this method is equivalent to creating an array as follows:
     * 用指定的组件（数组元素）类型和长度创建一个新数组。
     * 调用此方法等效于如下创建数组：
     * <code>
     * int[] x = {length};
     * Array.newInstance(componentType, x);
     * </code>
     * <p>
     * The number of dimensions of the new array must not exceed 255.
     * 新数组的维度不能超过255
     * <p>
     * param1 - Class<?> componentType
     * the Class object representing the component type of the new array
     * 代表新数组的组件类型的 Class对象
     * param2 - int length
     * the length of the new array
     * 新数组的长度
     */
    @Test
    public void newInstance() {
        Object o = Array.newInstance(int.class, 0);
        assert o.getClass().isArray();
        int[] array = (int[]) o;
        assert 0 == array.length;

        o = Array.newInstance(int.class, 5);
        assert o.getClass().isArray();
        array = (int[]) o;
        assert 5 == array.length;

        o = Array.newInstance(int.class, 256);
        assert o.getClass().isArray();
        array = (int[]) o;
        assert 256 == array.length;
    }
}
