package io.niufen.common.core.io.file;

import io.niufen.common.core.io.FileUtil;
import io.niufen.common.core.util.CharsetUtil;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 文件包装器，扩展文件对象
 *
 * @author haijun.zhang
 * @date 2020/5/28
 * @time 17:42
 */
public class FileWrapper implements Serializable {

    private static final long serialVersionUID = -5880024219742556813L;

    protected File file;

    protected Charset charset;

    /**
     * 默认编码 UTF-8
     */
    public static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;

    // ----------  Constructor start ----------

    /**
     * 构造
     *
     * @param file    文件
     * @param charset 编码
     */
    public FileWrapper(File file, Charset charset) {
        this.file = file;
        this.charset = charset;
    }
    // ----------  Constructor end ----------

    // ----------  setter getter start ----------

    /**
     * 获得文件
     *
     * @return 文件
     */
    public File getFile() {
        return file;
    }

    /**
     * 设置文件
     *
     * @param file 文件
     * @return 自身
     */
    public FileWrapper setFile(File file) {
        this.file = file;
        return this;
    }

    /**
     * 获得字符集编码
     *
     * @return 编码
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * 设置字符集编码
     *
     * @param charset 编码
     * @return 自身
     */
    public FileWrapper setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
    // ----------  setter getter end ----------

    /**
     * 可读的文件大小
     *
     * @return 大小
     */
    public String readableFileSize() {
        return FileUtil.readableFileSize(file.length());
    }
}
