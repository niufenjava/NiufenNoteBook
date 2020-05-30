package io.niufen.common.util;

/**
 * Boolean类型相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/29
 * @time 21:52
 */
public class BooleanUtils {


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
     * 转换字符串为boolean值
     *
     * @param valueStr 字符串
     * @return boolean值
     */
    public static boolean toBoolean(String valueStr) {
        if (StringUtils.isNotBlank(valueStr)) {
            valueStr = valueStr.trim().toLowerCase();
            return ArrayUtils.contains(TRUE_ARRAY, valueStr);
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
     * boolean值转为float
     *
     * @param value Boolean值
     * @return float值
     */
    public static float toFloat(boolean value) {
        return (float) toInt(value);
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
     * boolean值转为char
     *
     * @param value Boolean值
     * @return char值
     */
    public static char toChar(boolean value) {
        return (char) toInt(value);
    }
}
