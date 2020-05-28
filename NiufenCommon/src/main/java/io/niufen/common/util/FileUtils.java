package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;

import java.io.File;

/**
 * 文件处理相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/28
 * @time 17:50
 */
public class FileUtils {

    /**
     * 类Unix操作系统路径分隔符
     */
    private static final char UNIX_SEPARATOR = CharConstants.FORWARD_SLASH;

    /**
     * Windows操作系统路径分隔符
     */
    private static final char WINDOWS_SEPARATOR = CharConstants.BACKWARD_SLASH;

    /**
     * 是否为 Windows 环境
     *
     * @return true-windows 环境；false-非windows环境
     */
    public static boolean isWindowsSystem() {
        return WINDOWS_SEPARATOR == File.separatorChar;
    }
}
