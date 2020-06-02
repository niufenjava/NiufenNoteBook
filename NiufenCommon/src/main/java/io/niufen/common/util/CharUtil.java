package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.IntConstants;
import io.niufen.common.text.ASCIIStrCache;

/**
 * 字符相关工具类
 * char 类型是一个单一的 16 为 Unicode 字符
 * 最小 0 最大 65535
 * char 类型可以存储任何字符。
 *
 * @author haijun.zhang
 * @date 2020/5/25
 * @time 17:43
 */
public class CharUtil {


    public static final char SPACE = ' ';
    public static final char TAB = '	';
    public static final char DOT = '.';
    public static final char SLASH = '/';
    public static final char BACKSLASH = '\\';
    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char UNDERLINE = '_';
    public static final char DASHED = '-';
    public static final char COMMA = ',';
    public static final char DELIM_START = '{';
    public static final char DELIM_END = '}';
    public static final char BRACKET_START = '[';
    public static final char BRACKET_END = ']';
    public static final char COLON = ':';
    public static final char DOUBLE_QUOTES = '"';
    public static final char SINGLE_QUOTE = '\'';
    public static final char AMP = '&';

    /**
     * 是否为 ASCII 字符，ASCII 字符谓语 0~127 之间
     * <pre>
     *     CharUtil.isAscii('a')  = true
     *     CharUtil.isAscii('A')  = true
     *     CharUtil.isAscii('3')  = true
     *     CharUtil.isAscii('-')  = true
     *     CharUtil.isAscii('\n') = true
     *     CharUtil.isAscii('&copy;') = false
     * </pre>
     *
     * @param c 被检查的字符
     * @return true-ASCII字符； false-非ASCII字符
     */
    public static boolean isAscii(char c) {
        return c < 128;
    }

    /**
     * 是否为可见ASCII字符，可见字符位于32~126之间
     * <pre>
     *     CharUtil.isAscii('a')  = true
     *     CharUtil.isAscii('A')  = true
     *     CharUtil.isAscii('3')  = true
     *     CharUtil.isAscii('-')  = true
     *     CharUtil.isAscii('\n') = false
     *     CharUtil.isAscii('&copy;') = false
     * </pre>
     * 32 空格
     * 127 DEL (delete)
     *
     * @param c 被检查的字符处
     * @return true: 表示为ASCII可见字符；false：表示非可见字符 0~31 和 127
     */
    public static boolean isAsciiPrintable(char c) {
        return c >= 32 && c < 127;
    }


    /**
     * 是否为 ASCII控制符（不可见字符），控制符位于0~31和127
     * 32 空格
     * 127 DEL (delete)
     *
     * @param c 被检查的字符
     * @return true表示为控制符，控制符位于 0~31 和 127
     */
    public static boolean isAsciiControl(final char c) {
        return c < 32 || c == 127;
    }

    /**
     * 判断是否为英文字母（包括大写和小写）
     *
     * @param c 被检查的字符
     * @return true-表示为大写或小写英文字母字符 A~Z || z~z; false-表示非英文字母字符
     */
    public static boolean isLetter(final char c) {
        return isLetterUpper(c) || isLetterLower(c);
    }

    /**
     * 判断传入字符是否为大写字母 A-Z
     * letter 字母
     * 65 - A
     * 90 - Z
     *
     * @param c 被检查的字符
     * @return true-表示为大写字母字符 A~Z; false-表示非大写字母字符
     */
    public static boolean isLetterUpper(final char c) {
        return c >= 65 && c <= 90;
    }

    /**
     * 判断传入字符是否为小写字母 A-Z
     * letter 字母
     * 97 - a
     * 122 - z
     *
     * @param c 被检查的字符
     * @return true-表示为大写字母字符 a~z; false-表示非小写字母字符
     */
    public static boolean isLetterLower(final char c) {
        return c >= 97 && c <= 122;
    }

    /**
     * 检查是否为数字字符，数字字符指0~9
     * 48 - 0
     * 59 - 9
     *
     * @param c 被检查的字符
     * @return true-表示为数字字符 0~9; false-表示非数字字符
     */
    public static boolean isNumber(final char c) {
        return c >= 48 && c <= 57;
    }


