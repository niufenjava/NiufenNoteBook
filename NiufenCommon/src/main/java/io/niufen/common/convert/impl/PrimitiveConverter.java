package io.niufen.common.convert.impl;

import io.niufen.common.convert.AbstractConverter;
import io.niufen.common.util.BooleanUtils;
import io.niufen.common.util.DateUtils;
import io.niufen.common.util.NumberUtils;
import io.niufen.common.util.StringUtils;

import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * 原始类型转换器<br>
 * 支持类型为：
 * 1、type
 * 2、short
 * 3、int
 * 4、long
 * 5、float
 * 6、double
 * 7、char
 * 8、boolean
 *
 * @author haijun.zhang
 * @date 2020/5/29
 * @time 21:41
 */
public class PrimitiveConverter extends AbstractConverter<Object> {

    private static final long serialVersionUID = -5341492285090047295L;

    private final Class<?> targetType;

    /**
     * 构造方法
     *
     * @param clazz 需要被转换的原始类
     * @throws IllegalArgumentException 转入的转换类型非原始类型时输出
     */
    public PrimitiveConverter(Class<?> clazz) {
        if (null == clazz) {
            throw new NullPointerException("PrimitiveConverter not allow null target type");
        }
        if (!clazz.isPrimitive()) {
            throw new IllegalArgumentException("PrimitiveConverter() [" + clazz + "] is not a primitive class");
        }
        this.targetType = clazz;
    }

    @Override
    protected Object convertInternal(Object value) {
        // 不需要判断 null，调用此方法之前已经判断
        if (byte.class == this.targetType) {
            return convertToByte(value);
        }
        if (short.class == this.targetType) {
            return convertToShort(value);
        }
        if (int.class == this.targetType) {
            return convertToInt(value);
        }
        if (long.class == this.targetType) {
            return convertToLong(value);
        }
        if (float.class == this.targetType) {
            return convertToFloat(value);
        }
        if (double.class == this.targetType) {
            return convertToDouble(value);
        }
        if (boolean.class == this.targetType) {
            return convertToBoolean(value);
        }
        if (char.class == this.targetType) {
            return convertToChar(value);
        }
        return 0;
    }

    /**
     * 将对象转换成Byte类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 Byte 后的对象
     */
    private byte convertToByte(Object value) {
        try {
            // Number 是byte\short\int\long\float\double 的抽象类
            if (value instanceof Number) {
                return ((Number) value).byteValue();
            }
            if (value instanceof Boolean) {
                return BooleanUtils.toByte((Boolean) value);
            }
            final String strValue = convertToStr(value);
            if (StringUtils.isBlank(strValue)) {
                return 0;
            }
            return Byte.parseByte(strValue);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成Short类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 Byte 后的对象
     */
    private short convertToShort(Object value) {
        try {
            // Number 是byte\short\int\long\float\double 的抽象类
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            }
            if (value instanceof Boolean) {
                return BooleanUtils.toShort((Boolean) value);
            }
            final String strValue = convertToStr(value);
            if (StringUtils.isBlank(strValue)) {
                return 0;
            }
            return Short.parseShort(strValue);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成int类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 int 后的对象
     */
    private int convertToInt(Object value) {
        try {
            // Number 是byte\short\int\long\float\double 的抽象类
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            if (value instanceof Boolean) {
                return BooleanUtils.toInt((Boolean) value);
            }
            if (value instanceof Date) {
                return (int) ((Date) value).getTime();
            }
            if (value instanceof Calendar) {
                return (int) ((Calendar) value).getTimeInMillis();
            }
            if (value instanceof TemporalAccessor) {
                //TODO
                return (int) DateUtils.toInstant((TemporalAccessor) value).toEpochMilli();
            }
            final String strValue = convertToStr(value);
            if (StringUtils.isBlank(strValue)) {
                return 0;
            }
            return NumberUtils.parseInt(strValue);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成 long 类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 long 后的对象
     */
    private long convertToLong(Object value) {
        try {
            // Number 是byte\short\int\long\float\double 的抽象类
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            if (value instanceof Boolean) {
                return BooleanUtils.toInt((Boolean) value);
            }
            if (value instanceof Date) {
                return (int) ((Date) value).getTime();
            }
            if (value instanceof Calendar) {
                return (int) ((Calendar) value).getTimeInMillis();
            }
            if (value instanceof TemporalAccessor) {
                //TODO
                return (int) DateUtils.toInstant((TemporalAccessor) value).toEpochMilli();
            }
            final String strValue = convertToStr(value);
            if (StringUtils.isBlank(strValue)) {
                return 0;
            }
            return NumberUtils.parseLong(strValue);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成 float 类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 float 后的对象
     */
    private float convertToFloat(Object value) {
        try {
            // Number 是byte\short\int\long\float\double 的抽象类
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            }
            if (value instanceof Boolean) {
                return BooleanUtils.toFloat((Boolean) value);
            }
            final String strValue = convertToStr(value);
            if (StringUtils.isBlank(strValue)) {
                return 0;
            }
            return Float.parseFloat(strValue);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成 double 类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 double 后的对象
     */
    private double convertToDouble(Object value) {
        try {
            // Number 是byte\short\int\long\float\double 的抽象类
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            }
            if (value instanceof Boolean) {
                return BooleanUtils.toDouble((Boolean) value);
            }
            final String strValue = convertToStr(value);
            if (StringUtils.isBlank(strValue)) {
                return 0;
            }
            return Double.parseDouble(strValue);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成 char 类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 char 后的对象
     */
    private char convertToChar(Object value) {
        try {
            if (value instanceof Character) {
                //noinspection UnnecessaryUnboxing
                return ((Character) value).charValue();
            } else if (value instanceof Boolean) {
                return BooleanUtils.toChar((Boolean) value);
            }
            final String valueStr = convertToStr(value);
            if (StringUtils.isBlank(valueStr)) {
                return 0;
            }
            return valueStr.charAt(0);
        } catch (Exception e) {
            // Ignore Exception
        }
        return 0;
    }


    /**
     * 将对象转换成 boolean 类型
     *
     * @param value 需要转换的对象值
     * @return 转换成 boolean 后的对象
     */
    private boolean convertToBoolean(Object value) {
        try {
            if (value instanceof Boolean) {
                //noinspection UnnecessaryUnboxing
                return ((Boolean) value).booleanValue();
            }
            String valueStr = convertToStr(value);
            return BooleanUtils.toBoolean(valueStr);
        } catch (Exception e) {
            // Ignore Exception
        }
        return Boolean.FALSE;
    }

    @Override
    protected String convertToStr(Object value) {
        return StringUtils.trim(super.convertToStr(value));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Object> getTargetType() {
        return (Class<Object>) this.targetType;
    }
}
