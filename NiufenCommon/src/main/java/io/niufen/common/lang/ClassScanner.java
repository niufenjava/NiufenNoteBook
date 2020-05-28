package io.niufen.common.lang;

import io.niufen.common.collection.SetUtils;
import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.StringConstants;
import io.niufen.common.util.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * 类扫描器
 *
 * @author haijun.zhang
 * @date 2020/5/28
 * @time 15:30
 */
public class ClassScanner implements Serializable {
    private static final long serialVersionUID = -1380706982210457740L;

    /**
     * 包名
     */
    private final String packageName;

    /**
     * 包名，最后跟一个点，表示包名，避免在检查前缀时的歧义
     */
    private final String packageNameEndWithDot;

    /**
     * 包路径，用于文件中对路径操作
     */
    private final String packageDirName;

    /**
     * 包路径，用于 jar 中对路径操作，在 Linux 下与 packageDirName 一致
     */
    private final String packagePath;

    /**
     * 过滤器
     */
    private final Filter<Class<?>> classFilter;

    /**
     * 编码
     */
    private final Charset charset;

    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    /**
     * 是否初始化类
     */
    private boolean initialize;

    /**
     * 扫描结果集
     */
    private final Set<Class<?>> classes = SetUtils.newSet();

    /**
     * 构造
     *
     * @param packageName 包名，所有包转入 "" 或者 null
     * @param classFilter 过滤器，无需换入null
     * @param charset     编码
     */
    public ClassScanner(String packageName, Filter<Class<?>> classFilter, Charset charset) {
        packageName = StringUtils.nullToEmpty(packageName);
        this.packageName = packageName;
        this.packageNameEndWithDot = StringUtils.addSuffixIfNot(packageName, StringConstants.Mark.DOT);
        this.packageDirName = packageName.replace(StringConstants.Mark.DOT, File.separator);
        this.packagePath = packageName.replace(CharConstants.DOT, CharConstants.FORWARD_SLASH);
        this.classFilter = classFilter;
        this.charset = charset;
    }
}