    /**
     * 是否为16进制中的字符或数字，包括A~f、a~f、0~9
     * 97 ~ 103  A ~ F
     * 65 ~ 71   a ~ Z
     *
     * @param c 被检查的字符
     * @return true-16进制中的字符或数字，包括A~f、a~f、0~9; false-非16进制表中的字符
     */
    public static boolean isHex(final char c) {
        return isNumber(c) || (c >= 97 && c <= 103 || (c >= 65 && c <= 71));
    }

    /**
     * 是否为字符或数字，包括A~Z、a~z、0~9
     *
     * @param c 被检查的字符
     * @return true-表示为字母字符或数字，包括A~Z、a~z、0~9；false-非字母字符和数字
     */
    public static boolean isLetterOrNumber(final char c) {
        return isNumber(c) || isLetter(c);
    }

    /**
     * 字符转为字符串
     * 如果是 ASCII 字符，那么使用 ASCIIStrCache 中的缓存
     *
     * @param c 字符
     * @return 字符串
     */
    public static String toString(char c) {
        return ASCIIStrCache.toString(c);
    }

    /**
     * 给定类名是否为字符类
     * 字符类包括：Character.class | char.class
     *
     * @param clazz 被检查的类
     * @return true-表示字符类；false-表示非字符类
     */
    public static boolean isCharClass(Class<?> clazz) {
        return clazz == Character.class || clazz == char.class;
    }

    /**
     * 给定对象对应的类是否为字符类
     * 字符类包括：Character.class | char.class
     *
     * @param object 被检查的对象
     * @return true-表示该对象为字符类对象
     */
    public static boolean isChar(Object object) {
        return object instanceof Character || object.getClass() == char.class;
    }

    /**
     * 是否空白符
     * 空白符包括空格、制表符、全角空格和不间断空格
     * 制表符 '\ufeff'
     * 不间断空格 '\u202a'
     *
     * @param c 被检查的对象
     * @return true-空白字符；false-非空白字符
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符
     * 空白符包括空格、制表符、全角空格和不间断空格
     * <p>
     * 空格 Character.isSpaceChar(c)
     * 不间断空格 Character.isWhitespace(c)
     *
     * @param c 被检查的对象
     * @return true-空白字符；false-非空白字符
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == '\ufeff' || c == '\u202a';
    }

    /**
     * 判断是否为emoji表情符
     *
     * @param c 字符
     * @return true-emoji表情符；false-非emoji表情符
     */
    public static boolean isEmoji(char c) {
        return !((0x0 == c) ||
                (0x9 == c) ||
                (0xA == c) ||
                (0xD == c) ||
                ((0x20 <= c) && (0xD7FF >= c)) ||
                ((0xE000 <= c) && (0xFFFD >= c)) ||
                ((0x100000 <= c) && (0x10FFFF >= c)));
    }


    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为 \
     * Linux（Unix）为 /
     * Separator 分隔符
     *
     * @param c 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(char c) {
        return CharConstants.FORWARD_SLASH == c || CharConstants.BACKWARD_SLASH == c;
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1         字符1
     * @param c2         字符2
     * @param ignoreCase 是否忽略大小写
     * @return TRUE-相同；FALSE-不同
     */
    public static boolean equals(char c1, char c2, boolean ignoreCase) {
        if (ignoreCase) {
            return Character.toLowerCase(c1) == Character.toLowerCase(c2);
        }
        return c1 == c2;
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1 字符1
     * @param c2 字符2
     * @return TRUE-相同；FALSE-不同
     */
    public static boolean equals(char c1, char c2) {
        return c1 == c2;
    }

    /**
     * 获取字符类型
     *
     * @param c 字符
     * @return 字符类型
     */
    public static int getType(int c) {
        return Character.getType(c);
    }

    /**
     * 获取给定字符的16进制数值
     *
     * @param b 字符
     * @return 16进制字符
     * @since 5.3.1
     */
    public static int getDigit16(int b) {
        return Character.digit(b, IntConstants.SIXTEEN);
    }
}
