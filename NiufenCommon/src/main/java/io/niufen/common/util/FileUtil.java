package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.io.IORuntimeException;
import io.niufen.common.io.LineHandler;
import io.niufen.common.io.file.FileMode;
import io.niufen.common.io.file.FileReader;
import io.niufen.common.lang.Assert;
import io.niufen.common.text.StringSpliter;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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

        List<String> pathList = StringSpliter.split(pathToUse, StrUtil.C_SLASH);
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

        return prefix + CollectionUtil.join(pathElements, StrUtil.SLASH);
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
}
