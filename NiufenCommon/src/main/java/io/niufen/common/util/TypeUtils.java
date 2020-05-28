package io.niufen.common.util;

import io.niufen.common.constant.IntConstants;

import java.lang.reflect.*;
import java.util.List;

/**
 * 针对 {@link java.lang.reflect.Type} 的工具封装类<br>
 * 最主要的功能包括:
 * 1. 获取方法的参数和返回值类型（包括 Type 和 Class)
 * 2. 获取泛型参数类型（包括对象的方向参数或集合元素的泛型类型）
 * <p>
 * Type是 Java 中所有类型的父接口，是对 Java 语言中类型的一个抽象。
 * <p>
 * Type 接口有4个子接口:
 * 1. TypeVariable 类型变量
 * 2. ParameterizedType 参数化类型
 * 3. GenericArrayType 普通数组类型
 * 4. WildcardType 通配符类型
 * <p>
 * 还有一个实现类：Class
 *
 * @author haijun.zhang
 * @date 2020/5/27
 * @time 16:37
 */
public class TypeUtils {

    /**
     * 本工具类中使用到的数组第一个下标索引
     */
    public static final int FIRST_INDEX = IntConstants.ZERO;

    // --------------- Field Type ---------------

    /**
     * 获取Type对应的原始类
     *
     * @param type {@link java.lang.reflect.Type}
     * @return 原始类，如果无法获取原始类，返回 {@code null}
     */
    public static Class<?> getClass(Type type) {
        if (null == type) {
            return null;
        }
        // 如果是Class 的实现类，那强制转换成 Class
        if (type instanceof Class) {
            return (Class<?>) type;
        }

        // 如果type 是参数化类型,User<T> 就是一个参数化类型
        if (type instanceof ParameterizedType) {
            // 强制转换成参数化类型，再获取他的参数原始类型，再强制转换成 Class
            // parameterizedType.getRawType() 获取参数原始类型
            // public Map<String, T> map = new HashMap<>();
            // ==>> Map.class
            return (Class<?>) ((ParameterizedType) type).getRawType();
        }

        // 如果 type 是类型变量，User<T> 中的 T 就是一个 类型变量
        if (type instanceof TypeVariable) {
            // 强制转换成类型变量
            // typeVariable.getBounds() 返回一个类型对象数组，表示此类型变量的父类。注意，如果没有显式声明父类，则父类是Object。
            // V extends Number
            // getBounds 返回 Number
            return (Class<?>) ((TypeVariable<?>) type).getBounds()[FIRST_INDEX];
        }

        // 如果 type 是 通配符类型
        if (type instanceof WildcardType) {
            // 强制转换成通配符类型
            // getUpperBounds 获取通配符的父类，代码中定义的 ? extend String => String
            final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            // 如果只有一个父类，那么遍历
            if (upperBounds.length == 1) {
                return getClass(upperBounds[0]);
            }
        }
        return null;
    }

    /**
     * 获取字段对应的 Type 类型<br>
     * 方法优先获取 GenericType，获取不到则获取 Type(Class)
     *
     * @param field {@link java.lang.reflect.Field}
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getType(Field field) {
        if (null == field) {
            return null;
        }
        return field.getGenericType();
    }

    /**
     * 获取 Field 对应的原始类
     *
     * @param field {@link java.lang.reflect.Field}
     * @return 获取字段的原始类，如果无法获取原始类，返回 {@code null}
     */
    public static Class<?> getClass(Field field) {
        return null == field ? null : field.getType();
    }

    // --------------- Param Type  ---------------

    /**
     * 获取方法的第一个参数类型
     * 优先获取方法的GenericParameterTypes，如果获取不到，则获取ParameterTypes
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getFirstParamType(Method method) {
        return getParamType(method, FIRST_INDEX);
    }

    /**
     * 获取方法的第一个参数类
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @return {@link Class}，可能为{@code null}
     */
    public static Class<?> getFirstParamClass(Method method) {
        return getParamClass(method, FIRST_INDEX);
    }

    /**
     * 获取方法的参数类型<br>
     * 优先获取方法的 GenericParameterTypes, 如果获取不到，则获取 ParameterTypes
     * <p>
     * getGenericParameterTypes：返回Type类型的数组 Type[].
     * getParameterTypes：返回Class类型的数组： Class<?>[].
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @param index  第几个参数的索引，从0开始
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getParamType(Method method, int index) {
        Type[] types = getParamTypes(method);
        if (ArrayUtils.isEmpty(types) || types.length <= index) {
            return null;
        }
        return types[index];
    }

    /**
     * 获取方法的参数类
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @param index  第几个参数的索引，从0开始
     * @return 参数类，可能为{@code null}
     */
    public static Class<?> getParamClass(Method method, int index) {
        Class<?>[] classes = getParamClasses(method);
        if (ArrayUtils.isEmpty(classes) || classes.length <= index) {
            return null;
        }
        return classes[index];
    }

