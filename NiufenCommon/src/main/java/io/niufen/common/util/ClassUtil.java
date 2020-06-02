package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.IntConstants;
import io.niufen.common.convert.BasicTypeEnum;
import io.niufen.common.io.IORuntimeException;
import io.niufen.common.lang.Assert;
import io.niufen.common.text.StringSpliter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

/**
 * Class 类工具类
 *
 * @author niufen
 */
public class ClassUtil {

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
        final StringBuilder result = StrUtil.builder(size);
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
     * @return true-指定类与给定类的类名相同; false- 指定类与给定类的类名不相同
     */
    public static boolean equals(Class<?> clazz, String className) {
        return equals(clazz, className, Boolean.FALSE);
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
        if (null == clazz || StrUtil.isBlank(className)) {
            return Boolean.FALSE;
        }
        if (ignoreCase) {
            return className.equalsIgnoreCase(clazz.getName()) || className.equalsIgnoreCase(clazz.getSimpleName());
        }
        return className.equals(clazz.getName()) || className.equals(clazz.getSimpleName());
    }

    /**
     * 获得给定类的第一个泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类的类
     * @return {@link Class}
     */
    public static Class<?> getTypeArgument(Class<?> clazz) {
        return getTypeArgument(clazz, IntConstants.ZERO);
    }

    /**
     * 获取给定类的泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定放行类型的类
     * @param index 泛型类型的索引号，即第几个泛型类型
     * @return {@link Class}
     */
    public static Class<?> getTypeArgument(Class<?> clazz, int index) {
        final Type argumentType = TypeUtil.getTypeArgument(clazz, index);
        if (argumentType instanceof Class) {
            return (Class<?>) argumentType;
        }
        return null;
    }

    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return 是否为静态方法
     */
    public static boolean isStatic(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isStatic(method.getModifiers());
    }


    /**
     * 加载类
     *
     * @param <T>           对象类型
     * @param className     类名
     * @param isInitialized 是否初始化
     * @return 类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className, boolean isInitialized) {
        return (Class<T>) ClassLoaderUtil.loadClass(className, isInitialized);
    }

    /**
     * 加载类并初始化
     *
     * @param <T>       对象类型
     * @param className 类名
     * @return 类
     */
    public static <T> Class<T> loadClass(String className) {
        return loadClass(className, true);
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
        return getClassLoader(ClassUtil.class);
    }

    /**
     * 比较判断 types1 和 types2 两组类
     * 如果 types1 中所有的类都与types2 对应位置的类相同，或者是其父类或接口返回true
     *
     * @param types1 类数组1
     * @param types2 类数组2
     * @return true-types1 中所有的类都与types2 对应位置的类相同，或者是其父类或接口返回
     */
    public static boolean isAllAssignableFrom(Class<?>[] types1, Class<?>[] types2) {
        // 如果都为空，则说明相同，返回true
        if (ArrayUtil.isEmpty(types1) && ArrayUtil.isEmpty(types2)) {
            return true;
        }
        // 任意一个为null不相等，说明不同，返回false
        if (null == types1 || null == types2) {
            return false;
        }
        if (types1.length != types2.length) {
            return false;
        }
        Class<?> type1;
        Class<?> type2;
        for (int i = 0; i < types1.length; i++) {
            type1 = types1[i];
            type2 = types2[i];
            if (isBasicType(type1) && isBasicType(type2)) {
                // 原始类型和包装类型存在不一致情况，全部转换为原始类型，如果不一样，返回false
                if (BasicTypeEnum.unWrap(type1) != BasicTypeEnum.unWrap(type2)) {
                    return false;
                }
                // 判断 type1 是不是 type2的父类，或相同类型
            } else if (!type1.isAssignableFrom(type2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查目标类是否可以从原类转化<br/>
     * 就是targetType 是不是 sourceType他爹。相同也返回true
     * 转化包括：
     * 1、原类是对象，目标类型是源类型实现的接口
     * 2、目标类型是原类型的父类
     * 3、两者是原始类型或保证类型（相互转换）
     *
     * @param targetType 目标类型
     * @param sourceType 源类型
     * @return 是否转化
     */
    public static boolean isAssignable(Class<?> targetType, Class<?> sourceType) {
        if (null == targetType || null == sourceType) {
            return false;
        }
        // 对象类型，JDK的 isAssignableFrom 不支持基本类型的判断
        if (targetType.isAssignableFrom(sourceType)) {
            return true;
        }
        // 基本类型
        if (targetType.isPrimitive()) {
            // 原始类型
            Class<?> resolvedPrimitive = BasicTypeEnum.WRAPPER_PRIMITIVE_MAP.get(sourceType);
            return targetType.equals(resolvedPrimitive);
        } else {
            // 包装类型
            Class<?> resolvedWrapper = BasicTypeEnum.PRIMITIVE_WRAPPER_MAP.get(sourceType);
            return resolvedWrapper != null && targetType.isAssignableFrom(resolvedWrapper);
        }
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 被检查的类
     * @return true-基本类型\基本类型包装类；false-非基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 判断传入的类是否为基本类型的包装类型
     *
     * @param clazz 被检查的类
     * @return tue-基本类型包装类；false-非基本类型包装类
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return BasicTypeEnum.WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
    }

    /**
     * 获取指定类型分的默认值<br>
     * 默认值规则为：
     *
     * <pre>
     * 1、如果为原始类型，返回0
     * 2、非原始类型返回{@code null}
     * </pre>
     *
     * @param clazz 类
     * @return 默认值
     * @since 3.0.8
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            if (long.class == clazz) {
                return 0L;
            } else if (int.class == clazz) {
                return 0;
            } else if (short.class == clazz) {
                return (short) 0;
            } else if (char.class == clazz) {
                return (char) 0;
            } else if (byte.class == clazz) {
                return (byte) 0;
            } else if (double.class == clazz) {
                return 0D;
            } else if (float.class == clazz) {
                return 0f;
            } else if (boolean.class == clazz) {
                return false;
            }
        }

        return null;
    }

    /**
     * 获得ClassPath，将编码后的中文路径解码为原字符<br>
     * 这个ClassPath路径会文件路径被标准化处理
     *
     * @return ClassPath
     */
    public static String getClassPath() {
        return getClassPath(false);
    }

    /**
     * 获得ClassPath，这个ClassPath路径会文件路径被标准化处理
     *
     * @param isEncoded 是否编码路径中的中文
     * @return ClassPath
     * @since 3.2.1
     */
    public static String getClassPath(boolean isEncoded) {
        final URL classPathURL = getClassPathURL();
        String url = isEncoded ? classPathURL.getPath() : URLUtil.getDecodedPath(classPathURL);
        return FileUtil.normalize(url);
    }

    /**
     * 获得ClassPath URL
     *
     * @return ClassPath URL
     */
    public static URL getClassPathURL() {
        return getResourceURL(StrUtil.EMPTY);
    }

    /**
     * 获得资源的URL<br>
     * 路径用/分隔，例如:
     *
     * <pre>
     * config/a/db.config
     * spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     * @see ResourceUtil#getResource(String)
     */
    public static URL getResourceURL(String resource) throws IORuntimeException {
        return ResourceUtil.getResource(resource);
    }
}
