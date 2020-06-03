package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.IntConstants;
import io.niufen.common.constant.StringConstants;
import io.niufen.common.lang.Matcher;
import io.niufen.common.text.StrBuilder;
import io.niufen.common.text.StringFormatter;
import io.niufen.common.text.StrSpliter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author niufen
 * @date 2020/6/14 下午3:01
 */
public class StrUtil {
    public static final char C_SPACE = CharConstants.SPACE;
    public static final String EMPTY = "";
    public static final String UNDERLINE = "_";
    public static final char C_DELIM_START = CharUtil.DELIM_START;
    public static final String SPACE = " ";
    public static final char C_TAB = '	';;
    public static final char C_CR = CharConstants.CR;
    public static final char C_LF = CharConstants.LF;
    public static final char C_SLASH = CharConstants.FORWARD_SLASH;
    public static final char C_BACKSLASH = CharConstants.BACKWARD_SLASH;
    public static final char C_LEFT_BRACE = CharConstants.LEFT_BRACE;

    public static final int INDEX_NOT_FOUND = IntConstants.ONE_MINUS;

    public static final String EMPTY_JSON = "{}";
    public static final String NULL = "null";

    public static final String TAB = "	";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String DASHED = "-";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";
    public static final char C_COLON = ':';
    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp;";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_APOS = "&apos;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";


