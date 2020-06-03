package io.niufen.common.util;

/**
 * Boolean类型相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/29
 * @time 21:52
 */
public class BooleanUtil {

    private static final byte BYTE_TRUE = 1;

    private static final byte BYTE_FALSE = 0;

    private static final short SHORT_TRUE = 1;

    private static final short SHORT_FALSE = 0;

    private static final int INT_TRUE = 1;

    private static final int INT_FALSE = 0;

    private static final long LONG_TRUE = 1L;

    private static final long LONG_FALSE = 0L;

    /**
     * 表示为真的字符串数组
     */
    private static final String[] TRUE_ARRAY = {"true", "True", "TRUE", "yes", "Yes", "YES", "y", "Y", "t", "T", "ok", "Ok", "OK", "1", "on", "On", "ON", "是", "对", "真", "對"};

    /**
     * 取相反值
     * !bool 要比 bool ? Boolean.FALSE : Boolean.TRUE; 效率高
     *
     * @param bool Boolean值
     * @return 相反的boolean值
     */
    public static Boolean negate(Boolean bool) {
        if (null == bool) {
            return null;
        }
        return !bool;
    }

    /**
     * 取相反值
     *
     * @param bool Boolean值
     * @return 相反的Boolean值
     */
    public static boolean negate(boolean bool) {
        return !bool;
    }

    /**
     * 检查 {@code Boolean} 值是否为 {@code true}
     *
     * <pre>
     *   BooleanUtil.isTrue(Boolean.TRUE)  = true
     *   BooleanUtil.isTrue(Boolean.FALSE) = false
     *   BooleanUtil.isTrue(null)          = false
     * </pre>
     *
     * @param bool 被检查的Boolean值
     * @return 当值为true且非null时返回{@code true}
     */
    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    /**
     * 检查 {@code Boolean} 值是否为 {@code false}
     *
     * <pre>
     *   BooleanUtil.isFalse(Boolean.TRUE)  = false
     *   BooleanUtil.isFalse(Boolean.FALSE) = true
     *   BooleanUtil.isFalse(null)          = false
     * </pre>
     *
     * @param bool 被检查的Boolean值
     * @return 当值为false且非null时返回{@code true}
     */
    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    /**
     * 转换字符串为boolean值
     *
     * @param valueStr 字符串
     * @return boolean值
     */
    public static boolean toBoolean(String valueStr) {
        if (StrUtil.isNotBlank(valueStr)) {
            valueStr = valueStr.trim().toLowerCase();
            return ArrayUtil.contains(TRUE_ARRAY, valueStr);
        }
        return false;
    }

    /**
     * boolean 值转为 short
     * SHORT_TRUE = 1;
     * SHORT_FALSE = 0;
     *
     * @param value Boolean 值
     * @return 1-true；0-false
     */
    public static byte toByte(boolean value) {
        return value ? BYTE_TRUE : BYTE_FALSE;
    }

    /**
     * boolean值转为Byte
     *
     * @param value Boolean值
     * @return Byte值
     */
    public static Byte toByteObj(boolean value) {
        return toByte(value);
    }

    /**
     * boolean 值转为 short
     * SHORT_TRUE = 1;
     * SHORT_FALSE = 0;
     *
     * @param value Boolean 值
     * @return 1-true；0-false
     */
    public static short toShort(boolean value) {
        return value ? SHORT_TRUE : SHORT_FALSE;
    }

    /**
     * boolean值转为Short
     *
     * @param value Boolean值
     * @return Short值
     */
    public static Short toShortObj(boolean value) {
        return toShort(value);
    }


    /**
     * boolean 值转为 int
     * INT_TRUE = 1;
     * INT_FALSE = 0;
     *
     * @param value Boolean 值
     * @return 1-true；0-false
     */
    public static int toInt(boolean value) {
        return value ? INT_TRUE : INT_FALSE;
    }

    /**
     * boolean值转为Integer
     *
     * @param value Boolean值
     * @return Integer值
     */
    public static Integer toInteger(boolean value) {
        return toInt(value);
    }

