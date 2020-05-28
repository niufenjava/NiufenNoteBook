package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.text.StringSpliter;

import java.util.List;

/**
 * Class 类工具类
 *
 * @author niufen
 */
public class ClassUtils {

    // ------------ 获取 Class 相关 ------------

    /**
     * 安全的获取对象类型
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return 对象类型Class，如果为 null,返回 null
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(T obj) {
        return ((null == obj) ? null : (Class<T>) obj.getClass());
    }

    /**
     * 获取对象数组的类数组
     *
     * @param objects 对象数组，如果数组中存在{@code null} 元素，则此元素被认为是 Object 类型
     * @return 类数组
     */
    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        Object obj;
        for (int i = 0; i < objects.length; i++) {
            obj = objects[i];
            classes[i] = (null == obj) ? Object.class : obj.getClass();
        }
        return classes;
    }

    /**
     * 获得定义当前类的类（外围类）
     * 举例：
     * public class Test{
     * class InnerTest{
     * <p>
     * }
     * }
     * Test.class == getEnclosingClass(InnerTest.class);
     *
     * @param clazz 类对象
     * @return 如果此类是内部类，在类内部，则返回定义它的类
     */
    public static Class<?> getEnclosingClass(Class<?> clazz) {
        return null == clazz ? null : clazz.getEnclosingClass();
    }


    /**
     * 是否为顶层类，即定义在包中的类，而非定义在类中的内部类
     *
     * @param clazz 类对象
     * @return 是否为顶层类
     */
    public static boolean isTopLevelClass(Class<?> clazz) {
        if (null == clazz) {
            return Boolean.FALSE;
        }
        // 如果不存在顶层类，说明当前类就是顶层类，而非内部类
        return null == getEnclosingClass(clazz);
    }

    // ------------ 获取 Class Name 相关 ------------

    /**
     * 获取对象的全类名，包括包名
     *
     * @param obj 获取类名对象
     * @return 简单类名
     */
    public static String getClassName(Object obj) {
        return getClassName(obj, Boolean.FALSE);
    }

    /**
     * 获取对象的简单类名
     *
     * @param obj 获取类名对象
     * @return 简单类名
     */
    public static String getSimpleClassName(Object obj) {
        return getClassName(obj, Boolean.TRUE);
    }

    /**
     * 获取类名
     *
     * @param obj      获取类名的对象
     * @param isSimple 是否简单类名，如果为true，返回不带包名的类名
     * @return 当前对象对应的类名
     */
    public static String getClassName(Object obj, boolean isSimple) {
        if (null == obj) {
            return null;
        }
        final Class<?> clazz = obj.getClass();
        return getClassName(clazz, isSimple);
    }

    /**
     * 获取类名
     *
     * @param clazz    类对象
     * @param isSimple 是否简单类名，如果为true，返回不带包名的类名
     * @return 当前对象对应的类名
     */
    public static String getClassName(Class<?> clazz, boolean isSimple) {
        if (null == clazz) {
            return null;
        }
        return isSimple ? clazz.getSimpleName() : clazz.getName();
    }

    /**
     * 获取完整类名的短格式<br>
     * 如：io.niufen.util.ClassUtils
     * -> i.n.u.ClassUtils
     *
     * @param obj 获取类名的对象
     * @return 短格式的类名（包含包名）
     */
    public static String getShortClassName(Object obj) {
        return getShortClassName(getClassName(obj));
    }


    /**
     * 获取完整类名的短格式<br>
     * 如：io.niufen.util.ClassUtils
     * -> i.n.u.ClassUtils
     *
     * @param className 类名
     * @return 短格式的类名（包含包名）
     */
    public static String getShortClassName(String className) {
        if (null == className) {
            return null;
        }
        final List<String> packages = StringSpliter.split(className, CharConstants.DOT);
        if (null == packages || packages.size() < 2) {
            return className;
        }
        final int size = packages.size();
        final StringBuilder result = StringUtils.builder(size);
        result.append(packages.get(0).charAt(0));
        for (int i = 1; i < size - 1; i++) {
            result.append(CharConstants.DOT).append(packages.get(i).charAt(0));
        }
        result.append(CharConstants.DOT).append(packages.get(size - 1));
        return result.toString();
    }


    /**
     * 指定类是否与给定类的类名相同
     *
     * @param clazz      类
     * @param className  类名，可以是全类名，也可以是简单类名
     * @param ignoreCase 是否忽略大小写
     * @return true-指定类与给定类的类名相同; false- 指定类与给定类的类名不相同
     */
    public static boolean equals(Class<?> clazz, String className, boolean ignoreCase) {
        if (null == clazz || StringUtils.isBlank(className)) {
            return Boolean.FALSE;
        }
        if (ignoreCase) {
            return className.equalsIgnoreCase(clazz.getName()) || className.equalsIgnoreCase(clazz.getSimpleName());
        }
        return className.equals(clazz.getName()) || className.equals(clazz.getSimpleName());
    }


    /**
     * 获取ClassLoader
     *
     * @param clazz clazz
     * @return class loader
     */
    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // 无法访问线程上下文ClassLoader-退回到系统类加载器...
        }
        if (cl == null) {
            // 没有线程上下文类加载器->使用此类的类加载器。
            cl = clazz.getClassLoader();
            if (cl == null) {
                // getClassLoader（）返回null表示是使用引导ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // ignore
                }
            }
        }

        return cl;
    }

    /**
     * 返回要使用的默认ClassLoader：通常是线程上下文ClassLoader（如果有）；
     * 否则，返回默认值。即加载ClassUtils类的ClassLoader。
     * 如果打算在绝对需要非空ClassLoader引用的情况下使用线程上下文ClassLoader，
     * 请调用此方法：例如，用于类路径资源加载（但不一定用于Class.forName，该类接受null的ClassLoader引用为好）。
     *
     * @return class loader
     */
    public static ClassLoader getDefaultClassLoader() {
        return getClassLoader(ClassUtils.class);
    }
}