    // --------------------- Blank ---------------------
    // 为null
    // 为不可见字符（如空格）
    // ""

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
            if (!CharUtil.isBlankChar(cs.charAt(i))) {
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
        if (ArrayUtil.isEmpty(charSequences)) {
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
        if (ArrayUtil.isEmpty(charSequences)) {
            return true;
        }

        for (CharSequence str : charSequences) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    // --------------------- Empty ---------------------
    // 为null
    // ""

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
    public static String emptyIfNull(CharSequence str) {
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
     * 调用对象的toString方法，null会返回“null”
     *
     * @param obj 对象
     * @return 字符串
     * @since 4.1.3
     */
    public static String toString(Object obj) {
        return null == obj ? NULL : obj.toString();
    }
    /**
     * 如果字符串是 null 或者 ""，则返回指定的默认字符串，否则返回本身
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String emptyToDefault(CharSequence str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是 null 或者 空白字符，则返回指定的默认字符串，否则返回本身
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String blankToDefault(CharSequence str, String defaultStr) {
        return isBlank(str) ? defaultStr : str.toString();
    }

    /**
     * 当给定字符串为空字符串时，转换为 null。否则返回其本身
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(CharSequence str) {
        return isEmpty(str) ? null : str.toString();
    }

    /**
     * 是否包含空字符串
     *
     * @param charSequences 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(CharSequence... charSequences) {
        if (ArrayUtil.isEmpty(charSequences)) {
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
        if (ArrayUtil.isEmpty(charSequences)) {
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
     * 检查字符串是否为 null、"null"、"undefined"
     *
     * @param cs 被检查的字符串
     * @return true-字符串是为 null、"null"、"undefined"; false-取反
     */
    public static boolean isNullOrUndefined(CharSequence cs) {
        if (null == cs) {
            return true;
        }
        return isNullOrUndefinedStr(cs);
    }

    /**
     * 检查字符串是否为 null、""、"null"、"undefined"
     *
     * @param cs 被检查的字符串
     * @return true-字符串为null、""、"null"、"undefined"；false-取反
     */
    public static boolean isEmptyOrUndefined(CharSequence cs) {
        if (isEmpty(cs)) {
            return true;
        }
        return isNullOrUndefinedStr(cs);
    }

    /**
     * 检查字符串是否为 null、""、" "、"   "、"null"、"undefined"
     *
     * @param cs 被检查的字符串
     * @return true-字符串为 null、""、" "、"   "、"null"、"undefined"；false-取反
     */
    public static boolean isBlankOrUndefined(CharSequence cs) {
        if (isBlank(cs)) {
            return true;
        }
        return isNullOrUndefinedStr(cs);
    }

    /**
     * 检查被传入的字符串 是否为 "null" 或 "undefined"
     *
     * @param cs 被检查的字符串
     * @return TRUE-字符串为 "null" 或 "undefined"；FALSE-反
     */
    private static boolean isNullOrUndefinedStr(CharSequence cs) {
        String str = cs.toString().trim();
        return (StringConstants.NULL.equals(str) || StringConstants.UNDEFINED.equals(str));
    }

    // --------------------- Trim ---------------------

    /**
     * 除去头尾空白的字符串，如果原字串为 <code>null</code >，则返回 <code>null</code>
     * 注意，和 <code>String.trim</code> 不同，
     * 此方法使用 <code>CharUtils.isBlankChar</code> 来判定空白，
     * 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * @param cs 要处理的字符串
     * @return 除去头尾空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(CharSequence cs) {
        return (null == cs) ? null : trim(cs, IntConstants.ZERO);
    }

    /**
     * 给定字符串数组全部做去首尾空格处理
     *
     * @param strings 字符串数组
     */
    public static void trim(String[] strings) {
        if (null == strings) {
            return;
        }
        String str;

        for (int i = 0; i < strings.length; i++) {
            str = strings[i];
            if (null != str) {
                strings[i] = str.trim();
            }
        }
    }

    /**
     * 去除两边空白符后的字符串, 如果为null返回""
     *
     * @param cs 被处理的字符串
     * @return 去除两边空白符后的字符串, 如果为null返回""
     */
    public static String trimToEmpty(CharSequence cs) {
        return null == cs ? StringConstants.EMPTY : trim(cs);
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是{@code null}，返回<code>""</code>。
     *
     * @param cs 被处理的字符串
     * @return 去除两边空白符后的字符串, 如果为空返回null
     */
    public static String trimToNull(CharSequence cs) {

        final String trimStr = trim(cs);
        return StringConstants.EMPTY.equals(trimStr) ? null : trimStr;
    }

    /**
     * 除去字符串头部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * 注意，和<code>String.trim</code>不同，此方法使用<code>CharUtil.isBlankChar</code> 来判定空白，
     * 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * @param cs 被处理的字符串
     * @return 除去开头空白的字符串
     */
    public static String trimStart(CharSequence cs) {
        return trim(cs, IntConstants.ONE_MINUS);
    }

    /**
     * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * 注意，和<code>String.trim</code>不同，此方法使用<code>CharUtil.isBlankChar</code> 来判定空白，
     * 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * @param cs 被处理的字符串
     * @return 除去尾部空白的字符串
     */
    public static String trimEnd(CharSequence cs) {
        return trim(cs, IntConstants.ONE);
    }

    /**
     * 去除字符串头部或尾部或前后的空白符，如果为 null, 返回 null
     *
     * @param cs   要处理的字符串
     * @param mode -1 表示 trimStart; 0 表示 trim全部； 1 表示 trimEnd
     * @return 去除字符串头部或尾部或前后的空白符的字符串
     */
    public static String trim(CharSequence cs, int mode) {
        if (null == cs) {
            return null;
        }
        int length = cs.length();
        int start = 0;
        int end = length;

        // 如果是 trimStart，那么对 start 进行++
        if (mode <= 0) {
            while ((start < end) && CharUtil.isBlankChar(cs.charAt(start))) {
                start++;
            }
        }

        // 如果是 trimEnd，那么对 end 进行--
        if (mode >= 0) {
            while ((start < end) && CharUtil.isBlankChar(cs.charAt(end - 1))) {
                end--;
            }
        }

        if ((start > 0) || (end < length)) {
            return cs.toString().substring(start, end);
        }

        return cs.toString();
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

    // --------------------- With ---------------------

    /**
     * 字符串是否以给定字符开始
     * 如果被检查的字符串为 null 则返回false
     *
     * @param cs 被检查的字符串
     * @param c  字符
     * @return true-被检查的字符串以给定的字符开头；false-取反；如果被检查的字符串为 null 则返回false
     */
    public static boolean startWith(CharSequence cs, char c) {
        return null != cs && c == cs.charAt(IntConstants.ZERO);
    }

    /**
     * 是否以指定字符串开头
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param cs           被检查的字符串
     * @param prefix       开头字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return true-被检查的字符串以给定的字符串开头；false-取反；
     * 如果被检查的字符串和开头字符串都为 null，返回 true；否则返回 false
     */
    public static boolean startWith(CharSequence cs, CharSequence prefix, boolean isIgnoreCase) {
        if (null == cs || null == prefix) {
            return null == cs && null == prefix;
        }

        if (isIgnoreCase) {
            return cs.toString().toLowerCase().startsWith(prefix.toString().toLowerCase());
        } else {
            return cs.toString().startsWith(prefix.toString());
        }
    }

    /**
     * 是否以指定字符串开头
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param cs     被检查的字符串
     * @param prefix 开头字符串
     * @return true-被检查的字符串以给定的字符串开头；false-取反；
     * 如果被检查的字符串和开头字符串都为 null，返回 true；否则返回 false
     */
    public static boolean startWith(CharSequence cs, CharSequence prefix) {
        return startWith(cs, prefix, Boolean.FALSE);
    }

    /**
     * 是否以指定字符串开头，忽略大小写
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param cs     被检查的字符串
     * @param prefix 开头字符串
     * @return true-被检查的字符串以给定的字符串开头；false-取反；
     * 如果被检查的字符串和开头字符串都为 null，返回 true；否则返回 false
     */
    public static boolean startWithIgnoreCase(CharSequence cs, CharSequence prefix) {
        return startWith(cs, prefix, Boolean.TRUE);
    }

    /**
     * 给定字符串是否以任何一个字符串开始
     * 给定字符串和数组为空都返回false
     *
     * @param cs       被检查的字符串
     * @param prefixes 开头字符串数组或不定长参数
     * @return true-被检查的字符串以给定的字符串开头；false-取反；
     * 如果被检查的字符串和开头字符串都为 null，返回 false
     */
    public static boolean startWithAny(CharSequence cs, CharSequence... prefixes) {
        if (isEmpty(cs) || ArrayUtil.isEmpty(prefixes)) {
            return false;
        }

        for (CharSequence prefix : prefixes) {
            if (startWith(cs, prefix)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 字符串是否以给定字符结束
     * 如果被检查的字符串为 null 则返回false
     *
     * @param cs 被检查的字符串
     * @param c  字符
     * @return true-被检查的字符串以给定的字符结束；false-取反；如果被检查的字符串为 null 则返回false
     */
    public static boolean endWith(CharSequence cs, char c) {
        return null != cs && c == cs.charAt(cs.length() - IntConstants.ONE);
    }

    /**
     * 是否以指定字符串结尾
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param cs           被检查的字符串
     * @param suffix       结尾字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return true-被检查的字符串以给定的字符串开头；false-取反；
     * 如果被检查的字符串和开头字符串都为 null，返回 true；否则返回 false
     */
    public static boolean endWith(CharSequence cs, CharSequence suffix, boolean isIgnoreCase) {
        if (null == cs || null == suffix) {
            return null == cs && null == suffix;
        }

        if (isIgnoreCase) {
            return cs.toString().toLowerCase().endsWith(suffix.toString().toLowerCase());
        } else {
            return cs.toString().endsWith(suffix.toString());
        }
    }

    /**
     * 是否以指定字符串结尾
     * 如果给定的字符串和结尾字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param cs     被检查的字符串
     * @param suffix 即为字符串
     * @return true-被检查的字符串以给定的字符串结尾；false-取反；
     * 如果被检查的字符串和开头字符串都为 null，返回 true；否则返回 false
     */
    public static boolean endWith(CharSequence cs, CharSequence suffix) {
        return endWith(cs, suffix, Boolean.FALSE);
    }

    /**
     * 是否以指定字符串结尾，忽略大小写
     * 如果给定的字符串和结尾字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param cs     被检查的字符串
     * @param suffix 结尾字符串
     * @return true-被检查的字符串以给定的字符串结尾；false-取反；
     * 如果被检查的字符串和结尾字符串都为 null，返回 true；否则返回 false
     */
    public static boolean endWithIgnoreCase(CharSequence cs, CharSequence suffix) {
        return endWith(cs, suffix, Boolean.TRUE);
    }

    /**
     * 给定字符串是否以任何一个字符串结尾
     * 给定字符串和数组为空都返回false
     *
     * @param cs       被检查的字符串
     * @param suffixes 结尾字符串数组或不定长参数
     * @return true-被检查的字符串以给定的字符串结尾；false-取反；
     * 如果被检查的字符串和开即为符串都为 null，返回 false
     */
    public static boolean endWithAny(CharSequence cs, CharSequence... suffixes) {
        if (isEmpty(cs) || ArrayUtil.isEmpty(suffixes)) {
            return false;
        }

        for (CharSequence prefix : suffixes) {
            if (endWith(cs, prefix)) {
                return true;
            }
        }
        return false;
    }

    // --------------------- Contains ---------------------

    /**
     * 给定的字符串内存在搜索字符
     *
     * @param cs         被查找的字符串
     * @param searchChar 搜索字符
     * @return true-给定的字符串内存在字符; false-给定的字符串内不存在字符;
     */
    public static boolean contains(CharSequence cs, char searchChar) {
        return indexOf(cs, searchChar) > IntConstants.ONE_MINUS;
    }

    /**
     * 给定的字符串内存在搜索字符
     *
     * @param cs       被查找的字符串
     * @param searchCs 搜索字符串
     * @return true-给定的字符串内存在字符; false-给定的字符串内不存在字符;
     */
    public static boolean contains(CharSequence cs, CharSequence searchCs) {
        if (null == cs || null == searchCs) {
            return false;
        }
        return cs.toString().contains(searchCs);
    }

    /**
     * 查找指定字符串是否包含指定字符串数组中的任意一个字符串
     *
     * @param cs     指定字符串
     * @param csArgs 需要检查的字符串数组
     * @return true-否包含任意一个字符串; false-不否包含任意一个字符串
     */
    public static boolean containsAny(CharSequence cs, CharSequence... csArgs) {
        return null != getContainsStr(cs, csArgs);
    }

    /**
     * 查找指定字符串是否包含指定字符数组中的任意一个字符串
     *
     * @param cs     指定字符串
     * @param csArgs 需要检查的字符数组
     * @return true-否包含任意一个字符串; false-不否包含任意一个字符串
     */
    public static boolean containsAny(CharSequence cs, char... csArgs) {
        if (isNotEmpty(cs) && ArrayUtil.isNotEmpty(csArgs)) {
            for (char csArg : csArgs) {
                if (contains(cs, csArg)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 检查指定字符串中是否只包含给定的字符
     *
     * @param cs     指定字符串
     * @param csArgs 需要检查的字符数组
     * @return true-给定字符串的所有字符都包含在给定的字符数组中; false-只要有一个不在给定字符数组中就是false
     */
    public static boolean containsOnly(CharSequence cs, char... csArgs) {
        if (isEmpty(cs) || ArrayUtil.isEmpty(csArgs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!ArrayUtil.contains(csArgs, cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给定字符串是否包含空白符（空白符包括空格、制表符、全角空格和不间断空格）<br>
     * 如果给定字符串为null或者""，则返回false
     *
     * @param cs 被检查的字符串
     * @return true-包含空白符；false-不包含空白符
     */
    public static boolean containsBlank(CharSequence cs) {
        if (isBlank(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (CharUtil.isBlankChar(cs.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含特定字符，忽略大小写，如果给定两个参数都为<code>null</code>，返回true
     *
     * @param cs       被检测字符串
     * @param searchCs 被测试是否包含的字符串
     * @return 是否包含
     */
    public static boolean containsIgnoreCase(CharSequence cs, CharSequence searchCs) {
        if (null == cs) {
            return null == searchCs;
        }
        return cs.toString().toLowerCase().contains(searchCs.toString().toLowerCase());
    }


    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串<br>
     * 忽略大小写
     *
     * @param cs     指定字符串
     * @param csArgs 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     * @since 3.2.0
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... csArgs) {
        return null != getContainsStrIgnoreCase(cs, csArgs);
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串，如果包含返回找到的第一个字符串
     *
     * @param cs     指定字符串
     * @param csArgs 需要坚持的字符串数组
     * @return 被包含的第一个字符串
     */
    public static String getContainsStr(CharSequence cs, CharSequence... csArgs) {
        if (isEmpty(cs) || ArrayUtil.isEmpty(csArgs)) {
            return null;
        }
        for (CharSequence csArg : csArgs) {
            if (cs.toString().contains(csArg)) {
                return csArg.toString();
            }
        }
        return null;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串，如果包含返回找到的第一个字符串<br>
     * 忽略大小写
     *
     * @param cs     指定字符串
     * @param csArgs 需要坚持的字符串数组
     * @return 被包含的第一个字符串
     */
    public static String getContainsStrIgnoreCase(CharSequence cs, CharSequence... csArgs) {
        if (isEmpty(cs) || ArrayUtil.isEmpty(csArgs)) {
            return null;
        }
        for (CharSequence csArg : csArgs) {
            if (containsIgnoreCase(cs, csArg)) {
                return csArg.toString();
            }
        }
        return null;
    }

    // --------------------- remove ---------------------

    /**
     * 移除字符串中所有给定的字符串<br>
     * eg: removeAll("aa-bb-cc-dd","-") =>> aabbccdd
     *
     * @param str       字符串
     * @param removeStr 被移除的字符串
     * @return 移除后的字符串
     */
    public static String removeAll(CharSequence str, CharSequence removeStr) {
        if (isEmpty((str))) {
            return str(str);
        }
        return str.toString().replace(removeStr, StringConstants.EMPTY);
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str      字符串
     * @param charArgs 字符数组
     * @return 取出后的字符
     */
    public static String removeAll(CharSequence str, char... charArgs) {
        if (isEmpty(str) || ArrayUtil.isEmpty(charArgs)) {
            return str(str);
        }

        final int len = str.length();
        final StringBuilder stringBuilder = builder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!ArrayUtil.contains(charArgs, c)) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 去除所有换行符，包括：
     * 1. \r
     * 2. \n
     *
     * @param cs 字符串
     * @return 处理后的字符串
     */
    public static String removeAllLineBreaks(CharSequence cs) {
        return removeAll(cs, CharConstants.CR, CharConstants.LF);
    }

    /**
     * 去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix，则返回原字符串
     */
    public static String removePrefix(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix) || (prefix.length() > str.length())) {
            return str(str);
        }

        final String retStr = str.toString();

        if (startWith(retStr, prefix)) {
            return subSuf(str, prefix.length());
        }

        return retStr;
    }

    /**
     * 清理空白字符串
     *
     * @param str 被清理的字符串
     * @return 被清理的字符串
     */
    public static String removeBlank(CharSequence str) {
        if (str == null) {
            return null;
        }

        int len = str.length();
        final StringBuilder sb = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!CharUtil.isBlankChar(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 忽略大小写去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix，则返回原字符串
     */
    public static String removePrefixIgnoreCase(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix) || (prefix.length() > str.length())) {
            return str(str);
        }

        final String retStr = str.toString();

        if (startWithIgnoreCase(retStr, prefix)) {
            return subSuf(str, prefix.length());
        }

        return retStr;

    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若前缀不是 prefix，则返回原字符串
     */
    public static String removeSuffix(CharSequence str, CharSequence suffix) {
        if (isEmpty(str) || isEmpty(suffix) || (suffix.length() > str.length())) {
            return str(str);
        }

        final String retStr = str.toString();

        if (endWith(retStr, suffix)) {
            int toIndex = retStr.length() - suffix.length();
            return subPre(str, toIndex);
        }
        return retStr;
    }

    /**
     * 忽略大小写去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若前缀不是 prefix，则返回原字符串
     */
    public static String removeSuffixIgnoreCase(CharSequence str, CharSequence suffix) {
        if (isEmpty(str) || isEmpty(suffix) || (suffix.length() > str.length())) {
            return str(str);
        }

        final String retStr = str.toString();

        if (endWithIgnoreCase(retStr, suffix)) {
            int toIndex = retStr.length() - suffix.length();
            return subPre(str, toIndex);
        }
        return retStr;

    }

    // --------------------- upper & lower ---------------------

    /**
     * 大写首字母
     * 例如：cs = name, return Name
     *
     * @param cs 字符串
     * @return 字符串
     */
    public static String upperFirst(CharSequence cs) {
        if (null == cs) {
            return null;
        }
        if (cs.length() > 0) {
            char first = cs.charAt(IntConstants.ZERO);
            if (Character.isLowerCase(first)) {
                return Character.toUpperCase(first) + subSuf(cs.toString(), IntConstants.ONE);
            }
        }
        return cs.toString();
    }

    /**
     * 小写首字母
     * 例如：cs = name, return Name
     *
     * @param cs 字符串
     * @return 字符串
     */
    public static String lowerFirst(CharSequence cs) {
        if (null == cs) {
            return null;
        }
        if (cs.length() > 0) {
            char first = cs.charAt(IntConstants.ZERO);
            if (Character.isUpperCase(first)) {
                return Character.toLowerCase(first) + subSuf(cs.toString(), IntConstants.ONE);
            }
        }
        return cs.toString();
    }

    // --------------------- remove & upper & lower ---------------------

    /**
     * 去掉首部指定长度的字符串并将剩余的字符串首字母小写<br>
     * 如果字符串为空，返回 null
     * 如果字符串长度小于 preLength 则直接返回该字符串
     * 例如：
     * cs = setName , preLength = 3 => return name
     * cs = setUserName , preLength = 3 => return userName
     *
     * @param cs        被处理的字符串
     * @param preLength 去掉的开头长度
     * @return 处理后的字符串
     */
    public static String removePreAndLowerFirst(CharSequence cs, int preLength) {
        if (null == cs) {
            return null;
        }
        int length = cs.length();
        if (cs.length() > preLength) {
            char first = Character.toLowerCase(cs.toString().charAt(preLength));
            int subIndex = preLength + IntConstants.ONE;
            if (length > subIndex) {
                return first + subSuf(cs, subIndex);
            } else {
                return String.valueOf(first);
            }
        } else {
            return cs.toString();
        }
    }

    /**
     * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br>
     * 例如：str=setName, prefix=set =》 return name
     *
     * @param cs     被处理的字符串
     * @param prefix 前缀
     * @return 处理后的字符串，不符合规范返回null
     */
    public static String removePreAndLowerFirst(CharSequence cs, CharSequence prefix) {
        if (null == cs || null == prefix) {
            return str(cs);
        }
        return lowerFirst(removePrefix(cs, prefix));
    }

    /**
     * 原字符串首字母大写并在其首部添加指定字符串
     * 例如：str=name, preString=get =》 return getName
     *
     * @param cs     被处理的字符串
     * @param prefix 添加的首部
     * @return 处理后的字符串
     */
    public static String upperFirstAndAddPrefix(CharSequence cs, CharSequence prefix) {
        if (null == cs || null == prefix) {
            return str(cs);
        }
        return prefix + upperFirst(cs);
    }

    /**
     * 移除指定的后缀，并前首字母小写
     *
     * @param cs     被处理的字符串
     * @param suffix 后缀
     * @return 处理后的字符串
     */
    public static String removeSuffixAndLowerFirst(CharSequence cs, CharSequence suffix) {
        if (null == cs || null == suffix) {
            return str(cs);
        }
        return lowerFirst(removeSuffix(cs, suffix));
    }

    /**
     * 获得set或get或is方法对应的标准属性名<br>
     * 例如：setName 返回 name
     * 若无法匹配，则返回 null
     *
     * <pre>
     * getName =》name
     * setName =》name
     * </pre>
     *
     * @param getOrSetMethodName Get或Set方法名
     * @return 如果是set或get方法名，返回field， 否则null
     */
    public static String getGeneralField(CharSequence getOrSetMethodName) {
        if (isBlank(getOrSetMethodName)) {
            return str(getOrSetMethodName);
        }
        if (startWithAny(getOrSetMethodName, StringConstants.SET, StringConstants.GET)) {
            return removePreAndLowerFirst(getOrSetMethodName, 3);
        }
        return null;
    }

    /**
     * 生成set方法名
     * 例如：name 返回 setName
     *
     * @param fieldName 属性名
     * @return setXxx
     */
    public static String genSetter(CharSequence fieldName) {
        return upperFirstAndAddPrefix(fieldName, StringConstants.SET);
    }

    /**
     * 生成get方法名
     * 例如：name 返回 setName
     *
     * @param fieldName 属性名
     * @return setXxx
     */
    public static String genGetter(CharSequence fieldName) {
        return upperFirstAndAddPrefix(fieldName, StringConstants.GET);
    }

    // --------------------- strip ---------------------

    /**
     * 去除两边的指定字符串
     *
     * @param str            被处理的字符串
     * @param prefixOrSuffix 前缀或后缀
     * @return 处理后的字符串
     */
    public static String strip(CharSequence str, CharSequence prefixOrSuffix) {
        if (equals(str, prefixOrSuffix)) {
            return StringConstants.EMPTY;
        }
        return strip(str, prefixOrSuffix, prefixOrSuffix);
    }

    /**
     * 去除两边的指定字符串
     *
     * @param str    被处理的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 处理后的字符串
     */
    public static String strip(CharSequence str, CharSequence prefix, CharSequence suffix) {
        if (isEmpty(str)) {
            return str(str);
        }

        int from = 0;
        int to = str.length();

        String retStr = str.toString();
        if (startWith(retStr, prefix)) {
            from = prefix.length();
        }
        if (endWith(retStr, suffix)) {
            to -= suffix.length();
        }

        return retStr.substring(Math.min(from, to), Math.max(from, to));
    }

    /**
     * 去除两边的指定字符串，忽略大小写
     *
     * @param str            被处理的字符串
     * @param prefixOrSuffix 前缀或后缀
     * @return 处理后的字符串
     * @since 3.1.2
     */
    public static String stripIgnoreCase(CharSequence str, CharSequence prefixOrSuffix) {
        return stripIgnoreCase(str, prefixOrSuffix, prefixOrSuffix);
    }

    /**
     * 去除两边的指定字符串，忽略大小写
     *
     * @param str    被处理的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 处理后的字符串
     * @since 3.1.2
     */
    public static String stripIgnoreCase(CharSequence str, CharSequence prefix, CharSequence suffix) {
        if (isEmpty(str)) {
            return str(str);
        }
        int from = 0;
        int to = str.length();

        String str2 = str.toString();
        if (startWithIgnoreCase(str2, prefix)) {
            from = prefix.length();
        }
        if (endWithIgnoreCase(str2, suffix)) {
            to -= suffix.length();
        }
        return str2.substring(from, to);
    }

    // --------------------- add prefix or suffix ---------------------

    /**
     * 如果给定字符串不是以prefix开头的，在开头补充 prefix
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 补充后的字符串
     */
    public static String addPrefixIfNot(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str(str);
        }

        final String str2 = str.toString();
        final String prefix2 = prefix.toString();
        if (!str2.startsWith(prefix2)) {
            return prefix2.concat(str2);
        }
        return str2;
    }

    /**
     * 如果给定字符串不是以suffix结尾的，在尾部补充 suffix
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 补充后的字符串
     */
    public static String addSuffixIfNot(CharSequence str, CharSequence suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str(str);
        }

        final String str2 = str.toString();
        final String suffix2 = suffix.toString();
        if (!str2.endsWith(suffix2)) {
            return str2.concat(suffix2);
        }
        return str2;
    }

    // --------------------- split ---------------------


    // --------------------- indexOf ---------------------

    /**
     * 在给定字符串内查询字符，若存在则返回位置，若不存在返回 -1
     *
     * @param cs         给定字符串
     * @param searchChar 被查找的字符
     * @return 位置
     */
    public static int indexOf(final CharSequence cs, char searchChar) {
        return indexOf(cs, searchChar, IntConstants.ZERO);
    }

    /**
     * 在给定字符串，给定位置处查询字符，若存在则返回位置，若不存在返回 -1
     *
     * @param cs         给定字符串
     * @param searchChar 被查找的字符
     * @param start      起始位置
     * @return 位置
     */
    public static int indexOf(final CharSequence cs, char searchChar, int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar, start);
        } else {
            return indexOf(cs, searchChar, start, IntConstants.ONE_MINUS);
        }
    }

    /**
     * 指定范围内查找字符串，忽略大小写<br>
     *
     * <pre>
     * StrUtil.indexOfIgnoreCase(null, *, *)          = -1
     * StrUtil.indexOfIgnoreCase(*, null, *)          = -1
     * StrUtil.indexOfIgnoreCase("", "", 0)           = 0
     * StrUtil.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StrUtil.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StrUtil.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StrUtil.indexOfIgnoreCase("abc", "", 9)        = -1
     * </pre>
     *
     * @param str       字符串
     * @param searchStr 需要查找位置的字符串
     * @return 位置
     * @since 3.2.1
     */
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    /**
     * 指定范围内查找字符串
     *
     * <pre>
     * StrUtil.indexOfIgnoreCase(null, *, *)          = -1
     * StrUtil.indexOfIgnoreCase(*, null, *)          = -1
     * StrUtil.indexOfIgnoreCase("", "", 0)           = 0
     * StrUtil.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StrUtil.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StrUtil.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StrUtil.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StrUtil.indexOfIgnoreCase("abc", "", 9)        = -1
     * </pre>
     *
     * @param str       字符串
     * @param searchStr 需要查找位置的字符串
     * @param fromIndex 起始位置
     * @return 位置
     * @since 3.2.1
     */
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int fromIndex) {
        return indexOf(str, searchStr, fromIndex, true);
    }
    /**
     * 在给定字符串指定范围内查找指定字符的位置
     *
     * @param cs         给定字符串
     * @param searchChar 被查找的字符
     * @param start      起始位置
     * @param end        结束为止
     * @return 位置
     */
    public static int indexOf(final CharSequence cs, char searchChar, int start, int end) {
        if (null == cs) {
            return IntConstants.ONE_MINUS;
        }
        int length = cs.length();
        if (start < IntConstants.ZERO || start > length) {
            start = IntConstants.ZERO;
        }
        if (end > length || end < IntConstants.ZERO) {
            end = length;
        }
        for (int i = start; i < end; i++) {
            if (cs.charAt(i) == searchChar) {
                return i;
            }
        }
        return IntConstants.ONE_MINUS;
    }

    /**
     * 指定范围内查找字符串
     *
     * @param str        被搜索的字符串
     * @param searchStr  需要查找位置的字符串
     * @param fromIndex  起始位置
     * @param ignoreCase 是否忽略大小写
     * @return 位置
     */
    public static int indexOf(final CharSequence str, CharSequence searchStr, int fromIndex, boolean ignoreCase) {
        if (null == str || null == searchStr) {
            return INDEX_NOT_FOUND;
        }

        if (fromIndex < 0) {
            fromIndex = 0;
        }
        final int endLimit = str.length() - searchStr.length() + 1;
        if (fromIndex > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return fromIndex;
        }
        if (!ignoreCase) {
            // 不忽略大小写调用JDK方法
            return str.toString().indexOf(searchStr.toString(), fromIndex);
        }

        for (int i = fromIndex; i < endLimit; i++) {
            if (isSubEqual(str, i, searchStr, IntConstants.ZERO, searchStr.length(), Boolean.TRUE)) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }

    // --------------------- sub ---------------------


    /**
     * 切割指定位置之前部分的字符串
     *
     * @param string  字符串
     * @param toIndex 切割到的位置（不包括）
     * @return 切割后的剩余的前半部分字符串
     */
    public static String subPre(CharSequence string, int toIndex) {
        return sub(string, IntConstants.ZERO, toIndex);
    }


    /**
     * 截取分隔字符串之前的字符串，不包括分隔字符串<br>
     * 如果给定的字符串为空串（null或""）或者分隔字符串为null，返回原字符串<br>
     * 如果分隔字符串为空串""，则返回空串，如果分隔字符串未找到，返回原字符串，举例如下：
     *
     * <pre>
     * StrUtil.subBefore(null, *)      = null
     * StrUtil.subBefore("", *)        = ""
     * StrUtil.subBefore("abc", "a")   = ""
     * StrUtil.subBefore("abcba", "b") = "a"
     * StrUtil.subBefore("abc", "c")   = "ab"
     * StrUtil.subBefore("abc", "d")   = "abc"
     * StrUtil.subBefore("abc", "")    = ""
     * StrUtil.subBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param string          被查找的字符串
     * @param separator       分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true为选取最后一个
     * @return 切割后的字符串
     * @since 3.1.1
     */
    public static String subBefore(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string) || separator == null) {
            return null == string ? null : string.toString();
        }

        final String str = string.toString();
        final String sep = separator.toString();
        if (sep.isEmpty()) {
            return StringConstants.EMPTY;
        }
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (INDEX_NOT_FOUND == pos) {
            return str;
        }
        if (0 == pos) {
            return StringConstants.EMPTY;
        }
        return str.substring(0, pos);
    }
    /**
     * 切割指定位置之后部分的字符串
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后后剩余的后半部分字符串
     */
    public static String subSuf(CharSequence string, int fromIndex) {
        if (isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 改进JDK字符串截取<br/>
     * index 从0开始计算，最后一个字符为 -1
     * 如果 from 和 to 的位置一样，返回 ""
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str       被截取的字符串
     * @param fromIndex 开始的index (包括)
     * @param toIndex   结束的index (不包括)
     * @return 截取后的字符串
     */
    public static String sub(CharSequence str, int fromIndex, int toIndex) {
        if (isEmpty(str)) {
            return str(str);
        }
        int len = str.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return StringConstants.EMPTY;
        }
        return str.toString().substring(fromIndex, toIndex);
    }

    // --------------------- toString ---------------------

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return String
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }


    // --------------------- builder ---------------------

    public static StringBuilder builder(){
        return new StringBuilder();
    }

    /**
     * 创建 StringBuilder 对象
     *
     * @param capacity 初始大小
     * @return StringBuilder 对象
     */
    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }
    /**
     * 创建StringBuilder对象
     *
     * @param strs 初始字符串列表
     * @return StringBuilder对象
     */
    public static StringBuilder builder(CharSequence... strs) {
        final StringBuilder sb = new StringBuilder();
        for (CharSequence str : strs) {
            sb.append(str);
        }
        return sb;
    }

    /**
     * 创建StrBuilder对象
     *
     * @return StrBuilder对象
     * @since 4.0.1
     */
    public static StrBuilder strBuilder() {
        return StrBuilder.create();
    }

    // --------------------- equals ---------------------

    /**
     * 比较两个字符串（大小写敏感）。
     *
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 比较两个字符串是否相等。
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都是 null 猜想等
            return str2 == null;
        }

        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }
        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }


    /**
     * 比较两个字符串（大小写不敏感）。
     *
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 截取两个字符串的不同部分（长度一致），判断截取的子串是否相同<br>
     * 任意一个字符串为 null 返回 false
     *
     * @param str1       第一个字符串
     * @param start1     第一个字符串开始的位置
     * @param str2       第二个字符串
     * @param start2     第二个字符串开始的位置
     * @param length     截取长度
     * @param ignoreCase 是否忽略大小写
     */
    public static boolean isSubEqual(CharSequence str1, int start1, CharSequence str2, int start2, int length, boolean ignoreCase) {
        if (null == str1 || null == str2) {
            return false;
        }
        // 使用 JDK 的 regionMatches 区域匹配方法
        return str1.toString().regionMatches(ignoreCase, start1, str2.toString(), start2, length);
    }



    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String utf8Str(Object obj) {
        return str(obj, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 将对象转为字符串
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.toString(obj);
        }

        return obj.toString();
    }


    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return null;
        }
        if (ArrayUtil.isEmpty(params) || isBlank(template)) {
            return template.toString();
        }
        return StringFormatter.format(template.toString(), params);
    }


    /**
     * 编码字符串，编码为UTF-8
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] utf8Bytes(CharSequence str) {
        return bytes(str, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 编码字符串<br>
     * 使用系统默认编码
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str) {
        return bytes(str, Charset.defaultCharset());
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, String charset) {
        return bytes(str, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, Charset charset) {
        if (str == null) {
            return null;
        }

        if (null == charset) {
            return str.toString().getBytes();
        }
        return str.toString().getBytes(charset);
    }

    /**
     * 包装指定字符串<br>
     * 当前缀和后缀一致时使用此方法
     *
     * @param str             被包装的字符串
     * @param prefixAndSuffix 前缀和后缀
     * @return 包装后的字符串
     * @since 3.1.0
     */
    public static String wrap(CharSequence str, CharSequence prefixAndSuffix) {
        return wrap(str, prefixAndSuffix, prefixAndSuffix);
    }

    /**
     * 包装指定字符串
     *
     * @param str    被包装的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 包装后的字符串
     */
    public static String wrap(CharSequence str, CharSequence prefix, CharSequence suffix) {
        return nullToEmpty(prefix).concat(nullToEmpty(str)).concat(nullToEmpty(suffix));
    }

    /**
     * 包装多个字符串
     *
     * @param prefixAndSuffix 前缀和后缀
     * @param strs            多个字符串
     * @return 包装的字符串数组
     * @since 4.0.7
     */
    public static String[] wrapAll(CharSequence prefixAndSuffix, CharSequence... strs) {
        return wrapAll(prefixAndSuffix, prefixAndSuffix, strs);
    }

    /**
     * 包装多个字符串
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @param strs   多个字符串
     * @return 包装的字符串数组
     * @since 4.0.7
     */
    public static String[] wrapAll(CharSequence prefix, CharSequence suffix, CharSequence... strs) {
        final String[] results = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            results[i] = wrap(strs[i], prefix, suffix);
        }
        return results;
    }

    /**
     * 包装指定字符串，如果前缀或后缀已经包含对应的字符串，则不再包装
     *
     * @param str    被包装的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 包装后的字符串
     */
    public static String wrapIfMissing(CharSequence str, CharSequence prefix, CharSequence suffix) {
        int len = 0;
        if (isNotEmpty(str)) {
            len += str.length();
        }
        if (isNotEmpty(prefix)) {
            len += str.length();
        }
        if (isNotEmpty(suffix)) {
            len += str.length();
        }
        StringBuilder sb = new StringBuilder(len);
        if (isNotEmpty(prefix) && false == startWith(str, prefix)) {
            sb.append(prefix);
        }
        if (isNotEmpty(str)) {
            sb.append(str);
        }
        if (isNotEmpty(suffix) && false == endWith(str, suffix)) {
            sb.append(suffix);
        }
        return sb.toString();
    }

    /**
     * 包装多个字符串，如果已经包装，则不再包装
     *
     * @param prefixAndSuffix 前缀和后缀
     * @param strs            多个字符串
     * @return 包装的字符串数组
     * @since 4.0.7
     */
    public static String[] wrapAllIfMissing(CharSequence prefixAndSuffix, CharSequence... strs) {
        return wrapAllIfMissing(prefixAndSuffix, prefixAndSuffix, strs);
    }

    /**
     * 包装多个字符串，如果已经包装，则不再包装
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @param strs   多个字符串
     * @return 包装的字符串数组
     * @since 4.0.7
     */
    public static String[] wrapAllIfMissing(CharSequence prefix, CharSequence suffix, CharSequence... strs) {
        final String[] results = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            results[i] = wrapIfMissing(strs[i], prefix, suffix);
        }
        return results;
    }

    /**
     * 去掉字符包装，如果未被包装则返回原字符串
     *
     * @param str    字符串
     * @param prefix 前置字符串
     * @param suffix 后置字符串
     * @return 去掉包装字符的字符串
     * @since 4.0.1
     */
    public static String unWrap(CharSequence str, String prefix, String suffix) {
        if (isWrap(str, prefix, suffix)) {
            return sub(str, prefix.length(), str.length() - suffix.length());
        }
        return str.toString();
    }

    /**
     * 去掉字符包装，如果未被包装则返回原字符串
     *
     * @param str    字符串
     * @param prefix 前置字符
     * @param suffix 后置字符
     * @return 去掉包装字符的字符串
     * @since 4.0.1
     */
    public static String unWrap(CharSequence str, char prefix, char suffix) {
        if (isEmpty(str)) {
            return str(str);
        }
        if (str.charAt(0) == prefix && str.charAt(str.length() - 1) == suffix) {
            return sub(str, 1, str.length() - 1);
        }
        return str.toString();
    }

    /**
     * 去掉字符包装，如果未被包装则返回原字符串
     *
     * @param str             字符串
     * @param prefixAndSuffix 前置和后置字符
     * @return 去掉包装字符的字符串
     * @since 4.0.1
     */
    public static String unWrap(CharSequence str, char prefixAndSuffix) {
        return unWrap(str, prefixAndSuffix, prefixAndSuffix);
    }

    /**
     * 指定字符串是否被包装
     *
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(CharSequence str, String prefix, String suffix) {
        if (ArrayUtil.hasNull(str, prefix, suffix)) {
            return false;
        }
        final String str2 = str.toString();
        return str2.startsWith(prefix) && str2.endsWith(suffix);
    }

    /**
     * 指定字符串是否被同一字符包装（前后都有这些字符串）
     *
     * @param str     字符串
     * @param wrapper 包装字符串
     * @return 是否被包装
     */
    public static boolean isWrap(CharSequence str, String wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    /**
     * 指定字符串是否被同一字符包装（前后都有这些字符串）
     *
     * @param str     字符串
     * @param wrapper 包装字符
     * @return 是否被包装
     */
    public static boolean isWrap(CharSequence str, char wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    /**
     * 指定字符串是否被包装
     *
     * @param str        字符串
     * @param prefixChar 前缀
     * @param suffixChar 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(CharSequence str, char prefixChar, char suffixChar) {
        if (null == str) {
            return false;
        }

        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }
    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(CharSequence name) {
        if (null == name) {
            return null;
        }

        String name2 = name.toString();
        if (name2.contains(UNDERLINE)) {
            final StringBuilder sb = new StringBuilder(name2.length());
            boolean upperCase = false;
            for (int i = 0; i < name2.length(); i++) {
                char c = name2.charAt(i);

                if (c == CharUtil.UNDERLINE) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name2;
        }
    }

    /**
     * 重复某个字符
     *
     * @param c     被重复的字符
     * @param count 重复的数目，如果小于等于0则返回""
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count) {
        if (count <= 0) {
            return EMPTY;
        }

        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }

    /**
     * 重复某个字符串
     *
     * @param str   被重复的字符
     * @param count 重复的数目
     * @return 重复字符字符串
     */
    public static String repeat(CharSequence str, int count) {
        if (null == str) {
            return null;
        }
        if (count <= 0) {
            return EMPTY;
        }
        if (count == 1 || str.length() == 0) {
            return str.toString();
        }

        // 检查
        final int len = str.length();
        final long longSize = (long) len * (long) count;
        final int size = (int) longSize;
        if (size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required String length is too large: " + longSize);
        }

        final char[] array = new char[size];
        str.toString().getChars(0, len, array, 0);
        int n;
        for (n = len; n < size - n; n <<= 1) {// n <<= 1相当于n *2
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 例如：
     *
     * <pre>
     * HelloWorld=》hello_world
     * Hello_World=》hello_world
     * HelloWorld_test=》hello_world_test
     * </pre>
     *
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     */
    public static String toUnderlineCase(CharSequence str) {
        return toSymbolCase(str, CharUtil.UNDERLINE);
    }

    /**
     * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     *
     * @param str    转换前的驼峰式命名的字符串，也可以为符号连接形式
     * @param symbol 连接符
     * @return 转换后符号连接方式命名的字符串
     * @since 4.0.10
     */
    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        }

        final int length = str.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
            if (Character.isUpperCase(c)) {
                // 遇到大写字母处理
                final Character nextChar = (i < str.length() - 1) ? str.charAt(i + 1) : null;
                if (null != preChar && Character.isUpperCase(preChar)) {
                    // 前一个字符为大写，则按照一个词对待
                    sb.append(c);
                } else if (null != nextChar && Character.isUpperCase(nextChar)) {
                    // 后一个为大写字母，按照一个词对待
                    if (null != preChar && symbol != preChar) {
                        // 前一个是非大写时按照新词对待，加连接符
                        sb.append(symbol);
                    }
                    sb.append(c);
                } else {
                    // 前后都为非大写按照新词对待
                    if (null != preChar && symbol != preChar) {
                        // 前一个非连接符，补充连接符
                        sb.append(symbol);
                    }
                    sb.append(Character.toLowerCase(c));
                }
            } else {
                if (sb.length() > 0 && Character.isUpperCase(sb.charAt(sb.length() - 1)) && symbol != c) {
                    // 当结果中前一个字母为大写，当前为小写，说明此字符为新词开始（连接符也表示新词）
                    sb.append(symbol);
                }
                // 小写或符号
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 以 conjunction 为分隔符将多个对象转换为字符串
     *
     * @param conjunction 分隔符
     * @param objs        数组
     * @return 连接后的字符串
     * @see ArrayUtil#join(Object, CharSequence)
     */
    public static String join(CharSequence conjunction, Object... objs) {
        return ArrayUtil.join(objs, conjunction);
    }
    /**
     * 字符串的每一个字符是否都与定义的匹配器匹配
     *
     * @param value   字符串
     * @param matcher 匹配器
     * @return 是否全部匹配
     * @since 3.2.3
     */
    public static boolean isAllCharMatch(CharSequence value, Matcher<Character> matcher) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        int len = value.length();
        boolean isAllMatch = true;
        for (int i = 0; i < len; i++) {
            isAllMatch &= matcher.match(value.charAt(i));
        }
        return isAllMatch;
    }
    /**
     * 统计指定内容中包含指定字符串的数量<br>
     * 参数为 {@code null} 或者 "" 返回 {@code 0}.
     *
     * <pre>
     * StrUtil.count(null, *)       = 0
     * StrUtil.count("", *)         = 0
     * StrUtil.count("abba", null)  = 0
     * StrUtil.count("abba", "")    = 0
     * StrUtil.count("abba", "a")   = 2
     * StrUtil.count("abba", "ab")  = 1
     * StrUtil.count("abba", "xxx") = 0
     * </pre>
     *
     * @param content      被查找的字符串
     * @param strForSearch 需要查找的字符串
     * @return 查找到的个数
     */
    public static int count(CharSequence content, CharSequence strForSearch) {
        if (hasEmpty(content, strForSearch) || strForSearch.length() > content.length()) {
            return 0;
        }

        int count = 0;
        int idx = 0;
        final String content2 = content.toString();
        final String strForSearch2 = strForSearch.toString();
        while ((idx = content2.indexOf(strForSearch2, idx)) > -1) {
            count++;
            idx += strForSearch.length();
        }
        return count;
    }
    /**
     * 统计指定内容中包含指定字符的数量
     *
     * @param content       内容
     * @param charForSearch 被统计的字符
     * @return 包含数量
     */
    public static int count(CharSequence content, char charForSearch) {
        int count = 0;
        if (isEmpty(content)) {
            return 0;
        }
        int contentLength = content.length();
        for (int i = 0; i < contentLength; i++) {
            if (charForSearch == content.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     * @since 3.1.2
     */
    public static List<String> splitTrim(CharSequence str, char separator) {
        return splitTrim(str, separator, -1);
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     * @since 3.2.0
     */
    public static List<String> splitTrim(CharSequence str, CharSequence separator) {
        return splitTrim(str, separator, -1);
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     * @since 3.1.0
     */
    public static List<String> splitTrim(CharSequence str, char separator, int limit) {
        return split(str, separator, limit, true, true);
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     * @since 3.2.0
     */
    public static List<String> splitTrim(CharSequence str, CharSequence separator, int limit) {
        return split(str, separator, limit, true, true);
    }

    /**
     * 切分字符串，不限制分片数量
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static List<String> split(CharSequence str, char separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, 0, isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static List<String> split(CharSequence str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (null == str) {
            return new ArrayList<>(0);
        }
        return StrSpliter.split(str.toString(), separator, limit, isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 3.2.0
     */
    public static List<String> split(CharSequence str, CharSequence separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (null == str) {
            return new ArrayList<>(0);
        }
        final String separatorStr = (null == separator) ? null : separator.toString();
        return StrSpliter.split(str.toString(), separatorStr, limit, isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串，如果分隔符不存在则返回原字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @return 字符串
     */
    public static String[] split(CharSequence str, CharSequence separator) {
        if (str == null) {
            return new String[]{};
        }

        final String separatorStr = (null == separator) ? null : separator.toString();
        return StrSpliter.splitToArray(str.toString(), separatorStr, 0, false, false);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的数组
     */
    public static String[] splitToArray(CharSequence str, char separator) {
        return splitToArray(str, separator, 0);
    }
    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数
     * @return 切分后的数组
     */
    public static String[] splitToArray(CharSequence str, char separator, int limit) {
        if (null == str) {
            return new String[]{};
        }
        return StrSpliter.splitToArray(str.toString(), separator, limit, false, false);
    }

    /**
     * 根据给定长度，将给定字符串截取为多个部分
     *
     * @param str 字符串
     * @param len 每一个小节的长度
     * @return 截取后的字符串数组
     * @see StrSpliter#splitByLength(String, int)
     */
    public static String[] split(CharSequence str, int len) {
        if (null == str) {
            return new String[]{};
        }
        return StrSpliter.splitByLength(str.toString(), len);
    }

    /**
     * 切分字符串<br>
     * a#b#c =》 [a,b,c] <br>
     * a##b#c =》 [a,"",b,c]
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> split(CharSequence str, char separator) {
        return split(str, separator, 0);
    }
    /**
     * 切分字符串，不去除切分后每个元素两边的空白符，不去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     */
    public static List<String> split(CharSequence str, char separator, int limit) {
        return split(str, separator, limit, false, false);
    }
    /**
     * 原字符串首字母大写并在其首部添加指定字符串 例如：str=name, preString=get =》 return getName
     *
     * @param str       被处理的字符串
     * @param preString 添加的首部
     * @return 处理后的字符串
     */
    public static String upperFirstAndAddPre(CharSequence str, String preString) {
        if (str == null || preString == null) {
            return null;
        }
        return preString + upperFirst(str);
    }

}