    /**
     * boolean 值转为 long
     * LONG_TRUE = 1;
     * LONG_FALSE = 0;
     *
     * @param value Boolean 值
     * @return 1-true；0-false
     */
    public static long toLong(boolean value) {
        return value ? LONG_TRUE : LONG_FALSE;
    }

    /**
     * boolean值转为Long
     *
     * @param value Boolean值
     * @return Long值
     */
    public static Long toLongObj(boolean value) {
        return toLong(value);
    }


    /**
     * boolean值转为float
     *
     * @param value Boolean值
     * @return float值
     */
    public static float toFloat(boolean value) {
        return (float) toInt(value);
    }

    /**
     * boolean值转为Float
     *
     * @param value Boolean值
     * @return float值
     */
    public static Float toFloatObj(boolean value) {
        return toFloat(value);
    }

    /**
     * boolean值转为double
     *
     * @param value Boolean值
     * @return double值
     */
    public static double toDouble(boolean value) {
        return toInt(value);
    }

    /**
     * boolean值转为double
     *
     * @param value Boolean值
     * @return double值
     */
    public static Double toDoubleObj(boolean value) {
        return toDouble(value);
    }

    /**
     * boolean值转为char
     *
     * @param value Boolean值
     * @return char值
     */
    public static char toChar(boolean value) {
        return (char) toInt(value);
    }

    /**
     * boolean值转为Character
     *
     * @param value Boolean值
     * @return Character值
     */
    public static Character toCharacter(boolean value) {
        return toChar(value);
    }

    /**
     * 将boolean转换为字符串 {@code 'true'} 或者 {@code 'false'}.
     *
     * <pre>
     *   BooleanUtil.toStringTrueFalse(true)   = "true"
     *   BooleanUtil.toStringTrueFalse(false)  = "false"
     * </pre>
     *
     * @param bool Boolean值
     * @return {@code 'true'}, {@code 'false'}
     */
    public static String toStringTrueFalse(boolean bool) {
        return toString(bool, "true", "false");
    }

    /**
     * 将boolean转换为字符串 {@code 'on'} 或者 {@code 'off'}.
     *
     * <pre>
     *   BooleanUtil.toStringOnOff(true)   = "on"
     *   BooleanUtil.toStringOnOff(false)  = "off"
     * </pre>
     *
     * @param bool Boolean值
     * @return {@code 'on'}, {@code 'off'}
     */
    public static String toStringOnOff(boolean bool) {
        return toString(bool, "on", "off");
    }

    /**
     * 将boolean转换为字符串 {@code 'yes'} 或者 {@code 'no'}.
     *
     * <pre>
     *   BooleanUtil.toStringYesNo(true)   = "yes"
     *   BooleanUtil.toStringYesNo(false)  = "no"
     * </pre>
     *
     * @param bool Boolean值
     * @return {@code 'yes'}, {@code 'no'}
     */
    public static String toStringYesNo(boolean bool) {
        return toString(bool, "yes", "no");
    }

    /**
     * 将boolean转换为字符串
     *
     * <pre>
     *   BooleanUtil.toString(true, "true", "false")   = "true"
     *   BooleanUtil.toString(false, "true", "false")  = "false"
     * </pre>
     *
     * @param bool        Boolean值
     * @param trueString  当值为 {@code true}时返回此字符串, 可能为 {@code null}
     * @param falseString 当值为 {@code false}时返回此字符串, 可能为 {@code null}
     * @return 结果值
     */
    public static String toString(boolean bool, String trueString, String falseString) {
        return bool ? trueString : falseString;
    }

