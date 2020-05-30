package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.StringConstants;
import io.niufen.common.convert.BasicTypeEnum;
import io.niufen.common.exception.UtilException;
import io.niufen.common.lang.SimpleCache;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ClassLoader} 工具类
 *
 * @author haijun.zhang
 * @date 2020/5/28
 * @time 22:29
 */
public class ClassLoaderUtils {

    /**
     * 数组类的结尾符："[]"
     */
    private static final String ARRAY_SUFFIX = "[]";

    /**
     * 内部数组类名前缀 "["
     */
    private static final String INTERNAL_ARRAY_PREFIX = "[";

    /**
     * 内部非原始类型类名前缀 "[L"
     */
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

    /**
     * 包名分界符：'.'
     */
    private static final char PACKAGE_SEPARATOR = CharConstants.DOT;

    /**
     * 内部类分界符：'$'
     */
    private static final char INNER_CLASS_SEPARATOR = CharConstants.DOLLAR_SIGN;

    /**
     * 基本类型class类名最大长度
     */
    private static final int PRIMITIVE_CLASS_NAME_MAX_LENGTH = 8;

    /**
     * 原始类型和其class对应表，例如：int =>> int.class
     */
    private static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP = new ConcurrentHashMap<>(32);


    /**
     * 缓存
     */
    private static final SimpleCache<String, Class<?>> CLASS_CACHE = new SimpleCache<>();

    static {
        List<Class<?>> primitiveTypes = ListUtils.newList(32);
        // 加入原始类型
        primitiveTypes.addAll(BasicTypeEnum.PRIMITIVE_WRAPPER_MAP.keySet());
        // 加入原始类型数组类型
        primitiveTypes.add(boolean[].class);
        primitiveTypes.add(byte[].class);
        primitiveTypes.add(char[].class);
        primitiveTypes.add(double[].class);
        primitiveTypes.add(float[].class);
        primitiveTypes.add(int[].class);
        primitiveTypes.add(long[].class);
        primitiveTypes.add(short[].class);
        primitiveTypes.add(void.class);
        for (Class<?> primitiveType : primitiveTypes) {
            PRIMITIVE_TYPE_NAME_MAP.put(primitiveType.getName(), primitiveType);
        }
    }

    /**
     * 获取当前线程的{@link ClassLoader}
     *
     * @return 当前线程的 class loader
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取{@link ClassLoader}<br/>
     * 获取顺序如下：
     * 1. 获取当前线程的 ClassLoader
     * 2. 获取{@link ClassLoaderUtils}类对应的ClassLoader
     * 3. 获取系统ClassLoader ({@link ClassLoader#getSystemClassLoader()})
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (null == classLoader) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    // ----------- loadClass

    /**
     * 加载类，通过传入类的字符串，返回其对应的类名，
     * 使用默认ClassLoader并初始化类（调用static模块内容和初始化static属性）<br>
     * 扩展{@link Class#forName(String, boolean, ClassLoader)}方法，支持以下几类类名的加载：
     *
     * <pre>
     * 1、原始类型，例如：int
     * 2、数组类型，例如：int[]、Long[]、String[]
     * 3、内部类，例如：java.lang.Thread.State会被转为java.lang.Thread$State加载
     * </pre>
     *
     * @param name 类名
     * @return 类名对应的类
     * @throws UtilException 包装{@link ClassNotFoundException}，没有类名对应的类时抛出此异常
     */
    public static Class<?> loadClass(String name) throws UtilException {
        return loadClass(name, true);
    }

    /**
     * 加载类，通过传入类的字符串，返回其对应的类名，使用默认ClassLoader<br>
     * 扩展{@link Class#forName(String, boolean, ClassLoader)}方法，支持以下几类类名的加载：
     *
     * <pre>
     * 1、原始类型，例如：int
     * 2、数组类型，例如：int[]、Long[]、String[]
     * 3、内部类，例如：java.lang.Thread.State会被转为java.lang.Thread$State加载
     * </pre>
     *
     * @param name          类名
     * @param isInitialized 是否初始化类（调用static模块内容和初始化static属性）
     * @return 类名对应的类
     * @throws UtilException 包装{@link ClassNotFoundException}，没有类名对应的类时抛出此异常
     */
    public static Class<?> loadClass(String name, boolean isInitialized) throws UtilException {
        return loadClass(name, null, isInitialized);
    }