    /**
     * 获取方法的参数类型列表<br>
     * 优先获取方法的 GenericParameterTypes, 如果获取不到，则获取 ParameterTypes
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @return {@link Type} 数组，可能为{@code null}
     */
    public static Type[] getParamTypes(Method method) {
        // method.getGenericParameterTypes() 返回该方法入参的类型数组，如果无惨方法，则返回空数组
        return null == method ? null : method.getGenericParameterTypes();
    }

    /**
     * 解析方法的参数类型列表<br>
     * method.getParameterTypes() 返回方法入参的类数组
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @return {@link Type} 数组，可能为{@code null}
     */
    public static Class[] getParamClasses(Method method) {
        // method.getParameterTypes() 返回该方法入参的类数组，如果无惨方法，则返回空数组
        return null == method ? null : method.getParameterTypes();
    }

    // --------------- Return Type  ---------------

    /**
     * 获取方法的返回值类型<br>
     * 使用 method.getGenericReturnType() 方法
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getReturnType(Method method) {
        return null == method ? null : method.getGenericReturnType();
    }

    /**
     * 获取方法的返回值类<br>
     * 使用 method.getGenericReturnType() 方法
     *
     * @param method {@link java.lang.reflect.Method} 方法
     * @return 返回值类型的类
     */
    public static Type getReturnClass(Method method) {
        return null == method ? null : method.getReturnType();
    }

    // --------------- Type Argument  ---------------

    /**
     * 获得给定类的第一个泛型参数
     *
     * @param type 被检查的类型，必须是已经确定泛型类型的类型
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getTypeFirstArgument(Type type) {
        return getTypeArgument(type, FIRST_INDEX);
    }

    /**
     * 获得给定类的泛型参数
     *
     * @param type  被检查的类型，必须是已经确定泛型类型的类
     * @param index 泛型类型的索引号，即第几个泛型类型
     * @return {@link Type}
     */
    public static Type getTypeArgument(Type type, int index) {
        final Type[] typeArguments = getTypeArguments(type);
        if (ArrayUtils.isEmpty(typeArguments) || typeArguments.length <= index) {
            return null;
        }
        return typeArguments[index];
    }

    /**
     * 获取指定类型中所有泛型参数类型，例如：
     * public class A <T>
     * public class B extends A<String>
     * 通过此方法，传入B.class 即可得到 String
     *
     * @param type 指定类型
     * @return 所有泛型参数类型
     */
    public static Type[] getTypeArguments(Type type) {
        if (null == type) {
            return null;
        }
        final ParameterizedType parameterizedType = toParameterizedType(type);
        return (null == parameterizedType) ? null : parameterizedType.getActualTypeArguments();
    }

    /**
     * 将 {@link Type} 转换为{@link ParameterizedType}<br/>
     * {@link ParameterizedType} 用于获取当前类或父类中泛型参数化后的类型<br/>
     * 一般用于获取泛型参数具体的参数类型，例如：
     * class A <T>
     * class B extends A<String>
     * 通过此方法，传入 B.class 即可得到 {@link ParameterizedType}, 从而获得 String
     *
     * @param type {@link Type}
     * @return {@link ParameterizedType}
     */
    public static ParameterizedType toParameterizedType(Type type) {

        // 如果传入的类型本身就是 参数化类型 List<T>
        if (type instanceof ParameterizedType) {
            // 强制转换后直接返回
            return (ParameterizedType) type;
        }

        // 如果传入的类型是一个 Class类
        if (type instanceof Class) {
            // 将Type 强转为 Class
            final Class<?> clazz = (Class<?>) type;
            // 先获取 clazz 的父类型
            Type genericSuper = clazz.getGenericSuperclass();
            if (null == genericSuper || Object.class.equals(genericSuper)) {
                // 如果类没有父类，而是实现一些定义好的泛型接口，则取接口的Type
                final Type[] genericInterfaces = clazz.getGenericInterfaces();
                if (ArrayUtils.isNotEmpty(genericInterfaces)) {
                    genericSuper = genericInterfaces[FIRST_INDEX];
                }
            }
            // 递归
            return toParameterizedType(genericSuper);
        }
        return null;
    }

