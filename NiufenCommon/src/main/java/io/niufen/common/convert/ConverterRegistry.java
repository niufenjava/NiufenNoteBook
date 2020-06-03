package io.niufen.common.convert;

import io.niufen.common.convert.impl.PrimitiveConverter;
import io.niufen.common.lang.TypeReference;
import io.niufen.common.util.ClassUtil;
import io.niufen.common.util.ObjectUtil;
import io.niufen.common.util.ReflectUtil;
import io.niufen.common.util.TypeUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 转换器登记中心
 * <p>
 * 将各种类型Convert对象放入登记中心，通过 convert方法查找目标类型对应的转换器，将被转换对象转换。
 * <p>
 * 在此类中，存放着默认转换器和自定义转换器，默认转换器是convert包中预定义的一些转换器，
 * 自定义转换器存放用户自定义的转换器
 *
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 10:30
 */
public class ConverterRegistry implements Serializable {
    private static final long serialVersionUID = 214072619398486837L;

    /**
     * 默认类型转换器
     */
    private Map<Type, IConverter<?>> defaultConverterMap;

    /**
     * 用户自定义类型转换器，在工程中实现 IConverter 接口
     */
    private volatile Map<Type, IConverter<?>> customConverterMap;

    /**
     * 构造
     */
    public ConverterRegistry() {
        initDefaultConverter();
    }

    /**
     * 静态内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，
     * 而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        // 静态初始化器，由 JVM 来保证线程安全
        private static final ConverterRegistry INSTANCE = new ConverterRegistry();
    }

    /**
     * 获得单例的 {@link ConverterRegistry}
     *
     * @return {@link ConverterRegistry}
     */
    public static ConverterRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化默认转换器
     *
     * @return 转换器
     */
    private ConverterRegistry initDefaultConverter() {
        defaultConverterMap = new ConcurrentHashMap<>();

        // 原始类型转换器
        defaultConverterMap.put(int.class, new PrimitiveConverter(int.class));
        defaultConverterMap.put(long.class, new PrimitiveConverter(long.class));
        defaultConverterMap.put(byte.class, new PrimitiveConverter(byte.class));
        defaultConverterMap.put(short.class, new PrimitiveConverter(short.class));
        defaultConverterMap.put(float.class, new PrimitiveConverter(float.class));
        defaultConverterMap.put(double.class, new PrimitiveConverter(double.class));
        defaultConverterMap.put(char.class, new PrimitiveConverter(char.class));
        defaultConverterMap.put(boolean.class, new PrimitiveConverter(boolean.class));

        // 包装类转换器
//        defaultConverterMap.put(Number.class, new NumberConverter());
//        defaultConverterMap.put(Integer.class, new NumberConverter(Integer.class));
//        defaultConverterMap.put(AtomicInteger.class, new NumberConverter(AtomicInteger.class));// since 3.0.8
//        defaultConverterMap.put(Long.class, new NumberConverter(Long.class));
//        defaultConverterMap.put(AtomicLong.class, new NumberConverter(AtomicLong.class));// since 3.0.8
//        defaultConverterMap.put(Byte.class, new NumberConverter(Byte.class));
//        defaultConverterMap.put(Short.class, new NumberConverter(Short.class));
//        defaultConverterMap.put(Float.class, new NumberConverter(Float.class));
//        defaultConverterMap.put(Double.class, new NumberConverter(Double.class));
//        defaultConverterMap.put(Character.class, new CharacterConverter());
//        defaultConverterMap.put(Boolean.class, new BooleanConverter());
//        defaultConverterMap.put(AtomicBoolean.class, new AtomicBooleanConverter());// since 3.0.8
//        defaultConverterMap.put(BigDecimal.class, new NumberConverter(BigDecimal.class));
//        defaultConverterMap.put(BigInteger.class, new NumberConverter(BigInteger.class));
//        defaultConverterMap.put(CharSequence.class, new StringConverter());
//        defaultConverterMap.put(String.class, new StringConverter());

        // URI and URL
//        defaultConverterMap.put(URI.class, new URIConverter());
//        defaultConverterMap.put(URL.class, new URLConverter());

        // 日期时间
//        defaultConverterMap.put(Calendar.class, new CalendarConverter());
//        defaultConverterMap.put(java.util.Date.class, new DateConverter(java.util.Date.class));
//        defaultConverterMap.put(DateTime.class, new DateConverter(DateTime.class));
//        defaultConverterMap.put(java.sql.Date.class, new DateConverter(java.sql.Date.class));
//        defaultConverterMap.put(java.sql.Time.class, new DateConverter(java.sql.Time.class));
//        defaultConverterMap.put(java.sql.Timestamp.class, new DateConverter(java.sql.Timestamp.class));

        // 日期时间 JDK8+(since 5.0.0)
//        defaultConverterMap.put(TemporalAccessor.class, new TemporalAccessorConverter(Instant.class));
//        defaultConverterMap.put(Instant.class, new TemporalAccessorConverter(Instant.class));
//        defaultConverterMap.put(LocalDateTime.class, new TemporalAccessorConverter(LocalDateTime.class));
//        defaultConverterMap.put(LocalDate.class, new TemporalAccessorConverter(LocalDate.class));
//        defaultConverterMap.put(LocalTime.class, new TemporalAccessorConverter(LocalTime.class));
//        defaultConverterMap.put(ZonedDateTime.class, new TemporalAccessorConverter(ZonedDateTime.class));
//        defaultConverterMap.put(OffsetDateTime.class, new TemporalAccessorConverter(OffsetDateTime.class));
//        defaultConverterMap.put(OffsetTime.class, new TemporalAccessorConverter(OffsetTime.class));
//        defaultConverterMap.put(Period.class, new PeriodConverter());
//        defaultConverterMap.put(Duration.class, new DurationConverter());

        // Reference
//        defaultConverterMap.put(WeakReference.class, new ReferenceConverter(WeakReference.class));// since 3.0.8
//        defaultConverterMap.put(SoftReference.class, new ReferenceConverter(SoftReference.class));// since 3.0.8
//        defaultConverterMap.put(AtomicReference.class, new AtomicReferenceConverter());// since 3.0.8

        // 其它类型
//        defaultConverterMap.put(Class.class, new ClassConverter());
//        defaultConverterMap.put(TimeZone.class, new TimeZoneConverter());
//        defaultConverterMap.put(Locale.class, new LocaleConverter());
//        defaultConverterMap.put(Charset.class, new CharsetConverter());
//        defaultConverterMap.put(Path.class, new PathConverter());
//        defaultConverterMap.put(Currency.class, new CurrencyConverter());// since 3.0.8
//        defaultConverterMap.put(UUID.class, new UUIDConverter());// since 4.0.10
//        defaultConverterMap.put(StackTraceElement.class, new StackTraceElementConverter());// since 4.5.2
//        defaultConverterMap.put(Optional.class, new OptionalConverter());// since 5.0.0
        return this;
    }