    /**
     * 加载类，通过传入类的字符串，返回其对应的类Class <br/>
     * 此方法支持缓存，第一次被加载的类之后读取缓存中的类 <br/>
     * 加载失败的原因可能是此类不存在或其关联引用类不存在 <br/>
     * 扩展{@link Class#forName(String, boolean, ClassLoader)}方法，支持以下几类类名的加载：
     * 1、原始类型，例如：int
     * 2、数组类型，例如：int []
     * 3、内部类，例如：java.lang.Thread.State 会被转为 java.lang.Thread$State 加载
     *
     * @param name          类名
     * @param classLoader   {@link ClassLoader}，{@code null} 则使用系统默认ClassLoader
     * @param isInitialized 是否初始化类（调用 static 模块内容和初始化 static 属性）
     * @return 类名对应的类
     * @throws UtilException 包装{@link ClassNotFoundException}，没有类名对应的类时抛出此异常
     */
    public static Class<?> loadClass(String name, ClassLoader classLoader, boolean isInitialized) throws UtilException {
        // TODO Assert.notNull(name,"Name must not be null")
        if (StringUtils.isBlank(name)) {
            throw new UtilException("Name must not be null");
        }
        // 加载原始类型和缓存中的类
        Class<?> clazz = loadPrimitiveClass(name);
        if (null == clazz) {
            clazz = CLASS_CACHE.get(name);
        }
        if (null != clazz) {
            return clazz;
        }

        if (StringUtils.endWith(name, ARRAY_SUFFIX)) {
            // 对象数组 "java.lang.String[]" 风格
            final String elementClassName = StringUtils.removeSuffix(name, ARRAY_SUFFIX);
            // 递归
            final Class<?> elementClass = loadClass(elementClassName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else if (StringUtils.startWith(name, NON_PRIMITIVE_ARRAY_PREFIX) && StringUtils.endWith(name, StringConstants.Mark.SEMICOLON)) {
            // 内部非原始类型类名前缀 "[L"
            // "[Ljava.lang.String;" 风格
            final String elementName = StringUtils.sub(name, NON_PRIMITIVE_ARRAY_PREFIX.length(), name.length() - 1);
            final Class<?> elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else if (StringUtils.startWith(name, INTERNAL_ARRAY_PREFIX)) {
            // "[[I" 或 "[[Ljava.lang.String;" 风格
            final String elementName = StringUtils.subSuf(name, INTERNAL_ARRAY_PREFIX.length());
            final Class<?> elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else {
            // 加载普通类
            if (null == classLoader) {
                classLoader = getClassLoader();
            }
            try {
                clazz = Class.forName(name, isInitialized, classLoader);
            } catch (ClassNotFoundException ex) {
                // 尝试获取内部类，例如 java.lang.Thread.State => java.lang.Thread$State
                clazz = tryLoadInnerClass(name, classLoader, isInitialized);
                if (null == clazz) {
                    throw new UtilException(ex);
                }
            }
        }
        // 加入缓存并返回
        return CLASS_CACHE.put(name, clazz);
    }

    /**
     * 加载原始类型的类。包括原始类型、原始类型数组和 void
     *
     * @param name 原始类型名，例如 int
     * @return 原始类型类
     */
    public static Class<?> loadPrimitiveClass(String name) {
        Class<?> result = null;
        if (StringUtils.isNotBlank(name)) {
            name = name.trim();
            if (name.length() <= PRIMITIVE_CLASS_NAME_MAX_LENGTH) {
                result = PRIMITIVE_TYPE_NAME_MAP.get(name);
            }
        }
        return result;
    }

    /**
     * 创建新的{@link JarClassLoader}，并使用此Classloader加载目录下的class文件和jar文件
     *
     * @param jarOrDir jar文件或者包含jar和class文件的目录
     * @return {@link JarClassLoader}
     * @since 4.4.2
     */
    // TODO
//    public static JarClassLoader getJarClassLoader (File jarOrDir ) {
//        return JarClassLoader.load(jarOrDir);
//    }

    // ----------- Private method start ------------


    /**
     * 加载外部类
     *
     * @param jarOrDir jar文件或者包含jar和class文件的目录
     * @param name     类名
     * @return 类
     * @since 4.4.2
     */
    public static Class<?> loadClass(File jarOrDir, String name) {
//        TODO
//        try {
//            return getJarClassLoader(jarOrDir).loadClass(name);
//        } catch (ClassNotFoundException e) {
//            throw new UtilException(e);
//        }
        return null;
    }

    // -------- isPresent --------

    /**
     * 指定类是否被提供，使用默认ClassLoader<br>
     * 通过调用{@link #loadClass(String, ClassLoader, boolean)}方法尝试加载指定类名的类，如果加载失败返回false<br>
     * 加载失败的原因可能是此类不存在或其关联引用类不存在
     *
     * @param className 类名
     * @return 是否被提供
     */
    public static boolean isPresent(String className) {
        return isPresent(className, null);
    }

    /**
     * 指定类是否被提供<br>
     * 通过调用{@link #loadClass(String, ClassLoader, boolean)}方法尝试加载指定类名的类，如果加载失败返回false<br>
     * 加载失败的原因可能是此类不存在或其关联引用类不存在
     *
     * @param className   类名
     * @param classLoader {@link ClassLoader}
     * @return 是否被提供
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {

        try {
            loadClass(className, classLoader, Boolean.FALSE);
            return Boolean.TRUE;
        } catch (Throwable ex) {
            return Boolean.FALSE;
        }
    }

    /**
     * 尝试转换并加载内部类
     * 例如：java.lang.Thread.State =>> java.lang.Thread$State
     *
     * @param name          类名
     * @param classLoader   {@link ClassLoader} ，null 则使用系统默认 ClassLoader
     * @param isInitialized 是否初始化类（调用static模块内容和初始化static属性）
     * @return 类名对应的类
     */
    private static Class<?> tryLoadInnerClass(String name, ClassLoader classLoader, boolean isInitialized) {
        // 尝试获取内部类，例如 java.lang.Thread.State =>> java.lang.Thread$State
        final int lastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
        if (lastDotIndex > 0) {
            final String innerClassName = name.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR + name.substring(lastDotIndex + 1);
            try {
                return Class.forName(innerClassName, isInitialized, classLoader);
            } catch (ClassNotFoundException ex) {
                // 尝试获取内部类失败，忽略
            }
        }
        return null;
    }
}