    /**
     * 对Boolean数组取与
     *
     * <pre>
     *   BooleanUtil.and(true, true)         = true
     *   BooleanUtil.and(false, false)       = false
     *   BooleanUtil.and(true, false)        = false
     *   BooleanUtil.and(true, true, false)  = false
     *   BooleanUtil.and(true, true, true)   = true
     * </pre>
     *
     * @param array {@code Boolean}数组
     * @return 取与为真返回{@code true}
     */
    public static boolean and(boolean... array) {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (final boolean element : array) {
            if (false == element) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对Boolean数组取与
     *
     * <pre>
     *   BooleanUtil.and(Boolean.TRUE, Boolean.TRUE)                 = Boolean.TRUE
     *   BooleanUtil.and(Boolean.FALSE, Boolean.FALSE)               = Boolean.FALSE
     *   BooleanUtil.and(Boolean.TRUE, Boolean.FALSE)                = Boolean.FALSE
     *   BooleanUtil.and(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)   = Boolean.TRUE
     *   BooleanUtil.and(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE) = Boolean.FALSE
     *   BooleanUtil.and(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)  = Boolean.FALSE
     * </pre>
     *
     * @param array {@code Boolean}数组
     * @return 取与为真返回{@code true}
     */
    public static Boolean andOfWrap(Boolean... array) {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        final boolean[] primitive = Convert.convert(boolean[].class, array);
        return and(primitive);
    }

    /**
     * 对Boolean数组取或
     *
     * <pre>
     *   BooleanUtil.or(true, true)          = true
     *   BooleanUtil.or(false, false)        = false
     *   BooleanUtil.or(true, false)         = true
     *   BooleanUtil.or(true, true, false)   = true
     *   BooleanUtil.or(true, true, true)    = true
     *   BooleanUtil.or(false, false, false) = false
     * </pre>
     *
     * @param array {@code Boolean}数组
     * @return 取或为真返回{@code true}
     */
    public static boolean or(boolean... array) {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (final boolean element : array) {
            if (element) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对Boolean数组取或
     *
     * <pre>
     *   BooleanUtil.or(Boolean.TRUE, Boolean.TRUE)                  = Boolean.TRUE
     *   BooleanUtil.or(Boolean.FALSE, Boolean.FALSE)                = Boolean.FALSE
     *   BooleanUtil.or(Boolean.TRUE, Boolean.FALSE)                 = Boolean.TRUE
     *   BooleanUtil.or(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)    = Boolean.TRUE
     *   BooleanUtil.or(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE)  = Boolean.TRUE
     *   BooleanUtil.or(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)   = Boolean.TRUE
     *   BooleanUtil.or(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE) = Boolean.FALSE
     * </pre>
     *
     * @param array {@code Boolean}数组
     * @return 取或为真返回{@code true}
     */
    public static Boolean orOfWrap(Boolean... array) {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        final boolean[] primitive = Convert.convert(boolean[].class, array);
        return or(primitive);
    }

    /**
     * 对Boolean数组取异或
     *
     * <pre>
     *   BooleanUtil.xor(true, true)   = false
     *   BooleanUtil.xor(false, false) = false
     *   BooleanUtil.xor(true, false)  = true
     *   BooleanUtil.xor(true, true)   = false
     *   BooleanUtil.xor(false, false) = false
     *   BooleanUtil.xor(true, false)  = true
     * </pre>
     *
     * @param array {@code boolean}数组
     * @return 如果异或计算为true返回 {@code true}
     */
    public static boolean xor(boolean... array) {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty");
        }

        boolean result = false;
        for (final boolean element : array) {
            result ^= element;
        }

        return result;
    }

    /**
     * 对Boolean数组取异或
     *
     * <pre>
     *   BooleanUtil.xor(new Boolean[] { Boolean.TRUE, Boolean.TRUE })   = Boolean.FALSE
     *   BooleanUtil.xor(new Boolean[] { Boolean.FALSE, Boolean.FALSE }) = Boolean.FALSE
     *   BooleanUtil.xor(new Boolean[] { Boolean.TRUE, Boolean.FALSE })  = Boolean.TRUE
     * </pre>
     *
     * @param array {@code Boolean} 数组
     * @return 异或为真取{@code true}
     */
    public static Boolean xorOfWrap(Boolean... array) {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        final boolean[] primitive = Convert.convert(boolean[].class, array);
        return xor(primitive);
    }

    /**
     * 给定类是否为Boolean或者boolean
     *
     * @param clazz 类
     * @return 是否为Boolean或者boolean
     * @since 4.5.2
     */
    public static boolean isBoolean(Class<?> clazz) {
        return (clazz == Boolean.class || clazz == boolean.class);
    }
}
