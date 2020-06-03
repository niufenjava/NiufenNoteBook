package io.niufen.common.bean;

import io.niufen.common.bean.BeanDesc.PropDesc;
import io.niufen.common.bean.copier.BeanCopier;
import io.niufen.common.bean.copier.CopyOptions;
import io.niufen.common.lang.Editor;
import io.niufen.common.util.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haijun.zhang
 * @date 2020/6/2
 * @time 21:45
 */
public class BeanUtil {

    /**
     * 判断是否为Bean对象，判定方法是：
     *
     * <pre>
     *     1、是否存在只有一个参数的setXXX方法
     *     2、是否存在public类型的字段
     * </pre>
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     * @see #hasSetter(Class)
     * @see #hasPublicField(Class)
     */
    public static boolean isBean(Class<?> clazz) {
        return hasSetter(clazz) || hasPublicField(clazz);
    }
    /**
     * 指定类中是否有public类型字段(static字段除外)
     *
     * @param clazz 待测试类
     * @return 是否有public类型字段
     * @since 5.1.0
     */
    public static boolean hasPublicField(Class<?> clazz) {
        if (ClassUtil.isNormalClass(clazz)) {
            for (Field field : clazz.getFields()) {
                if (ModifierUtil.isPublic(field) && false == ModifierUtil.isStatic(field)) {
                    //非static的public字段
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有Setter方法<br>
     * 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     * @since 4.2.2
     */
    public static boolean hasSetter(Class<?> clazz) {
        if (ClassUtil.isNormalClass(clazz)) {
            final Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    // 检测包含标准的setXXX方法即视为标准的JavaBean
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 解析Bean中的属性值
     *
     * @param <T>        属性值类型
     * @param bean       Bean对象，支持Map、List、Collection、Array
     * @param expression 表达式，例如：person.friend[5].name
     * @return Bean属性值
     * @see BeanPath#get(Object)
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object bean, String expression) {
        return (T) BeanPath.create(expression).get(bean);
    }

    /**
     * 设置字段值，，通过反射设置字段值，并不调用setXXX方法<br>
     * 对象同样支持Map类型，fieldNameOrIndex即为key
     *
     * @param bean             Bean
     * @param fieldNameOrIndex 字段名或序号，序号支持负数
     * @param value            值
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void setFieldValue(Object bean, String fieldNameOrIndex, Object value) {
        if (bean instanceof Map) {
            ((Map) bean).put(fieldNameOrIndex, value);
        } else if (bean instanceof List) {
            CollUtil.setOrAppend((List) bean, Convert.toInt(fieldNameOrIndex), value);
        } else if (ArrayUtil.isArray(bean)) {
            ArrayUtil.setOrAppend(bean, Convert.toInt(fieldNameOrIndex), value);
        } else {
            // 普通Bean对象
            ReflectUtil.setFieldValue(bean, fieldNameOrIndex, value);
        }
    }


    /**
     * 给定的Bean的类名是否匹配指定类名字符串<br>
     * 如果isSimple为{@code false}，则只匹配类名而忽略包名，例如：cn.hutool.TestEntity只匹配TestEntity<br>
     * 如果isSimple为{@code true}，则匹配包括包名的全类名，例如：cn.hutool.TestEntity匹配cn.hutool.TestEntity
     *
     * @param bean          Bean
     * @param beanClassName Bean的类名
     * @param isSimple      是否只匹配类名而忽略包名，true表示忽略包名
     * @return 是否匹配
     * @since 4.0.6
     */
    public static boolean isMatchName(Object bean, String beanClassName, boolean isSimple) {
        return ClassUtil.getClassName(bean, isSimple).equals(isSimple ? StrUtil.upperFirst(beanClassName) : beanClassName);
    }

    /**
     * 获得字段值，通过反射直接获得字段值，并不调用getXXX方法<br>
     * 对象同样支持Map类型，fieldNameOrIndex即为key
     *
     * @param bean             Bean对象
     * @param fieldNameOrIndex 字段名或序号，序号支持负数
     * @return 字段值
     */
    public static Object getFieldValue(Object bean, String fieldNameOrIndex) {
        if (null == bean || null == fieldNameOrIndex) {
            return null;
        }

        if (bean instanceof Map) {
            return ((Map<?, ?>) bean).get(fieldNameOrIndex);
        } else if (bean instanceof Collection) {
            return CollUtil.get((Collection<?>) bean, Integer.parseInt(fieldNameOrIndex));
        } else if (ArrayUtil.isArray(bean)) {
            return ArrayUtil.get(bean, Integer.parseInt(fieldNameOrIndex));
        } else {// 普通Bean对象
            return ReflectUtil.getFieldValue(bean, fieldNameOrIndex);
        }
    }

    // --------------------------------------------------------------------------------------------- beanToMap

    /**
     * 对象转Map，不进行驼峰转下划线，不忽略值为空的字段
     *
     * @param bean bean对象
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, false, false);
    }

    /**
     * 对象转Map
     *
     * @param bean              bean对象
     * @param isToUnderlineCase 是否转换为下划线模式
     * @param ignoreNullValue   是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean, boolean isToUnderlineCase, boolean ignoreNullValue) {
        return beanToMap(bean, new LinkedHashMap<>(), isToUnderlineCase, ignoreNullValue);
    }



    /**
     * 对象转Map
     *
     * @param bean              bean对象
     * @param targetMap         目标的Map
     * @param isToUnderlineCase 是否转换为下划线模式
     * @param ignoreNullValue   是否忽略值为空的字段
     * @return Map
     * @since 3.2.3
     */
    public static Map<String, Object> beanToMap(Object bean, Map<String, Object> targetMap, final boolean isToUnderlineCase, boolean ignoreNullValue) {
        if (bean == null) {
            return null;
        }

        return beanToMap(bean, targetMap, ignoreNullValue, key -> isToUnderlineCase ? StrUtil.toUnderlineCase(key) : key);
    }

    /**
     * 对象转Map<br>
     * 通过实现{@link Editor} 可以自定义字段值，如果这个Editor返回null则忽略这个字段，以便实现：
     *
     * <pre>
     * 1. 字段筛选，可以去除不需要的字段
     * 2. 字段变换，例如实现驼峰转下划线
     * 3. 自定义字段前缀或后缀等等
     * </pre>
     *
     * @param bean            bean对象
     * @param targetMap       目标的Map
     * @param ignoreNullValue 是否忽略值为空的字段
     * @param keyEditor       属性字段（Map的key）编辑器，用于筛选、编辑key
     * @return Map
     * @since 4.0.5
     */
    public static Map<String, Object> beanToMap(Object bean, Map<String, Object> targetMap, boolean ignoreNullValue, Editor<String> keyEditor) {
        if (bean == null) {
            return null;
        }

        final Collection<PropDesc> props = BeanUtil.getBeanDesc(bean.getClass()).getProps();

        String key;
        Method getter;
        Object value;
        for (PropDesc prop : props) {
            key = prop.getFieldName();
            // 过滤class属性
            // 得到property对应的getter方法
            getter = prop.getGetter();
            if (null != getter) {
                // 只读取有getter方法的属性
                try {
                    value = getter.invoke(bean);
                } catch (Exception ignore) {
                    continue;
                }
                if (false == ignoreNullValue || (null != value && false == value.equals(bean))) {
                    key = keyEditor.edit(key);
                    if (null != key) {
                        targetMap.put(key, value);
                    }
                }
            }
        }
        return targetMap;
    }

    // --------------------------------------------------------------------------------------------- copyProperties

    /**
     * 获取{@link BeanDesc} Bean描述信息
     *
     * @param clazz Bean类
     * @return {@link BeanDesc}
     * @since 3.1.2
     */
    public static BeanDesc getBeanDesc(Class<?> clazz) {
        BeanDesc beanDesc = BeanDescCache.INSTANCE.getBeanDesc(clazz);
        if (null == beanDesc) {
            beanDesc = new BeanDesc(clazz);
            BeanDescCache.INSTANCE.putBeanDesc(clazz, beanDesc);
        }
        return beanDesc;
    }


    // --------------------------------------------------------------------------------------------- mapToBean

    /**
     * Map转换为Bean对象
     *
     * @param <T>           Bean类型
     * @param map           {@link Map}
     * @param beanClass     Bean Class
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass, boolean isIgnoreError) {
        return fillBeanWithMap(map, ReflectUtil.newInstance(beanClass), isIgnoreError);
    }

    /**
     * Map转换为Bean对象<br>
     * 忽略大小写
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param beanClass     Bean Class
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T mapToBeanIgnoreCase(Map<?, ?> map, Class<T> beanClass, boolean isIgnoreError) {
        return fillBeanWithMapIgnoreCase(map, ReflectUtil.newInstance(beanClass), isIgnoreError);
    }

    /**
     * Map转换为Bean对象
     *
     * @param <T>         Bean类型
     * @param map         {@link Map}
     * @param beanClass   Bean Class
     * @param copyOptions 转Bean选项
     * @return Bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass, CopyOptions copyOptions) {
        return fillBeanWithMap(map, ReflectUtil.newInstance(beanClass), copyOptions);
    }

    // --------------------------------------------------------------------------------------------- fillBeanWithMap

    /**
     * 使用Map填充Bean对象
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isIgnoreError) {
        return fillBeanWithMap(map, bean, false, isIgnoreError);
    }

    /**
     * 使用Map填充Bean对象，可配置将下划线转换为驼峰
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isToCamelCase 是否将下划线模式转换为驼峰模式
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isToCamelCase, boolean isIgnoreError) {
        return fillBeanWithMap(map, bean, isToCamelCase, CopyOptions.create().setIgnoreError(isIgnoreError));
    }

    /**
     * 使用Map填充Bean对象，忽略大小写
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T fillBeanWithMapIgnoreCase(Map<?, ?> map, T bean, boolean isIgnoreError) {
        return fillBeanWithMap(map, bean, CopyOptions.create().setIgnoreCase(true).setIgnoreError(isIgnoreError));
    }

    /**
     * 使用Map填充Bean对象
     *
     * @param <T>         Bean类型
     * @param map         Map
     * @param bean        Bean
     * @param copyOptions 属性复制选项 {@link CopyOptions}
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, CopyOptions copyOptions) {
        return fillBeanWithMap(map, bean, false, copyOptions);
    }

    /**
     * 使用Map填充Bean对象
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isToCamelCase 是否将Map中的下划线风格key转换为驼峰风格
     * @param copyOptions   属性复制选项 {@link CopyOptions}
     * @return Bean
     * @since 3.3.1
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isToCamelCase, CopyOptions copyOptions) {
        if (MapUtil.isEmpty(map)) {
            return bean;
        }
        if (isToCamelCase) {
            map = MapUtil.toCamelCaseMap(map);
        }
        return BeanCopier.create(map, bean, copyOptions).copy();
    }

    // --------------------------------------------------------------------------------------------- fillBean

}
