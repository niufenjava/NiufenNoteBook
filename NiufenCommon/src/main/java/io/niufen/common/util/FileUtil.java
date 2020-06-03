package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.io.IORuntimeException;
import io.niufen.common.io.IoUtil;
import io.niufen.common.io.LineHandler;
import io.niufen.common.io.file.FileCopier;
import io.niufen.common.io.file.FileMode;
import io.niufen.common.io.file.FileReader;
import io.niufen.common.io.file.FileWriter;
import io.niufen.common.io.resource.ResourceUtil;
import io.niufen.common.lang.Assert;
import io.niufen.common.text.StrSpliter;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 文件处理相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/28
 * @time 17:50
 */
public class FileUtil {

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


    /**
     * 创建File对象，相当于调用new File()，不做任何处理
     *
     * @param path 文件路径
     * @return File
     * @since 4.1.4
     */
    public static File newFile(String path) {
        return new File(path);
    }

    /**
     * 创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (null == path) {
            return null;
        }
        return new File(getAbsolutePath(path));
    }

    /**
     * 创建File对象<br>
     * 此方法会检查slip漏洞，漏洞说明见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param parent 父目录
     * @param path   文件路径
     * @return File
     */
    public static File file(String parent, String path) {
        return file(new File(parent), path);
    }

    /**
     * 创建File对象<br>
     * 此方法会检查slip漏洞，漏洞说明见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param parent 父文件对象
     * @param path   文件路径
     * @return File
     */
    public static File file(File parent, String path) {
        if (StrUtil.isBlank(path)) {
            throw new NullPointerException("File path is blank!");
        }
        return checkSlip(parent, new File(parent, path));
    }

    /**
     * 通过多层目录参数创建文件<br>
     * 此方法会检查slip漏洞，漏洞说明见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param directory 父目录
     * @param names     元素名（多层目录名），由外到内依次传入
     * @return the file 文件
     * @since 4.0.6
     */
    public static File file(File directory, String... names) {
        Assert.notNull(directory, "directorydirectory must not be null");
        if (ArrayUtil.isEmpty(names)) {
            return directory;
        }

        File file = directory;
        for (String name : names) {
            if (null != name) {
                file = file(file, name);
            }
        }
        return file;
    }

    /**
     * 通过多层目录创建文件
     * <p>
     * 元素名（多层目录名）
     *
     * @param names 多层文件的文件名，由外到内依次传入
     * @return the file 文件
     * @since 4.0.6
     */
    public static File file(String... names) {
        if (ArrayUtil.isEmpty(names)) {
            return null;
        }

        File file = null;
        for (String name : names) {
            if (file == null) {
                file = file(name);
            } else {
                file = file(file, name);
            }
        }
        return file;
    }

    /**
     * 创建File对象
     *
     * @param uri 文件URI
     * @return File
     */
    public static File file(URI uri) {
        if (uri == null) {
            throw new NullPointerException("File uri is null!");
        }
        return new File(uri);
    }

    /**
     * 创建File对象
     *
     * @param url 文件URL
     * @return File
     */
    public static File file(URL url) {
        return new File(URLUtil.toURI(url));
    }


