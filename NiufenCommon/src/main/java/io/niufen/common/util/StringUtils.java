package io.niufen.common.util;

import io.niufen.common.constant.IntConstants;
import io.niufen.common.constant.StringConstants;

/**
 * 字符串工具类
 *
 * @author niufen
 * @date 2020/6/14 下午3:01
 */
public class StringUtils {

    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param cs 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence cs) {
        int length;
        if (null == cs) {
            return true;
        }
        if ((length = cs.length()) == IntConstants.ZERO) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtils.isBlankChar(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 如果对象时字符串，是否为空字符，空白的定义如下：
     * 1、null
     * 2、不可见字符，如空格
     * 3、""
     *
     * @param obj 对象
     * @return 是否为空
     */
    public static boolean isBlankIfStr(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return isBlank((CharSequence) obj);
        }
        return false;
    }

    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param cs 被检测的字符串
     * @return 是否为非空白
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断字符串列表 是否包含空字符串
     *
     * @param charSequences 字符串列表
     * @return TRUE-包含空字符串；FALSE-不包含空字符串
     */
    public static boolean hasBlank(CharSequence... charSequences) {
        // 如果数组为空，返回 TRUE
        if (ArrayUtils.isEmpty(charSequences)) {
            return true;
        }
        for (CharSequence charSequence : charSequences) {
            if (isBlank(charSequence)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 给定所有字符串是否为空白
     *
     * @param charSequences 字符串列表
     * @return 所有字符串是否为空白
     */
    public static boolean isAllBlank(CharSequence... charSequences) {
        if (ArrayUtils.isEmpty(charSequences)) {
            return true;
        }

        for (CharSequence str : charSequences) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空且长度为0 空的定义如下:
     * 1、为null <br>
     * 2、为""<br>
     *
     * @param str 字符串
     * @return 为空或0返回true
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 如果对象是字符串是否为空串空的定义如下:<br>
     * 1、为null <br>
     * 2、为""<br>
     *
     * @param obj 对象
     * @return 如果为字符串是否为空串
     */
    public static boolean isEmptyIfStr(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return 0 == ((CharSequence) obj).length();
        }
        return false;
    }

    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 当给定的字符串为 null 时，转换为 EMPTY ""
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyIfNull(CharSequence str){
        return nullToEmpty(str);
    }

    /**
     * 当给定的字符串为 null 时，转换为 EMPTY ""
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(CharSequence str) {
        return nullToDefault(str, StringConstants.EMPTY);
    }

    /**
     * 如果字符串是<code>null</code>，则返回指定默认字符串，否则返回字符串本身。
     *
     * @param str        要替换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(CharSequence str, String defaultStr) {
        return (str == null) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是 null 或者 ""，则返回指定的默认字符串，否则返回本身
     * @param str  要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String emptyToDefault(CharSequence str, String defaultStr){
        return isEmpty(str) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是 null 或者 空白字符，则返回指定的默认字符串，否则返回本身
     * @param str  要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String blankToDefault(CharSequence str, String defaultStr){
        return isBlank(str) ? defaultStr : str.toString();
    }

    /**
     * 当给定字符串为空字符串时，转换为 null。否则返回其本身
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(CharSequence str){
        return isEmpty(str) ? null : str.toString();
    }

    /**
     * 是否包含空字符串
     *
     * @param charSequences 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(CharSequence... charSequences) {
        if (ArrayUtils.isEmpty(charSequences)) {
            return true;
        }

        for (CharSequence str : charSequences) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全部为空字符串
     *
     * @param charSequences 字符串列表
     * @return 是否全部为空字符串
     */
    public static boolean isAllEmpty(CharSequence... charSequences) {
        if (ArrayUtils.isEmpty(charSequences)) {
            return true;
        }

        for (CharSequence str : charSequences) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 驼峰模式字符串转换为下划线字符串
     *
     * @param camelStr 驼峰字符串
     * @return 下划线字符串
     */
    public static String camel2Underscore(String camelStr) {
        return convertCamel(camelStr, '_');
    }

    /**
     * 转换驼峰字符串为指定分隔符的字符串 <br/>
     * 如：camelStr:"UserInfo"    separator:'_' <br/>
     * return "user_info"
     *
     * @param camelStr  驼峰字符串
     * @param separator 分隔符
     * @return 将驼峰字符串转换后的字符串
     */
    public static String convertCamel(String camelStr, char separator) {
        if (isEmpty(camelStr)) {
            return camelStr;
        }
        StringBuilder out = new StringBuilder();
        char[] strChar = camelStr.toCharArray();
        for (int i = 0, len = strChar.length; i < len; i++) {
            char c = strChar[i];
            if (Character.isUpperCase(c)) {
                //如果不是首字符，则需要添加分隔符
                if (i != 0) {
                    out.append(separator);
                }
                out.append(Character.toLowerCase(c));
                continue;
            }
            out.append(c);
        }
        return out.toString();
    }
}