    /**
     * 登记自定义转换器
     *
     * @param type               转换的目标类型
     * @param converterImplClass 转换器类，必须有默认构造方法
     * @return {@link ConverterRegistry}
     */
    public ConverterRegistry registry(Type type, Class<? extends IConverter<?>> converterImplClass) {
        return registry(type, ReflectUtil.newInstance(converterImplClass));
    }

    /**
     * 登记自定义转换器
     *
     * @param type      转换的目标类型
     * @param converter 转换器，实现了 IConverter接口
     * @return {@link ConverterRegistry}
     */
    public ConverterRegistry registry(Type type, IConverter<?> converter) {
        // 如果 customConverterMap 未被初始化
        if (null == customConverterMap) {
            // 锁住
            synchronized (this) {
                // 如果 customConverterMap 未被初始化
                if (null == customConverterMap) {
                    // 进行初始化
                    // todo 使用map工具类生成
                    customConverterMap = new ConcurrentHashMap<>();
                }
            }
        }
        customConverterMap.put(type, converter);
        return this;
    }

    /**
     * 获得转换器<br>
     *
     * @param <T>           转换的目标类型
     * @param type          类型
     * @param isCustomFirst 是否自定义转换器优先
     * @return 转换器
     */
    public <T> IConverter<T> getConverter(Type type, boolean isCustomFirst) {
        IConverter<T> converter;
        if (isCustomFirst) {
            converter = this.getCustomConverter(type);
            if (null == converter) {
                converter = this.getDefaultConverter(type);
            }
        } else {
            converter = this.getDefaultConverter(type);
            if (null == converter) {
                converter = this.getCustomConverter(type);
            }
        }
        return converter;
    }

    /**
     * 获得默认转换器
     *
     * @param <T>  转换的目标类型（转换器转换到的类型）
     * @param type 类型
     * @return 转换器
     */
    @SuppressWarnings("unchecked")
    public <T> IConverter<T> getDefaultConverter(Type type) {
        return (null == defaultConverterMap) ? null : (IConverter<T>) defaultConverterMap.get(type);
    }