    /**
     * 获取绝对路径<br>
     * 此方法不会判定给定路径是否有效（文件或目录存在）
     *
     * @param path      相对路径
     * @param baseClass 相对路径所相对的类
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path, Class<?> baseClass) {
        String normalPath;
        if (path == null) {
            normalPath = StrUtil.EMPTY;
        } else {
            normalPath = normalize(path);
            if (isAbsolutePath(normalPath)) {
                // 给定的路径已经是绝对路径了
                return normalPath;
            }
        }

        // 相对于ClassPath路径
        final URL url = ResourceUtil.getResource(normalPath, baseClass);
        if (null != url) {
            // 对于jar中文件包含file:前缀，需要去掉此类前缀，在此做标准化，since 3.0.8 解决中文或空格路径被编码的问题
            return FileUtil.normalize(URLUtil.getDecodedPath(url));
        }

        // 如果资源不存在，则返回一个拼接的资源绝对路径
        final String classPath = ClassUtil.getClassPath();
        if (null == classPath) {
            // throw new NullPointerException("ClassPath is null !");
            // 在jar运行模式中，ClassPath有可能获取不到，此时返回原始相对路径（此时获取的文件为相对工作目录）
            return path;
        }

        // 资源不存在的情况下使用标准化路径有问题，使用原始路径拼接后标准化路径
        return normalize(classPath.concat(Objects.requireNonNull(path)));
    }

    /**
     * 获取绝对路径，相对于ClassPath的目录<br>
     * 如果给定就是绝对路径，则返回原路径，原路径把所有\替换为/<br>
     * 兼容Spring风格的路径表示，例如：classpath:config/example.setting也会被识别后转换
     *
     * @param path 相对路径
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path) {
        return getAbsolutePath(path, null);
    }

    /**
     * 获取标准的绝对路径
     *
     * @param file 文件
     * @return 绝对路径
     */
    public static String getAbsolutePath(File file) {
        if (file == null) {
            return null;
        }

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * 给定路径已经是绝对路径<br>
     * 此方法并没有针对路径做标准化，建议先执行{@link #normalize(String)}方法标准化路径后判断
     *
     * @param path 需要检查的Path
     * @return 是否已经是绝对路径
     */
    public static boolean isAbsolutePath(String path) {
        if (StrUtil.isEmpty(path)) {
            return false;
        }

        // 给定的路径已经是绝对路径了
        return StrUtil.C_SLASH == path.charAt(0) || path.matches("^[a-zA-Z]:([/\\\\].*)?");
    }

    /**
     * 修复路径<br>
     * 如果原路径尾部有分隔符，则保留为标准分隔符（/），否则不保留
     * <ol>
     * <li>1. 统一用 /</li>
     * <li>2. 多个 / 转换为一个 /</li>
     * <li>3. 去除两边空格</li>
     * <li>4. .. 和 . 转换为绝对路径，当..多于已有路径时，直接返回根路径</li>
     * </ol>
     * <p>
     * 栗子：
     *
     * <pre>
     * "/foo//" =》 "/foo/"
     * "/foo/./" =》 "/foo/"
     * "/foo/../bar" =》 "/bar"
     * "/foo/../bar/" =》 "/bar/"
     * "/foo/../bar/../baz" =》 "/baz"
     * "/../" =》 "/"
     * "foo/bar/.." =》 "foo"
     * "foo/../bar" =》 "bar"
     * "foo/../../bar" =》 "bar"
     * "//server/foo/../bar" =》 "/server/bar"
     * "//server/../bar" =》 "/bar"
     * "C:\\foo\\..\\bar" =》 "C:/bar"
     * "C:\\..\\bar" =》 "C:/bar"
     * "~/foo/../bar/" =》 "~/bar/"
     * "~/../bar" =》 "bar"
     * </pre>
     *
     * @param path 原路径
     * @return 修复后的路径
     */
    public static String normalize(String path) {
        if (path == null) {
            return null;
        }


        // 兼容Spring风格的ClassPath路径，去除前缀，不区分大小写
        String pathToUse = StrUtil.removePrefixIgnoreCase(path, URLUtil.CLASSPATH_URL_PREFIX);
        // 去除file:前缀
        pathToUse = StrUtil.removePrefixIgnoreCase(pathToUse, URLUtil.FILE_URL_PREFIX);

        // 识别home目录形式，并转换为绝对路径
        if (pathToUse.startsWith("~")) {
            pathToUse = pathToUse.replace("~", getUserHomePath());
        }

        // 统一使用斜杠
        pathToUse = pathToUse.replaceAll("[/\\\\]+", StrUtil.SLASH).trim();
        //兼容Windows下的共享目录路径（原始路径如果以\\开头，则保留这种路径）
        if (path.startsWith("\\\\")) {
            pathToUse = "\\" + pathToUse;
        }

        String prefix = "";
        int prefixIndex = pathToUse.indexOf(StrUtil.COLON);
        if (prefixIndex > -1) {
            // 可能Windows风格路径
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (StrUtil.startWith(prefix, StrUtil.C_SLASH)) {
                // 去除类似于/C:这类路径开头的斜杠
                prefix = prefix.substring(1);
            }
            if (false == prefix.contains(StrUtil.SLASH)) {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            } else {
                // 如果前缀中包含/,说明非Windows风格path
                prefix = StrUtil.EMPTY;
            }
        }
        if (pathToUse.startsWith(StrUtil.SLASH)) {
            prefix += StrUtil.SLASH;
            pathToUse = pathToUse.substring(1);
        }

        List<String> pathList = StrSpliter.split(pathToUse, StrUtil.C_SLASH);
        List<String> pathElements = new LinkedList<>();
        int tops = 0;

        String element;
        for (int i = pathList.size() - 1; i >= 0; i--) {
            element = pathList.get(i);
            // 只处理非.的目录，即只处理非当前目录
            if (!StrUtil.DOT.equals(element)) {
                if (StrUtil.DOUBLE_DOT.equals(element)) {
                    tops++;
                } else {
                    if (tops > 0) {
                        // 有上级目录标记时按照个数依次跳过
                        tops--;
                    } else {
                        // Normal path element found.
                        pathElements.add(0, element);
                    }
                }
            }
        }

        return prefix + CollUtil.join(pathElements, StrUtil.SLASH);
    }

    /**
     * 获取用户路径（绝对路径）
     *
     * @return 用户路径
     * @since 4.0.6
     */
    public static String getUserHomePath() {
        return System.getProperty("user.home");
    }

    /**
     * 可读的文件大小
     *
     * @param file 文件
     * @return 大小
     */
    public static String readableFileSize(File file) {
        return readableFileSize(file.length());
    }

    /**
     * 可读的文件大小<br>
     * 参考 http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
     *
     * @param size Long类型大小
     * @return 大小
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB", "EB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    /**
     * 获得输入流
     *
     * @param path Path
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     * @since 4.0.0
     */
    public static BufferedInputStream getInputStream(Path path) throws IORuntimeException {
        try {
            return new BufferedInputStream(Files.newInputStream(path));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     */
    public static BufferedInputStream getInputStream(File file) throws IORuntimeException {
        return new BufferedInputStream(IoUtil.toStream(file));
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     */
    public static BufferedInputStream getInputStream(String path) throws IORuntimeException {
        return getInputStream(file(path));
    }


    /**
     * 检查父完整路径是否为自路径的前半部分，如果不是说明不是子路径，可能存在slip注入。
     * <p>
     * 见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param parentFile 父文件或目录
     * @param file       子文件或目录
     * @return 子文件或目录
     * @throws IllegalArgumentException 检查创建的子文件不在父目录中抛出此异常
     */
    public static File checkSlip(File parentFile, File file) throws IllegalArgumentException {
        if (null != parentFile && null != file) {
            String parentCanonicalPath;
            String canonicalPath;
            try {
                parentCanonicalPath = parentFile.getCanonicalPath();
                canonicalPath = file.getCanonicalPath();
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
            if (false == canonicalPath.startsWith(parentCanonicalPath)) {
                throw new IllegalArgumentException("New file is outside of the parent dir: " + file.getName());
            }
        }
        return file;
    }


    /**
     * 获得一个文件读取器
     *
     * @param path 文件Path
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     * @since 4.0.0
     */
    public static BufferedReader getUtf8Reader(Path path) throws IORuntimeException {
        return getReader(path, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file 文件
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getUtf8Reader(File file) throws IORuntimeException {
        return getReader(file, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path 文件路径
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getUtf8Reader(String path) throws IORuntimeException {
        return getReader(path, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path    文件Path
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     * @since 4.0.0
     */
    public static BufferedReader getReader(Path path, Charset charset) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(path), charset);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getReader(File file, String charsetName) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(file), charsetName);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file    文件
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getReader(File file, Charset charset) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(file), charset);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path        绝对路径
     * @param charsetName 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getReader(String path, String charsetName) throws IORuntimeException {
        return getReader(file(path), charsetName);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path    绝对路径
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getReader(String path, Charset charset) throws IORuntimeException {
        return getReader(file(path), charset);
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param fullFilePath 文件的全路径，使用POSIX风格
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(String fullFilePath) throws IORuntimeException {
        if (fullFilePath == null) {
            return null;
        }
        return touch(file(fullFilePath));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(File file) throws IORuntimeException {
        if (null == file) {
            return null;
        }
        if (!file.exists()) {
            mkParentDirs(file);
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (Exception e) {
                throw new IORuntimeException(e);
            }
        }
        return file;
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param parent 父文件对象
     * @param path   文件路径
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File touch(File parent, String path) throws IORuntimeException {
        return touch(file(parent, path));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param parent 父文件对象
     * @param path   文件路径
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File touch(String parent, String path) throws IORuntimeException {
        return touch(file(parent, path));
    }

    /**
     * 创建所给文件或目录的父目录
     *
     * @param file 文件或目录
     * @return 父目录
     */
    public static File mkParentDirs(File file) {
        final File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parentFile.mkdirs();
        }
        return parentFile;
    }

    /**
     * 创建父文件夹，如果存在直接返回此文件夹
     *
     * @param path 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkParentDirs(String path) {
        if (path == null) {
            return null;
        }
        return mkParentDirs(file(path));
    }


    /**
     * 获取指定位置的子路径部分，支持负数，例如index为-1表示从后数第一个节点位置
     *
     * @param path  路径
     * @param index 路径节点位置，支持负数（负数从后向前计数）
     * @return 获取的子路径
     * @since 3.1.2
     */
    public static Path getPathEle(Path path, int index) {
        return subPath(path, index, index == -1 ? path.getNameCount() : index + 1);
    }

    /**
     * 获取指定位置的最后一个子路径部分
     *
     * @param path 路径
     * @return 获取的最后一个子路径
     * @since 3.1.2
     */
    public static Path getLastPathEle(Path path) {
        return getPathEle(path, path.getNameCount() - 1);
    }


    /**
     * 获取指定位置的子路径部分，支持负数，例如起始为-1表示从后数第一个节点位置
     *
     * @param path      路径
     * @param fromIndex 起始路径节点（包括）
     * @param toIndex   结束路径节点（不包括）
     * @return 获取的子路径
     * @since 3.1.2
     */
    public static Path subPath(Path path, int fromIndex, int toIndex) {
        if (null == path) {
            return null;
        }
        final int len = path.getNameCount();

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
            return null;
        }
        return path.subpath(fromIndex, toIndex);
    }


    /**
     * 单行处理文件内容
     *
     * @param file        {@link RandomAccessFile}文件
     * @param charset     编码
     * @param lineHandler {@link LineHandler}行处理器
     * @throws IORuntimeException IO异常
     * @since 4.5.2
     */
    public static void readLine(RandomAccessFile file, Charset charset, LineHandler lineHandler) {
        final String line = readLine(file, charset);
        if (null != line) {
            lineHandler.handle(line);
        }
    }

    /**
     * 单行处理文件内容
     *
     * @param file    {@link RandomAccessFile}文件
     * @param charset 编码
     * @return 行内容
     * @throws IORuntimeException IO异常
     * @since 4.5.18
     */
    public static String readLine(RandomAccessFile file, Charset charset) {
        String line;
        try {
            line = file.readLine();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (null != line) {
            return CharsetUtil.convert(line, CharsetUtil.CHARSET_ISO_8859_1, charset);
        }

        return null;
    }


    /**
     * 从文件中读取每一行的UTF-8编码数据
     *
     * @param <T>        集合类型
     * @param path       文件路径
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static <T extends Collection<String>> T readUtf8Lines(String path, T collection) throws IORuntimeException {
        return readLines(path, CharsetUtil.CHARSET_UTF_8, collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param <T>        集合类型
     * @param path       文件路径
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(String path, String charset, T collection) throws IORuntimeException {
        return readLines(file(path), charset, collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param <T>        集合类型
     * @param path       文件路径
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(String path, Charset charset, T collection) throws IORuntimeException {
        return readLines(file(path), charset, collection);
    }

    /**
     * 从文件中读取每一行数据，数据编码为UTF-8
     *
     * @param <T>        集合类型
     * @param file       文件路径
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static <T extends Collection<String>> T readUtf8Lines(File file, T collection) throws IORuntimeException {
        return readLines(file, CharsetUtil.CHARSET_UTF_8, collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param <T>        集合类型
     * @param file       文件路径
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(File file, String charset, T collection) throws IORuntimeException {
        return FileReader.create(file, CharsetUtil.charset(charset)).readLines(collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param <T>        集合类型
     * @param file       文件路径
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(File file, Charset charset, T collection) throws IORuntimeException {
        return FileReader.create(file, charset).readLines(collection);
    }

    /**
     * 从文件中读取每一行数据，编码为UTF-8
     *
     * @param <T>        集合类型
     * @param url        文件的URL
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readUtf8Lines(URL url, T collection) throws IORuntimeException {
        return readLines(url, CharsetUtil.CHARSET_UTF_8, collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param <T>         集合类型
     * @param url         文件的URL
     * @param charsetName 字符集
     * @param collection  集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public static <T extends Collection<String>> T readLines(URL url, String charsetName, T collection) throws IORuntimeException {
        return readLines(url, CharsetUtil.charset(charsetName), collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param <T>        集合类型
     * @param url        文件的URL
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static <T extends Collection<String>> T readLines(URL url, Charset charset, T collection) throws IORuntimeException {
        InputStream in = null;
        try {
            in = url.openStream();
            return IoUtil.readLines(in, charset, collection);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtil.close(in);
        }
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param url 文件的URL
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     */
    public static List<String> readUtf8Lines(URL url) throws IORuntimeException {
        return readLines(url, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param url     文件的URL
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     */
    public static List<String> readLines(URL url, String charset) throws IORuntimeException {
        return readLines(url, charset, new ArrayList<>());
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param url     文件的URL
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     */
    public static List<String> readLines(URL url, Charset charset) throws IORuntimeException {
        return readLines(url, charset, new ArrayList<>());
    }

    /**
     * 从文件中读取每一行数据，编码为UTF-8
     *
     * @param path 文件路径
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static List<String> readUtf8Lines(String path) throws IORuntimeException {
        return readLines(path, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     */
    public static List<String> readLines(String path, String charset) throws IORuntimeException {
        return readLines(path, charset, new ArrayList<>());
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static List<String> readLines(String path, Charset charset) throws IORuntimeException {
        return readLines(path, charset, new ArrayList<>());
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param file 文件
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     * @since 3.1.1
     */
    public static List<String> readUtf8Lines(File file) throws IORuntimeException {
        return readLines(file, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param file    文件
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     */
    public static List<String> readLines(File file, String charset) throws IORuntimeException {
        return readLines(file, charset, new ArrayList<>());
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param file    文件
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IORuntimeException IO异常
     */
    public static List<String> readLines(File file, Charset charset) throws IORuntimeException {
        return readLines(file, charset, new ArrayList<>());
    }

    /**
     * 按行处理文件内容，编码为UTF-8
     *
     * @param file        文件
     * @param lineHandler {@link LineHandler}行处理器
     * @throws IORuntimeException IO异常
     */
    public static void readUtf8Lines(File file, LineHandler lineHandler) throws IORuntimeException {
        readLines(file, CharsetUtil.CHARSET_UTF_8, lineHandler);
    }

    /**
     * 按行处理文件内容
     *
     * @param file        文件
     * @param charset     编码
     * @param lineHandler {@link LineHandler}行处理器
     * @throws IORuntimeException IO异常
     */
    public static void readLines(File file, Charset charset, LineHandler lineHandler) throws IORuntimeException {
        FileReader.create(file, charset).readLines(lineHandler);
    }

    /**
     * 按行处理文件内容
     *
     * @param file        {@link RandomAccessFile}文件
     * @param charset     编码
     * @param lineHandler {@link LineHandler}行处理器
     * @throws IORuntimeException IO异常
     * @since 4.5.2
     */
    public static void readLines(RandomAccessFile file, Charset charset, LineHandler lineHandler) {
        String line;
        try {
            while ((line = file.readLine()) != null) {
                lineHandler.handle(CharsetUtil.convert(line, CharsetUtil.CHARSET_ISO_8859_1, charset));
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }


    /**
     * 创建{@link RandomAccessFile}
     *
     * @param path 文件Path
     * @param mode 模式，见{@link FileMode}
     * @return {@link RandomAccessFile}
     * @since 4.5.2
     */
    public static RandomAccessFile createRandomAccessFile(Path path, FileMode mode) {
        return createRandomAccessFile(path.toFile(), mode);
    }

    /**
     * 创建{@link RandomAccessFile}
     *
     * @param file 文件
     * @param mode 模式，见{@link FileMode}
     * @return {@link RandomAccessFile}
     * @since 4.5.2
     */
    public static RandomAccessFile createRandomAccessFile(File file, FileMode mode) {
        try {
            return new RandomAccessFile(file, mode.name());
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 检查两个文件是否是同一个文件<br>
     * 所谓文件相同，是指File对象是否指向同一个文件或文件夹
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 是否相同
     * @throws IORuntimeException IO异常
     * @see Files#isSameFile(Path, Path)
     */
    public static boolean equals(File file1, File file2) throws IORuntimeException {
        Assert.notNull(file1);
        Assert.notNull(file2);
        if (false == file1.exists() || false == file2.exists()) {
            // 两个文件都不存在判断其路径是否相同， 对于一个存在一个不存在的情况，一定不相同
            return false == file1.exists()//
                    && false == file2.exists()//
                    && pathEquals(file1, file2);
        }
        try {
            return Files.isSameFile(file1.toPath(), file2.toPath());
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 文件路径是否相同<br>
     * 取两个文件的绝对路径比较，在Windows下忽略大小写，在Linux下不忽略。
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 文件路径是否相同
     * @since 3.0.9
     */
    public static boolean pathEquals(File file1, File file2) {
        if (isWindows()) {
            // Windows环境
            try {
                if (StrUtil.equalsIgnoreCase(file1.getCanonicalPath(), file2.getCanonicalPath())) {
                    return true;
                }
            } catch (Exception e) {
                if (StrUtil.equalsIgnoreCase(file1.getAbsolutePath(), file2.getAbsolutePath())) {
                    return true;
                }
            }
        } else {
            // 类Unix环境
            try {
                if (StrUtil.equals(file1.getCanonicalPath(), file2.getCanonicalPath())) {
                    return true;
                }
            } catch (Exception e) {
                if (StrUtil.equals(file1.getAbsolutePath(), file2.getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     * @since 3.0.9
     */
    public static boolean isWindows() {
        return WINDOWS_SEPARATOR == File.separatorChar;
    }


    /**
     * 判断给定的目录是否为给定文件或文件夹的子目录
     *
     * @param parent 父目录
     * @param sub    子目录
     * @return 子目录是否为父目录的子目录
     * @since 4.5.4
     */
    public static boolean isSub(File parent, File sub) {
        Assert.notNull(parent);
        Assert.notNull(sub);
        return sub.toPath().startsWith(parent.toPath());
    }


    /**
     * 创建文件夹，如果存在直接返回此文件夹<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param dirPath 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkdir(String dirPath) {
        if (dirPath == null) {
            return null;
        }
        final File dir = file(dirPath);
        return mkdir(dir);
    }

    /**
     * 创建文件夹，会递归自动创建其不存在的父文件夹，如果存在直接返回此文件夹<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param dir 目录
     * @return 创建的目录
     */
    public static File mkdir(File dir) {
        if (dir == null) {
            return null;
        }
        if (false == dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
        return dir;
    }


    /**
     * 获取文件扩展名，扩展名不带“.”
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String extName(File file) {
        if (null == file) {
            return null;
        }
        if (file.isDirectory()) {
            return null;
        }
        return extName(file.getName());
    }

    /**
     * 获得文件的扩展名，扩展名不带“.”
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String extName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(StrUtil.DOT);
        if (index == -1) {
            return StrUtil.EMPTY;
        } else {
            String ext = fileName.substring(index + 1);
            // 扩展名中不能包含路径相关的符号
            return StrUtil.containsAny(ext, UNIX_SEPARATOR, WINDOWS_SEPARATOR) ? StrUtil.EMPTY : ext;
        }
    }


    // -------------------------------------------------------------------------------------------- in end

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param file 文件
     * @return 字节码
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(File file) throws IORuntimeException {
        return FileReader.create(file).readBytes();
    }

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param filePath 文件路径
     * @return 字节码
     * @throws IORuntimeException IO异常
     * @since 3.2.0
     */
    public static byte[] readBytes(String filePath) throws IORuntimeException {
        return readBytes(file(filePath));
    }

    /**
     * 读取文件内容
     *
     * @param file 文件
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readUtf8String(File file) throws IORuntimeException {
        return readString(file, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 读取文件内容
     *
     * @param path 文件路径
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readUtf8String(String path) throws IORuntimeException {
        return readString(path, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 读取文件内容
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readString(File file, String charsetName) throws IORuntimeException {
        return readString(file, CharsetUtil.charset(charsetName));
    }

    /**
     * 读取文件内容
     *
     * @param file    文件
     * @param charset 字符集
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readString(File file, Charset charset) throws IORuntimeException {
        return FileReader.create(file, charset).readString();
    }

    /**
     * 读取文件内容
     *
     * @param path        文件路径
     * @param charsetName 字符集
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readString(String path, String charsetName) throws IORuntimeException {
        return readString(file(path), charsetName);
    }

    /**
     * 读取文件内容
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readString(String path, Charset charset) throws IORuntimeException {
        return readString(file(path), charset);
    }

    /**
     * 读取文件内容
     *
     * @param url     文件URL
     * @param charset 字符集
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public static String readString(URL url, String charset) throws IORuntimeException {
        if (url == null) {
            throw new NullPointerException("Empty url provided!");
        }

        InputStream in = null;
        try {
            in = url.openStream();
            return IoUtil.read(in, charset);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtil.close(in);
        }
    }


    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @param path 目标文件
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File writeBytes(byte[] data, String path) throws IORuntimeException {
        return writeBytes(data, touch(path));
    }

    /**
     * 写数据到文件中
     *
     * @param dest 目标文件
     * @param data 数据
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File writeBytes(byte[] data, File dest) throws IORuntimeException {
        return writeBytes(data, dest, 0, data.length, false);
    }

    /**
     * 写入数据到文件
     *
     * @param data     数据
     * @param dest     目标文件
     * @param off      数据开始位置
     * @param len      数据长度
     * @param isAppend 是否追加模式
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File writeBytes(byte[] data, File dest, int off, int len, boolean isAppend) throws IORuntimeException {
        return FileWriter.create(dest).write(data, off, len, isAppend);
    }

    /**
     * 获取当前系统的换行分隔符
     *
     * <pre>
     * Windows: \r\n
     * Mac: \r
     * Linux: \n
     * </pre>
     *
     * @return 换行符
     * @since 4.0.5
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
        // return System.getProperty("line.separator");
    }


    /**
     * 通过JDK7+的 {@link Files#copy(Path, Path, CopyOption...)} 方法拷贝文件
     *
     * @param src     源文件路径
     * @param dest    目标文件或目录路径，如果为目录使用与源文件相同的文件名
     * @param options {@link StandardCopyOption}
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File copyFile(String src, String dest, StandardCopyOption... options) throws IORuntimeException {
        Assert.notBlank(src, "Source File path is blank !");
        Assert.notNull(src, "Destination File path is null !");
        return copyFile(Paths.get(src), Paths.get(dest), options).toFile();
    }

    /**
     * 通过JDK7+的 {@link Files#copy(Path, Path, CopyOption...)} 方法拷贝文件
     *
     * @param src     源文件
     * @param dest    目标文件或目录，如果为目录使用与源文件相同的文件名
     * @param options {@link StandardCopyOption}
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File copyFile(File src, File dest, StandardCopyOption... options) throws IORuntimeException {
        // check
        Assert.notNull(src, "Source File is null !");
        if (false == src.exists()) {
            throw new IORuntimeException("File not exist: " + src);
        }
        Assert.notNull(dest, "Destination File or directiory is null !");
        if (equals(src, dest)) {
            throw new IORuntimeException("Files '{}' and '{}' are equal", src, dest);
        }
        return copyFile(src.toPath(), dest.toPath(), options).toFile();
    }

    /**
     * 通过JDK7+的 {@link Files#copy(Path, Path, CopyOption...)} 方法拷贝文件
     *
     * @param src     源文件路径
     * @param dest    目标文件或目录，如果为目录使用与源文件相同的文件名
     * @param options {@link StandardCopyOption}
     * @return Path
     * @throws IORuntimeException IO异常
     */
    public static Path copyFile(Path src, Path dest, StandardCopyOption... options) throws IORuntimeException {
        Assert.notNull(src, "Source File is null !");
        Assert.notNull(dest, "Destination File or directiory is null !");

        Path destPath = dest.toFile().isDirectory() ? dest.resolve(src.getFileName()) : dest;
        try {
            return Files.copy(src, destPath, options);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 复制文件或目录<br>
     * 如果目标文件为目录，则将源文件以相同文件名拷贝到目标目录
     *
     * @param srcPath    源文件或目录
     * @param destPath   目标文件或目录，目标不存在会自动创建（目录、文件都创建）
     * @param isOverride 是否覆盖目标文件
     * @return 目标目录或文件
     * @throws IORuntimeException IO异常
     */
    public static File copy(String srcPath, String destPath, boolean isOverride) throws IORuntimeException {
        return copy(file(srcPath), file(destPath), isOverride);
    }

    /**
     * 复制文件或目录<br>
     * 情况如下：
     *
     * <pre>
     * 1、src和dest都为目录，则将src目录及其目录下所有文件目录拷贝到dest下
     * 2、src和dest都为文件，直接复制，名字为dest
     * 3、src为文件，dest为目录，将src拷贝到dest目录下
     * </pre>
     *
     * @param src        源文件
     * @param dest       目标文件或目录，目标不存在会自动创建（目录、文件都创建）
     * @param isOverride 是否覆盖目标文件
     * @return 目标目录或文件
     * @throws IORuntimeException IO异常
     */
    public static File copy(File src, File dest, boolean isOverride) throws IORuntimeException {
        return FileCopier.create(src, dest).setOverride(isOverride).copy();
    }

    /**
     * 复制文件或目录<br>
     * 情况如下：
     *
     * <pre>
     * 1、src和dest都为目录，则讲src下所有文件目录拷贝到dest下
     * 2、src和dest都为文件，直接复制，名字为dest
     * 3、src为文件，dest为目录，将src拷贝到dest目录下
     * </pre>
     *
     * @param src        源文件
     * @param dest       目标文件或目录，目标不存在会自动创建（目录、文件都创建）
     * @param isOverride 是否覆盖目标文件
     * @return 目标目录或文件
     * @throws IORuntimeException IO异常
     */
    public static File copyContent(File src, File dest, boolean isOverride) throws IORuntimeException {
        return FileCopier.create(src, dest).setCopyContentIfDir(true).setOverride(isOverride).copy();
    }

    /**
     * 复制文件或目录<br>
     * 情况如下：
     *
     * <pre>
     * 1、src和dest都为目录，则讲src下所有文件（包括子目录）拷贝到dest下
     * 2、src和dest都为文件，直接复制，名字为dest
     * 3、src为文件，dest为目录，将src拷贝到dest目录下
     * </pre>
     *
     * @param src        源文件
     * @param dest       目标文件或目录，目标不存在会自动创建（目录、文件都创建）
     * @param isOverride 是否覆盖目标文件
     * @return 目标目录或文件
     * @throws IORuntimeException IO异常
     * @since 4.1.5
     */
    public static File copyFilesFromDir(File src, File dest, boolean isOverride) throws IORuntimeException {
        return FileCopier.create(src, dest).setCopyContentIfDir(true).setOnlyCopyFile(true).setOverride(isOverride).copy();
    }


    // -------------------------------------------------------------------------------------------- name start

    /**
     * 返回文件名
     *
     * @param file 文件
     * @return 文件名
     * @since 4.1.13
     */
    public static String getName(File file) {
        return (null != file) ? file.getName() : null;
    }

    /**
     * 返回文件名
     *
     * @param filePath 文件
     * @return 文件名
     * @since 4.1.13
     */
    public static String getName(String filePath) {
        if (null == filePath) {
            return null;
        }
        int len = filePath.length();
        if (0 == len) {
            return filePath;
        }
        if (CharUtil.isFileSeparator(filePath.charAt(len - 1))) {
            // 以分隔符结尾的去掉结尾分隔符
            len--;
        }

        int begin = 0;
        char c;
        for (int i = len - 1; i > -1; i--) {
            c = filePath.charAt(i);
            if (CharUtil.isFileSeparator(c)) {
                // 查找最后一个路径分隔符（/或者\）
                begin = i + 1;
                break;
            }
        }

        return filePath.substring(begin, len);
    }

    /**
     * 返回主文件名
     *
     * @param file 文件
     * @return 主文件名
     */
    public static String mainName(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }
        return mainName(file.getName());
    }

    /**
     * 返回主文件名
     *
     * @param fileName 完整文件名
     * @return 主文件名
     */
    public static String mainName(String fileName) {
        if (null == fileName) {
            return null;
        }
        int len = fileName.length();
        if (0 == len) {
            return fileName;
        }
        if (CharUtil.isFileSeparator(fileName.charAt(len - 1))) {
            len--;
        }

        int begin = 0;
        int end = len;
        char c;
        for (int i = len - 1; i >= 0; i--) {
            c = fileName.charAt(i);
            if (len == end && CharUtil.DOT == c) {
                // 查找最后一个文件名和扩展名的分隔符：.
                end = i;
            }
            // 查找最后一个路径分隔符（/或者\），如果这个分隔符在.之后，则继续查找，否则结束
            if (CharUtil.isFileSeparator(c)) {
                begin = i + 1;
                break;
            }
        }

        return fileName.substring(begin, end);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供file为文件，直接返回过滤结果
     *
     * @param path       当前遍历文件或目录的路径
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录
     * @return 文件列表
     * @since 3.2.0
     */
    public static List<File> loopFiles(String path, FileFilter fileFilter) {
        return loopFiles(file(path), fileFilter);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供file为文件，直接返回过滤结果
     *
     * @param file       当前遍历文件或目录
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录
     * @return 文件列表
     */
    public static List<File> loopFiles(File file, FileFilter fileFilter) {
        final List<File> fileList = new ArrayList<>();
        if (null == file || false == file.exists()) {
            return fileList;
        }

        if (file.isDirectory()) {
            final File[] subFiles = file.listFiles();
            if (ArrayUtil.isNotEmpty(subFiles)) {
                for (File tmp : subFiles) {
                    fileList.addAll(loopFiles(tmp, fileFilter));
                }
            }
        } else {
            if (null == fileFilter || fileFilter.accept(file)) {
                fileList.add(file);
            }
        }

        return fileList;
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供file为文件，直接返回过滤结果
     *
     * @param file       当前遍历文件或目录
     * @param maxDepth   遍历最大深度，-1表示遍历到没有目录为止
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录，null表示接收全部文件
     * @return 文件列表
     * @since 4.6.3
     */
    public static List<File> loopFiles(File file, int maxDepth, final FileFilter fileFilter) {
        final List<File> fileList = new ArrayList<>();
        if (null == file || false == file.exists()) {
            return fileList;
        } else if (false == file.isDirectory()) {
            if (null == fileFilter || fileFilter.accept(file)) {
                fileList.add(file);
            }
            return fileList;
        }

        walkFiles(file.toPath(), maxDepth, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                final File file = path.toFile();
                if (null == fileFilter || fileFilter.accept(file)) {
                    fileList.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return fileList;
    }

    /**
     * 遍历指定path下的文件并做处理
     *
     * @param start    起始路径，必须为目录
     * @param maxDepth 最大遍历深度，-1表示不限制深度
     * @param visitor  {@link FileVisitor} 接口，用于自定义在访问文件时，访问目录前后等节点做的操作
     * @see Files#walkFileTree(Path, java.util.Set, int, FileVisitor)
     * @since 4.6.3
     */
    public static void walkFiles(Path start, int maxDepth, FileVisitor<? super Path> visitor) {
        if (maxDepth < 0) {
            // < 0 表示遍历到最底层
            maxDepth = Integer.MAX_VALUE;
        }

        try {
            Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), maxDepth, visitor);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 递归遍历目录以及子目录中的所有文件
     *
     * @param path 当前遍历文件或目录的路径
     * @return 文件列表
     * @since 3.2.0
     */
    public static List<File> loopFiles(String path) {
        return loopFiles(file(path));
    }

    /**
     * 递归遍历目录以及子目录中的所有文件
     *
     * @param file 当前遍历文件
     * @return 文件列表
     */
    public static List<File> loopFiles(File file) {
        return loopFiles(file, null);
    }

    /**
     * 判断是否为文件，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果为文件true
     */
    public static boolean isFile(String path) {
        return (null != path) && file(path).isFile();
    }

    /**
     * 判断是否为文件，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果为文件true
     */
    public static boolean isFile(File file) {
        return (null != file) && file.isFile();
    }

    /**
     * 判断是否为文件，如果file为null，则返回false
     *
     * @param path          文件
     * @param isFollowLinks 是否跟踪软链（快捷方式）
     * @return 如果为文件true
     */
    public static boolean isFile(Path path, boolean isFollowLinks) {
        if (null == path) {
            return false;
        }
        final LinkOption[] options = isFollowLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
        return Files.isRegularFile(path, options);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].tmp
     *
     * @param dir 临时文件创建的所在目录
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(File dir) throws IORuntimeException {
        return createTempFile("hutool", null, dir, true);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].tmp
     *
     * @param dir       临时文件创建的所在目录
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(File dir, boolean isReCreat) throws IORuntimeException {
        return createTempFile("hutool", null, dir, isReCreat);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].suffix From com.jodd.io.FileUtil
     *
     * @param prefix    前缀，至少3个字符
     * @param suffix    后缀，如果null则使用默认.tmp
     * @param dir       临时文件创建的所在目录
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(String prefix, String suffix, File dir, boolean isReCreat) throws IORuntimeException {
        int exceptionsCount = 0;
        while (true) {
            try {
                File file = File.createTempFile(prefix, suffix, dir).getCanonicalFile();
                if (isReCreat) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                    //noinspection ResultOfMethodCallIgnored
                    file.createNewFile();
                }
                return file;
            } catch (IOException ioex) { // fixes java.io.WinNTFileSystem.createFileExclusively access denied
                if (++exceptionsCount >= 50) {
                    throw new IORuntimeException(ioex);
                }
            }
        }
    }

    /**
     * 移动文件或者目录
     *
     * @param src        源文件或者目录
     * @param dest       目标文件或者目录
     * @param isOverride 是否覆盖目标，只有目标为文件才覆盖
     * @throws IORuntimeException IO异常
     */
    public static void move(File src, File dest, boolean isOverride) throws IORuntimeException {
        // check
        if (false == src.exists()) {
            throw new IORuntimeException("File not found: " + src);
        }

        // 来源为文件夹，目标为文件
        if (src.isDirectory() && dest.isFile()) {
            throw new IORuntimeException(StrUtil.format("Can not move directory [{}] to file [{}]", src, dest));
        }

        if (isOverride && dest.isFile()) {// 只有目标为文件的情况下覆盖之
            //noinspection ResultOfMethodCallIgnored
            dest.delete();
        }

        // 来源为文件，目标为文件夹
        if (src.isFile() && dest.isDirectory()) {
            dest = new File(dest, src.getName());
        }

        if (false == src.renameTo(dest)) {
            // 在文件系统不同的情况下，renameTo会失败，此时使用copy，然后删除原文件
            try {
                copy(src, dest, isOverride);
            } catch (Exception e) {
                throw new IORuntimeException(StrUtil.format("Move [{}] to [{}] failed!", src, dest), e);
            }
            // 复制后删除源
            del(src);
        }
    }

    /**
     * 删除文件或者文件夹<br>
     * 路径如果为相对路径，会转换为ClassPath路径！ 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param fullFileOrDirPath 文件或者目录的路径
     * @return 成功与否
     * @throws IORuntimeException IO异常
     */
    public static boolean del(String fullFileOrDirPath) throws IORuntimeException {
        return del(file(fullFileOrDirPath));
    }

    /**
     * 删除文件或者文件夹<br>
     * 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param file 文件对象
     * @return 成功与否
     * @throws IORuntimeException IO异常
     */
    public static boolean del(File file) throws IORuntimeException {
        if (file == null || false == file.exists()) {
            // 如果文件不存在或已被删除，此处返回true表示删除成功
            return true;
        }

        if (file.isDirectory()) {
            // 清空目录下所有文件和目录
            boolean isOk = clean(file);
            if (false == isOk) {
                return false;
            }
        }

        // 删除文件或清空后的目录
        return file.delete();
    }

    /**
     * 删除文件或者文件夹<br>
     * 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param path 文件对象
     * @return 成功与否
     * @throws IORuntimeException IO异常
     * @since 4.4.2
     */
    public static boolean del(Path path) throws IORuntimeException {
        if (Files.notExists(path)) {
            return true;
        }

        try {
            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                        if (e == null) {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        } else {
                            throw e;
                        }
                    }
                });
            } else {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return true;
    }

    /**
     * 清空文件夹<br>
     * 注意：清空文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param dirPath 文件夹路径
     * @return 成功与否
     * @throws IORuntimeException IO异常
     * @since 4.0.8
     */
    public static boolean clean(String dirPath) throws IORuntimeException {
        return clean(file(dirPath));
    }

    /**
     * 清空文件夹<br>
     * 注意：清空文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param directory 文件夹
     * @return 成功与否
     * @throws IORuntimeException IO异常
     * @since 3.0.6
     */
    public static boolean clean(File directory) throws IORuntimeException {
        if (directory == null || directory.exists() == false || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        if (null != files) {
            boolean isOk;
            for (File childFile : files) {
                isOk = del(childFile);
                if (isOk == false) {
                    // 删除一个出错则本次删除任务失败
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 清理空文件夹<br>
     * 此方法用于递归删除空的文件夹，不删除文件<br>
     * 如果传入的文件夹本身就是空的，删除这个文件夹
     *
     * @param directory 文件夹
     * @return 成功与否
     * @throws IORuntimeException IO异常
     * @since 4.5.5
     */
    public static boolean cleanEmpty(File directory) throws IORuntimeException {
        if (directory == null || false == directory.exists() || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        if (ArrayUtil.isEmpty(files)) {
            // 空文件夹则删除之
            //noinspection ResultOfMethodCallIgnored
            directory.delete();
        } else {
            for (File childFile : files) {
                cleanEmpty(childFile);
            }
        }
        return true;
    }

    /**
     * 获得最后一个文件路径分隔符的位置
     *
     * @param filePath 文件路径
     * @return 最后一个文件路径分隔符的位置
     */
    public static int lastIndexOfSeparator(String filePath) {
        if (StrUtil.isNotEmpty(filePath)) {
            int i = filePath.length();
            char c;
            while (--i >= 0) {
                c = filePath.charAt(i);
                if (CharUtil.isFileSeparator(c)) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * 获得一个带缓存的写入对象
     *
     * @param path        输出路径，绝对路径
     * @param charsetName 字符集
     * @param isAppend    是否追加
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedWriter getWriter(String path, String charsetName, boolean isAppend) throws IORuntimeException {
        return getWriter(touch(path), Charset.forName(charsetName), isAppend);
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param path     输出路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedWriter getWriter(String path, Charset charset, boolean isAppend) throws IORuntimeException {
        return getWriter(touch(path), charset, isAppend);
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param file        输出文件
     * @param charsetName 字符集
     * @param isAppend    是否追加
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedWriter getWriter(File file, String charsetName, boolean isAppend) throws IORuntimeException {
        return getWriter(file, Charset.forName(charsetName), isAppend);
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param file     输出文件
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedWriter getWriter(File file, Charset charset, boolean isAppend) throws IORuntimeException {
        return FileWriter.create(file, charset).getWriter(isAppend);
    }

    /**
     * 获得一个打印写入对象，可以有print
     *
     * @param path     输出路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return 打印对象
     * @throws IORuntimeException IO异常
     */
    public static PrintWriter getPrintWriter(String path, String charset, boolean isAppend) throws IORuntimeException {
        return new PrintWriter(getWriter(path, charset, isAppend));
    }

    /**
     * 获得一个打印写入对象，可以有print
     *
     * @param path     输出路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return 打印对象
     * @throws IORuntimeException IO异常
     * @since 4.1.1
     */
    public static PrintWriter getPrintWriter(String path, Charset charset, boolean isAppend) throws IORuntimeException {
        return new PrintWriter(getWriter(path, charset, isAppend));
    }

    /**
     * 获得一个打印写入对象，可以有print
     *
     * @param file     文件
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return 打印对象
     * @throws IORuntimeException IO异常
     */
    public static PrintWriter getPrintWriter(File file, String charset, boolean isAppend) throws IORuntimeException {
        return new PrintWriter(getWriter(file, charset, isAppend));
    }

    /**
     * 获取Web项目下的web root路径<br>
     * 原理是首先获取ClassPath路径，由于在web项目中ClassPath位于 WEB-INF/classes/下，故向上获取两级目录即可。
     *
     * @return web root路径
     * @since 4.0.13
     */
    public static File getWebRoot() {
        final String classPath = ClassUtil.getClassPath();
        if (StrUtil.isNotBlank(classPath)) {
            return getParent(file(classPath), 2);
        }
        return null;
    }

    /**
     * 获取指定层级的父路径
     *
     * <pre>
     * getParent("d:/aaa/bbb/cc/ddd", 0) -》 "d:/aaa/bbb/cc/ddd"
     * getParent("d:/aaa/bbb/cc/ddd", 2) -》 "d:/aaa/bbb"
     * getParent("d:/aaa/bbb/cc/ddd", 4) -》 "d:/"
     * getParent("d:/aaa/bbb/cc/ddd", 5) -》 null
     * </pre>
     *
     * @param filePath 目录或文件路径
     * @param level    层级
     * @return 路径File，如果不存在返回null
     * @since 4.1.2
     */
    public static String getParent(String filePath, int level) {
        final File parent = getParent(file(filePath), level);
        try {
            return null == parent ? null : parent.getCanonicalPath();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取指定层级的父路径
     *
     * <pre>
     * getParent(file("d:/aaa/bbb/cc/ddd", 0)) -》 "d:/aaa/bbb/cc/ddd"
     * getParent(file("d:/aaa/bbb/cc/ddd", 2)) -》 "d:/aaa/bbb"
     * getParent(file("d:/aaa/bbb/cc/ddd", 4)) -》 "d:/"
     * getParent(file("d:/aaa/bbb/cc/ddd", 5)) -》 null
     * </pre>
     *
     * @param file  目录或文件
     * @param level 层级
     * @return 路径File，如果不存在返回null
     * @since 4.1.2
     */
    public static File getParent(File file, int level) {
        if (level < 1 || null == file) {
            return file;
        }

        File parentFile;
        try {
            parentFile = file.getCanonicalFile().getParentFile();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (1 == level) {
            return parentFile;
        }
        return getParent(parentFile, level - 1);
    }
}
