package io.niufen.common.util;


import io.niufen.common.io.FastByteArrayOutputStream;
import io.niufen.common.io.IORuntimeException;
import io.niufen.common.io.LineHandler;
import io.niufen.common.io.StreamProgress;
import io.niufen.common.lang.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * IO 工具类
 * IO 工具类只是辅助流的读写，并不负责关闭。
 * 原因是流可能被多次读写，读写关闭后容易造成问题
 *
 * @author niufen
 */
public class IoUtil {

    /**
     * 默认缓存大小 8192
     */
    public static final int DEFAULT_BUFFER_SIZE = 2 << 12;
    /**
     * 默认中等缓存大小 16384
     */
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 2 << 13;
    /**
     * 默认大缓存大小 32768
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 2 << 14;

    /**
     * 数据流末尾
     */
    public static final int EOF = -1;


    /**
     * 拷贝流，使用默认Buffer大小，拷贝后不关闭流
     *
     * @param in  输入流
     * @param out 输出流
     * @return 传输的byte数
     * @throws IORuntimeException IO异常
     */
    public static long copy(InputStream in, OutputStream out) throws IORuntimeException {
        return copy(in, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param in         输入流
     * @param out        输出流
     * @param bufferSize 缓存大小
     * @return 传输的byte数
     * @throws IORuntimeException IO异常
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize) throws IORuntimeException {
        return copy(in, out, bufferSize, null);
    }

    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param in             输入流
     * @param out            输出流
     * @param bufferSize     缓存大小
     * @param streamProgress 进度条
     * @return 传输的byte数
     * @throws IORuntimeException IO异常
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize, StreamProgress streamProgress) throws IORuntimeException {
        Assert.notNull(in, "InputStream is null !");
        Assert.notNull(out, "OutputStream is null !");
        if (bufferSize <= 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }

        byte[] buffer = new byte[bufferSize];
        if (null != streamProgress) {
            streamProgress.start();
        }
        long size = 0;
        try {
            for (int readSize; (readSize = in.read(buffer)) != EOF; ) {
                out.write(buffer, 0, readSize);
                size += readSize;
                out.flush();
                if (null != streamProgress) {
                    streamProgress.progress(size);
                }
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (null != streamProgress) {
            streamProgress.finish();
        }
        return size;
    }


    /**
     * String 转为流
     *
     * @param content     内容
     * @param charsetName 编码
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(String content, String charsetName) {
        return toStream(content, CharsetUtil.charset(charsetName));
    }

    /**
     * String 转为流
     *
     * @param content 内容
     * @param charset 编码
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(String content, Charset charset) {
        if (content == null) {
            return null;
        }
        return toStream(StrUtil.bytes(content, charset));
    }

    /**
     * String 转为UTF-8编码的字节流流
     *
     * @param content 内容
     * @return 字节流
     * @since 4.5.1
     */
    public static ByteArrayInputStream toUtf8Stream(String content) {
        return toStream(content, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 文件转为流
     *
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream toStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * String 转为流
     *
     * @param content 内容bytes
     * @return 字节流
     * @since 4.1.8
     */
    public static ByteArrayInputStream toStream(byte[] content) {
        if (content == null) {
            return null;
        }
        return new ByteArrayInputStream(content);
    }

    /**
     * 转换为{@link BufferedInputStream}
     *
     * @param in {@link InputStream}
     * @return {@link BufferedInputStream}
     * @since 4.0.10
     */
    public static BufferedInputStream toBuffered(InputStream in) {
        return (in instanceof BufferedInputStream) ? (BufferedInputStream) in : new BufferedInputStream(in);
    }

    /**
     * 转换为{@link BufferedOutputStream}
     *
     * @param out {@link OutputStream}
     * @return {@link BufferedOutputStream}
     * @since 4.0.10
     */
    public static BufferedOutputStream toBuffered(OutputStream out) {
        return (out instanceof BufferedOutputStream) ? (BufferedOutputStream) out : new BufferedOutputStream(out);
    }


    /**
     * 获得一个文件读取器，默认使用UTF-8编码
     *
     * @param in 输入流
     * @return BufferedReader对象
     * @since 5.1.6
     */
    public static BufferedReader getUtf8Reader(InputStream in) {
        return getReader(in, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param in          输入流
     * @param charsetName 字符集名称
     * @return BufferedReader对象
     */
    public static BufferedReader getReader(InputStream in, String charsetName) {
        return getReader(in, Charset.forName(charsetName));
    }

    /**
     * 获得一个Reader
     *
     * @param in      输入流
     * @param charset 字符集
     * @return BufferedReader对象
     */
    public static BufferedReader getReader(InputStream in, Charset charset) {
        if (null == in) {
            return null;
        }

        InputStreamReader reader;
        if (null == charset) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }

    /**
     * 获得{@link BufferedReader}<br>
     * 如果是{@link BufferedReader}强转返回，否则新建。如果提供的Reader为null返回null
     *
     * @param reader 普通Reader，如果为null返回null
     * @return {@link BufferedReader} or null
     * @since 3.0.9
     */
    public static BufferedReader getReader(Reader reader) {
        if (null == reader) {
            return null;
        }

        return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
    }


    /**
     * 从缓存中刷出数据
     *
     * @param flushable {@link Flushable}
     * @since 4.2.2
     */
    public static void flush(Flushable flushable) {
        if (null != flushable) {
            try {
                flushable.flush();
            } catch (Exception e) {
                // 静默刷出
            }
        }
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    /**
     * 尝试关闭指定对象<br>
     * 判断对象如果实现了{@link AutoCloseable}，则调用之
     *
     * @param obj 可关闭对象
     * @since 4.3.2
     */
    public static void closeIfPosible(Object obj) {
        if (obj instanceof AutoCloseable) {
            close((AutoCloseable) obj);
        }
    }


    /**
     * 从流中读取内容，使用UTF-8编码
     *
     * @param <T>        集合类型
     * @param in         输入流
     * @param collection 返回集合
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readUtf8Lines(InputStream in, T collection) throws IORuntimeException {
        return readLines(in, CharsetUtil.CHARSET_UTF_8, collection);
    }

    /**
     * 从流中读取内容
     *
     * @param <T>         集合类型
     * @param in          输入流
     * @param charsetName 字符集
     * @param collection  返回集合
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(InputStream in, String charsetName, T collection) throws IORuntimeException {
        return readLines(in, CharsetUtil.charset(charsetName), collection);
    }

    /**
     * 从流中读取内容
     *
     * @param <T>        集合类型
     * @param in         输入流
     * @param charset    字符集
     * @param collection 返回集合
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(InputStream in, Charset charset, T collection) throws IORuntimeException {
        return readLines(getReader(in, charset), collection);
    }

    /**
     * 从Reader中读取内容
     *
     * @param <T>        集合类型
     * @param reader     {@link Reader}
     * @param collection 返回集合
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(Reader reader, final T collection) throws IORuntimeException {
        readLines(reader, (LineHandler) collection::add);
        return collection;
    }

    /**
     * 按行读取UTF-8编码数据，针对每行的数据做处理
     *
     * @param in          {@link InputStream}
     * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static void readUtf8Lines(InputStream in, LineHandler lineHandler) throws IORuntimeException {
        readLines(in, CharsetUtil.CHARSET_UTF_8, lineHandler);
    }

    /**
     * 按行读取数据，针对每行的数据做处理
     *
     * @param in          {@link InputStream}
     * @param charset     {@link Charset}编码
     * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
     * @throws IORuntimeException IO异常
     * @since 3.0.9
     */
    public static void readLines(InputStream in, Charset charset, LineHandler lineHandler) throws IORuntimeException {
        readLines(getReader(in, charset), lineHandler);
    }

    /**
     * 按行读取数据，针对每行的数据做处理<br>
     * {@link Reader}自带编码定义，因此读取数据的编码跟随其编码。
     *
     * @param reader      {@link Reader}
     * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
     * @throws IORuntimeException IO异常
     */
    public static void readLines(Reader reader, LineHandler lineHandler) throws IORuntimeException {
        Assert.notNull(reader);
        Assert.notNull(lineHandler);

        // 从返回的内容中读取所需内容
        final BufferedReader bReader = getReader(reader);
        String line;
        try {
            while ((line = bReader.readLine()) != null) {
                lineHandler.handle(line);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 读取16进制字符串
     *
     * @param in          {@link InputStream}
     * @param length      长度
     * @param toLowerCase true 传换成小写格式 ， false 传换成大写格式
     * @return 16进制字符串
     * @throws IORuntimeException IO异常
     */
    public static String readHex(InputStream in, int length, boolean toLowerCase) throws IORuntimeException {
        return HexUtil.encodeHexStr(readBytes(in, length), toLowerCase);
    }

    /**
     * 从流中读取前28个byte并转换为16进制，字母部分使用大写
     *
     * @param in {@link InputStream}
     * @return 16进制字符串
     * @throws IORuntimeException IO异常
     */
    public static String readHex28Upper(InputStream in) throws IORuntimeException {
        return readHex(in, 28, false);
    }

    /**
     * 从流中读取前28个byte并转换为16进制，字母部分使用小写
     *
     * @param in {@link InputStream}
     * @return 16进制字符串
     * @throws IORuntimeException IO异常
     */
    public static String readHex28Lower(InputStream in) throws IORuntimeException {
        return readHex(in, 28, true);
    }

    /**
     * 从流中读取bytes，读取完毕后关闭流
     *
     * @param in {@link InputStream}
     * @return bytes
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(InputStream in) throws IORuntimeException {
        return readBytes(in, true);
    }

    /**
     * 从流中读取bytes
     *
     * @param in            {@link InputStream}
     * @param isCloseStream 是否关闭输入流
     * @return bytes
     * @throws IORuntimeException IO异常
     * @since 5.0.4
     */
    public static byte[] readBytes(InputStream in, boolean isCloseStream) throws IORuntimeException {
        final FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        copy(in, out);
        if (isCloseStream) {
            close(in);
        }
        return out.toByteArray();
    }

    /**
     * 读取指定长度的byte数组，不关闭流
     *
     * @param in     {@link InputStream}，为null返回null
     * @param length 长度，小于等于0返回空byte数组
     * @return bytes
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(InputStream in, int length) throws IORuntimeException {
        if (null == in) {
            return null;
        }
        if (length <= 0) {
            return new byte[0];
        }

        byte[] b = new byte[length];
        int readLength;
        try {
            readLength = in.read(b);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (readLength > 0 && readLength < length) {
            byte[] b2 = new byte[length];
            System.arraycopy(b, 0, b2, 0, readLength);
            return b2;
        } else {
            return b;
        }
    }

}

