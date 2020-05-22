package io.niufen.common.util;

/**
 * 字符串相关工具类
 * @author haijun.zhang
 * @date 2020/4/28
 * @time 15:44
 */
public class MyStringUtils {

    /**
     * <p>检查字符串是否为 ""空字符，null 或 " "空格</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  待检查字符串，可以为 null
     * @return true-字符串是否为 ""空字符，null 或 " "空格; false-!true
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs){
        return !isBlank(cs);
    }


    /**
     * 获取字符序列的长度，如果字符序列为 null ，那么返回 0
     *
     * @param cs
     *            字符序列 或 null
     * @return 字符序列长度 或 0 如果 字符序列为 null
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
}