    /**
     * 获取指定泛型变量对应的真实类型第一个 <br/>
     * 由于子类中泛型参数实现和父类（接口）中泛型定义位置是一一对应的，因此可以通过对应关系找到泛型实现类型<br>
     * 使用此方法注意：
     * 1. superClass 必须是 clazz的父类或者clazz实现的接口
     * 2. typeVariable 必须在 superClass 中声明
     *
     * @param actualType      真是类型所在类，此类中记录了泛型参数对应的实际类型
     * @param typeDefineClass 泛型变量声明所在类或接口，此类中定义了泛型类型
     * @param typeVariable    泛型变量，需要的实际类型对应的泛型参数
     * @return 给定泛型参数对应的实际类型，如果无对应类型，返回 null
     */
    public static Type getActualType(Type actualType, Class<?> typeDefineClass, Type typeVariable) {
        Type[] types = getActualTypes(actualType, typeDefineClass, typeVariable);
        if (ArrayUtils.isEmpty(types)) {
            return null;
        }

        return types[FIRST_INDEX];
    }

    /**
     * 获取指定泛型变量对应的真实类型 <br/>
     * 由于子类中泛型参数实现和父类（接口）中泛型定义位置是一一对应的，因此可以通过对应关系找到泛型实现类型<br>
     * 使用此方法注意：
     * 1. superClass 必须是 clazz的父类或者clazz实现的接口
     * 2. typeVariable 必须在 superClass 中声明
     *
     * @param actualType      真是类型所在类，此类中记录了泛型参数对应的实际类型
     * @param typeDefineClass 泛型变量声明所在类或接口，此类中定义了泛型类型
     * @param typeVariables   泛型变量，需要的实际类型对应的泛型参数
     * @return 给定泛型参数对应的实际类型，如果无对应类型，返回 null
     */
    public static Type[] getActualTypes(Type actualType, Class<?> typeDefineClass, Type... typeVariables) {
        if (null == actualType) {
            throw new IllegalArgumentException("Parameter actualType is null");
        }
        if (null == typeDefineClass) {
            throw new IllegalArgumentException("Parameter typeDefineClass is null");
        }

        // class.isAssignableFrom()
        // 确定此类对象所表示的类或接口是否与指定的类参数所表示的类或接口相同
        // 或是否为该类或接口的超类或超接口。
        if (!typeDefineClass.isAssignableFrom(getClass(actualType))) {
            throw new IllegalArgumentException("Parameter [superClass] must be assignable from [clazz]");
        }

        // 泛型参数标识符列表
        final TypeVariable<?>[] typeVars = typeDefineClass.getTypeParameters();
        if (ArrayUtils.isEmpty(typeVars)) {
            return null;
        }

        // 实际类型列表
        final Type[] actualTypeArguments = TypeUtils.getTypeArguments(actualType);
        if (ArrayUtils.isEmpty(actualTypeArguments)) {
            return null;
        }

        int size = Math.min(actualTypeArguments.length, typeVars.length);
//        TODO
//        final Map<TypeVariable<?>,Type> tableMap = new TableMap<>(typeVars,actualTypeArguments);

        final List<TypeVariable<?>> keyList = ListUtils.newList(size);
        final List<Type> valueList = ListUtils.newList(size);
        for (int i = 0; i < size; i++) {
            keyList.add(typeVars[i]);
            valueList.add(actualTypeArguments[i]);
        }

        // 查找方法定义所在类或接口中此泛型参数的位置
        final Type[] result = new Type[size];
        for (int i = 0; i < typeVariables.length; i++) {
            // TODO
            result[i] = (typeVariables[i] instanceof TypeVariable) ? valueList.get(i) : typeVariables[i];
        }

        return result;
    }

    /**
     * 是否未知类型<br/>
     * type 为 null 或者 {@link TypeVariable} 都是为未知类型
     *
     * @param type Type类型
     * @return true-未知类型；false-已知类型
     */
    public static boolean isUnknownType(Type type) {
        return null == type || type instanceof TypeVariable;
    }

    /**
     * 指定泛型数组中是否有泛型变量
     *
     * @param types 泛型数组
     * @return true-含有泛型变量;false-不包含泛型变量
     */
    public static boolean hasTypeVariable(Type... types) {
        for (Type type : types) {
            if (type instanceof TypeVariable) {
                return true;
            }
        }
        return false;
    }


}
