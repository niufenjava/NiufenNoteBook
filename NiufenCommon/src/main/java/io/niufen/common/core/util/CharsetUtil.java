package io.niufen.common.core.util;

import io.niufen.common.core.io.FileUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集工具类
 * <p>
 * https://www.cnblogs.com/cuiqq/p/11761375.html
 * java.nio.charset 包中提供了Charset类，它继承了Comparable接口；还有CharsetDecoder、CharsetEncoder编码和解码的类。
 * Java中的字符使用 Unicode编码，每个字符占用两个字节，16个二进制位，
 * Java中的String默认使用系统字符集UTF-16
 * 向 ByteBuffer 中存放数据的时候需要考虑字符的编码，从中读取的时候也需要考虑字符的编码方式，也就是编码和解码。
 * <p>
 * Unicode，就像它的名字都表示的，这是一种所有符号的编码。
 * Unicode 只是一个符号集，它只规定了符号的二进制代码，却没有规定这个二进制代码应该如何存储。
 * UTF-8 是 Unicode 的实现方式之一。
 * UTF-8 最大的一个特点，就是它是一种变长的编码方式。它可以使用1~4个字节表示一个符号，根据不同的符号而变化字节长度。
 * <p>
 * 有了UTF-8为何还要使用GBK编码 既然现在已经有了一个统一的UTF-8编码来表示所有语言来，
 * 为什么现在很多程序以及系统仍旧还在使用GBK，Big5，ISO-8859-1等这些编码呢？
 * 我觉得主要有两点因素：
 * 1. 兼容问题。由于之前的历史原因，导致很多系统及程序应用是使用其他编码来开发的，
 * 而UTF-8又不能兼容之前的编码，如果要继续使用也只能用对应的编码方式。
 * 2. 资源问题。由于UTF-8编码是变长的，需要使用1～4个字节来表示一个字符，
 * 如果是汉字的话通常需要2～4个字节来表示。而使用GBK编码只需要2个字节。
 * 当你的文本内容及使用的对象主要是中文环境时，使用GBK编码将大大减少你的存储空间、传输速度及网络流量。
 *
 * @author haijun.zhang
 * @date 2020/5/28
 * @time 16:13
 */
public class CharsetUtil {

    /**
     * ISO-8859-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * GBK
     */
    public static final String GBK = "GBK";

    /**
     * ISO-8859-1
     */
    public static final Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;

    /**
     * UTF-8
     */
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;

    /**
     * GBK
     */
    public static final Charset CHARSET_GBK;

    static {
        // 避免不支持 GBK 的系统中运行报错
        Charset CHARSET_GBK_TMP = null;
        try {
            CHARSET_GBK_TMP = Charset.forName(GBK);
        } catch (UnsupportedCharsetException e) {
            //ignore
        }
        CHARSET_GBK = CHARSET_GBK_TMP;
    }

    /**
     * @param charsetName 字符集，为空则返回默认字符集
     * @return Charset
     * @throws UnsupportedCharsetException 编码不支持
     */
    public static Charset charset(String charsetName) throws UnsupportedCharsetException {
        return StrUtil.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    /**
     * 解析字符串编码为 Charset对象，解析失败返回系统默认编码
     *
     * @param charsetName 字符集，为空则返回默认字符集
     * @return Charset
     */
    public static Charset parse(String charsetName) {
        return parse(charsetName, Charset.defaultCharset());
    }

    /**
     * 解析字符串编码为 Charset 对象，解析失败返回默认编码
     *
     * @param charsetName    字符集，为空则返回默认字符集
     * @param defaultCharset 解析失败使用默认编码
     * @return Charset
     */
    public static Charset parse(String charsetName, Charset defaultCharset) {
        if (StrUtil.isBlank(charsetName)) {
            return defaultCharset;
        }
        Charset result;
        try {
            result = Charset.forName(charsetName);
        } catch (UnsupportedCharsetException e) {
            result = defaultCharset;
        }
        return result;
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认ISO-8859-1
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * 转换字符串的字符集编码<br>
     * 当以错误的编码读取为字符串时，打印字符串将出现乱码
     * 此方法用于纠正读取使用编码错误导致的乱码问题
     * 例如，在Servlet请求中客户端用GBK编码了请求参数，
     * 而我们使用 UTF-8 读取到的是乱码，此时，
     * 使用此方法即可还原原编码的内容
     *
     * @param source      被转换的字符串
     * @param srcCharset  源字符集，默认 ISO-8859-1
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset) {
            srcCharset = CHARSET_ISO_8859_1;
        }
        if (null == destCharset) {
            destCharset = CHARSET_UTF_8;
        }
        if (StrUtil.isBlank(source) || srcCharset.equals(destCharset)) {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * TODO
     * 转换文件编码
     * 此方法用于转换文件编码，读取的文件实际编码必须与指定的 srcCharset编码一致，否则导致乱码
     *
     * @param file        文件
     * @param srcCharset  原文件的编码，必须与文件内容的编码保持一致
     * @param descCharset 转换后的编码
     * @return 被转换编码的问题及
     */
    public static File convert(File file, Charset srcCharset, Charset descCharset) {
        // TODO
        return null;
    }

    /**
     * 系统字符集编码，如果是Windows，则默认为GBK编码，否则取 {@link CharsetUtil#defaultCharsetName()}
     *
     * @return 系统字符集编码
     * @see CharsetUtil#defaultCharsetName()
     */
    public static String systemCharsetName() {
        return systemCharset().name();
    }

    /**
     * 操作系统字符集编码，如果是Windows，则默认为GBK编码，否则取 {@link CharsetUtil#defaultCharsetName()}
     *
     * @return 系统字符集编码
     * @see CharsetUtil#defaultCharsetName()
     */
    public static Charset systemCharset() {
        return FileUtil.isWindows() ? CHARSET_GBK : defaultCharset();
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static String defaultCharsetName() {
        return defaultCharset().name();
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }
}