    /**
     * 获取自定义转换器
     *
     * @param type 类型
     * @param <T>  转换的目标类型（转换器转换的类型）
     * @return 转换器
     */
    @SuppressWarnings("unchecked")
    public <T> IConverter<T> getCustomConverter(Type type) {
        return (null == customConverterMap) ? null : (IConverter<T>) customConverterMap.get(type);
    }

    /**
     * 特殊类型转换<br>
     * 1. Collection
     * 2. Map
     * 3. 强转（无需转换）
     * 4. 数组
     *
     * @param type         目标类型
     * @param typeClass    目标类型的Class对象
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @param <T>          转换的目标类型（转换器转换到的类型）
     * @return 转换后的值
     */
    @SuppressWarnings("unchecked")
    private <T> T convertSpecial(Type type, Class<T> typeClass, Object value, T defaultValue) {
        if (null == typeClass) {
            return null;
        }
        //集合转换（不可以默认强转） Collection集合 是不是 typeClass 目标类型的Class 的父类或接口
        // 就是说，目标类是不是 集合
        if (ClassUtil.isAssignable(Collection.class, typeClass)) {
            final CollectionConverter collectionConverter = new CollectionConverter(type);
            return (T) collectionConverter.convert(value, (Collection<?>) defaultValue);
        }

        //Map类型（不可以默认强转）
        if (ClassUtil.isAssignable(Map.class, typeClass)) {
            final MapConverter mapConverter = new MapConverter(type);
            return (T) mapConverter.convert(value, (Map<?, ?>) defaultValue);
        }

        //默认强转
        if (typeClass.isInstance(value)) {
            return (T) value;
        }

        // 枚举转换
        if (typeClass.isEnum()) {
            return (T) new EnumConverter(typeClass).convert(value, defaultValue);
        }

        //数组转换
        if (typeClass.isArray()) {
            final ArrayConverter arrayConverter = new ArrayConverter(typeClass);
            try {
                return (T) arrayConverter.convert(value, defaultValue);
            } catch (Exception e) {
                // 数组转换失败进行下一步
            }

        }
        return null;
    }


    /**
     * 转换值为指定类型<br>
     * 自定义转换器优先
     *
     * @param <T>          转换的目标类型（转换器转换到的类型）
     * @param type         类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public <T> T convert(Type type, Object value, T defaultValue) throws ConvertException {
        return convert(type, value, defaultValue, true);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>   转换的目标类型（转换器转换到的类型）
     * @param type  类型
     * @param value 值
     * @return 转换后的值，默认为<code>null</code>
     * @throws ConvertException 转换器不存在
     */
    public <T> T convert(Type type, Object value) throws ConvertException {
        return convert(type, value, null);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>           转换的目标类型（转换器转换到的类型）
     * @param type          类型目标
     * @param value         被转换值
     * @param defaultValue  默认值
     * @param isCustomFirst 是否自定义转换器优先
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Type type, Object value, T defaultValue, boolean isCustomFirst) throws ConvertException {
        if (TypeUtil.isUnknow(type) && null == defaultValue) {
            // 对于用户不指定目标类型的情况，返回原值
            return (T) value;
        }
        if (ObjectUtil.isNull(value)) {
            return defaultValue;
        }
        if (TypeUtil.isUnknow(type)) {
            type = defaultValue.getClass();
        }

        if (type instanceof TypeReference) {
            type = ((TypeReference<?>) type).getType();
        }

        // 标准转换器
        final IConverter<T> converter = getConverter(type, isCustomFirst);
        if (null != converter) {
            return converter.convert(value, defaultValue);
        }

        Class<T> rowType = (Class<T>) TypeUtil.getClass(type);
        if (null == rowType) {
            if (null != defaultValue) {
                rowType = (Class<T>) defaultValue.getClass();
            } else {
                // 无法识别的泛型类型，按照Object处理
                return (T) value;
            }
        }

        // 特殊类型转换，包括Collection、Map、强转、Array等
        final T result = convertSpecial(type, rowType, value, defaultValue);
        if (null != result) {
            return result;
        }

        // 尝试转Bean
        // todo
//        if (BeanUtil.isBean(rowType)) {
//            return new BeanConverter<T>(type).convert(value, defaultValue);
//        }

        // 无法转换
        throw new ConvertException("No Converter for type [{}]", rowType.getName());
    }

}

