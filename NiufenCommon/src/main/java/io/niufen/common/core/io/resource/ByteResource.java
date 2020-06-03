package io.niufen.common.core.io.resource;

import io.niufen.common.core.io.IORuntimeException;
import io.niufen.common.core.util.StrUtil;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 基于 byte[] 的资源获取器<br>
 * 注意，此对象中 getUrl 方法始终返回 null
 *
 * @author haijun.zhang
 * @date 2020/6/2
 * @time 13:11
 */
public class ByteResource implements Resource, Serializable {

    private static final long serialVersionUID = -4144911875089024631L;

    /**
     * 字节数组
     */
    private final byte[] bytes;

    /**
     * 资源名称
     */
    private final String name;

    /**
     * 构造
     *
     * @param bytes 字节数组
     */
    public ByteResource(byte[] bytes) {
        this(bytes, null);
    }

    /**
     * 构造
     *
     * @param bytes 字节数组
     * @param name  资源名称
     */
    public ByteResource(byte[] bytes, String name) {
        this.bytes = bytes;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override
    public BufferedReader getReader(Charset charset) {
        return new BufferedReader(new StringReader(readStr(charset)));
    }

    @Override
    public String readStr(Charset charset) throws IORuntimeException {
        return StrUtil.str(this.bytes, charset);
    }

    @Override
    public byte[] readBytes() throws IORuntimeException {
        return this.bytes;
    }
}
