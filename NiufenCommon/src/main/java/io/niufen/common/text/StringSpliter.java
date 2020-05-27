package io.niufen.common.text;

import io.niufen.common.constant.IntConstants;
import io.niufen.common.lang.PatternPool;
import io.niufen.common.util.CharUtils;
import io.niufen.common.util.ListUtils;
import io.niufen.common.util.NumberUtils;
import io.niufen.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串切分器
 *
 * @author haijun.zhang
 * @date 2020/5/26
 * @time 21:00
 */
public class StringSpliter {

    /**
     * 当前系统路径分隔符
     */
    private static final char SYSTEM_FILE_SEPARATOR = java.io.File.separatorChar;

    /**
     * -1；表示不进行分片
     */
    private static final int NONE_LIMIT_INT = IntConstants.ONE_MINUS;

    //-------- Split by Char ---------

    /**
     * 切分字符串路径，根据当前系统的默认路径进行切分
     *
     * @param path 系统文件或文件夹路径
     * @return 切分后的集合
     */
    public static List<String> splitPath(String path) {
        return splitPath(path, IntConstants.ZERO);
    }

    /**
     * 切分字符串路径，根据当前系统的默认路径进行切分
     * 返回字符串数组
     *
     * @param path 系统文件或文件夹路径
     * @return 切分后的字符串数组
     */
    public static String[] splitPathToArray(String path) {
        return toArray(splitPath(path));
    }

    /**
     * 切分字符串路径，根据当前系统的默认路径进行切分
     *
     * @param path  系统文件或文件夹路径
     * @param limit 限制分片数
     * @return 切分后的集合
     */
    public static List<String> splitPath(String path, int limit) {
        return split(path, SYSTEM_FILE_SEPARATOR, limit, Boolean.TRUE, Boolean.TRUE);
    }

    /**
     * 切分字符串路径，根据当前系统的默认路径进行切分
     * 返回字符串数组
     *
     * @param path  系统文件或文件夹路径
     * @param limit 限制分片数
     * @return 切分后的字符串数组
     */
    public static String[] splitPathToArray(String path, int limit) {
        return toArray(splitPath(path, limit));
    }

    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, char separator, boolean ignoreEmpty) {
        return split(str, separator, IntConstants.ZERO, Boolean.TRUE, ignoreEmpty);
    }


    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, IntConstants.ZERO, isTrim, ignoreEmpty);
    }


    /**
     * 切分字符串，大小写敏感，去除每个元素两边空白符
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, char separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, Boolean.TRUE, ignoreEmpty, Boolean.FALSE);
    }

    /**
     * 切分字符串，大小写敏感
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数 < 0 不限制
     * @param isTrim      是否去除切分字符串后每个元素离岸边的空格
     * @param ignoreEmpty 是否忽略空字符
     * @return 切分后的 List 集合
     */
    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, Boolean.FALSE);
    }

    /**
     * 切分字符串，忽略大小写
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> splitIgnoreCase(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, Boolean.TRUE);
    }


    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1 0 不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (StringUtils.isEmpty(str)) {
            return ListUtils.newList(IntConstants.ZERO);
        }

        // 如果限制分片数为 1 ，那么直接把字符串放进去，不进行切分
        if (limit == IntConstants.ONE) {
            return addToList(ListUtils.newList(IntConstants.ONE), str, isTrim, ignoreEmpty);
        }
        int initialCapacity = (limit > 0 ? limit : ListUtils.defaultInitialCapacity);
        // 使用final关键字修饰一个变量时，是指引用变量不能指向别的对象了，
        // 引用变量所指向的对象中的内容还是可以改变的。
        final List<String> list = ListUtils.newList(initialCapacity);

        int strLength = str.length();

        // 切分后每个部分的起始
        int start = IntConstants.ZERO;
        for (int i = 0; i < strLength; i++) {
            // 对每一个字符串字符进行遍历，如果与 分隔符字符 separator 相同，则进行切割，并且加入 list 中
            if (NumberUtils.equals(separator, str.charAt(i), ignoreCase)) {
                addToList(list, StringUtils.sub(str, start, i), isTrim, ignoreEmpty);
                // i+1 同时将start与i保持一致，下次开始的起始位置
                start = i + 1;

                // 检查是否超出范围（最大运行 limit-1个，剩下一个留给末尾字符串)
                if (limit > 0 && list.size() > (limit - 2)) {
                    break;
                }
            }
        }
        //收尾
        return addToList(list, str.substring(start, strLength), isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串为字符串数组
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static String[] splitToArray(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    //-------- Split by String ---------

    /**
     * 切分字符串，不忽略大小写
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符串
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> split(String str, String separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, NONE_LIMIT_INT, isTrim, ignoreEmpty, Boolean.FALSE);
    }

    /**
     * 切分字符串，去除每个元素两边的空格，忽略大小写
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符串
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, String separator, boolean ignoreEmpty) {
        return split(str, separator, Boolean.TRUE, ignoreEmpty);
    }

    /**
     * 切分字符串，不忽略大小写
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符串
     * @param limit       限制分片数
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, Boolean.FALSE);
    }

    /**
     * 切分字符串，去除每个元素两边空格，忽略大小写
     *
     * @param str         被切分后的字符串
     * @param separator   分隔符字符串
     * @param limit       限制分片数
     * @param ignoreEmpty 是否忽略空字符串
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, Boolean.TRUE, ignoreEmpty);
    }

    /**
     * 切分字符串，忽略大小写
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符串
     * @param limit       限制分片数
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> splitIgnoreCase(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, Boolean.TRUE);
    }

    /**
     * 切分字符串，去除每个元素两边空格，忽略大小写
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符串
     * @param limit       限制分片数
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> splitTrimIgnoreCase(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, Boolean.TRUE, ignoreEmpty, Boolean.TRUE);
    }


    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符传
     * @param limit       限制分片数，-1 0 不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @return 切分后的集合
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (StringUtils.isEmpty(str)) {
            return ListUtils.newList(IntConstants.ZERO);
        }

        if (limit == IntConstants.ONE) {
            return addToList(ListUtils.newList(IntConstants.ONE), str, isTrim, ignoreEmpty);
        }

        if (StringUtils.isEmpty(separator)) {
            return split(str, limit);
        } else if (separator.length() == 1) {
            return split(str, separator.charAt(0), limit, isTrim, ignoreEmpty, ignoreCase);
        }

        final List<String> list = ListUtils.newList();

        int separatorLen = separator.length();
        int strLength = str.length();
        int start = 0;
        int i = 0;

        while (i < strLength) {
            i = StringUtils.indexOf(str, separator, start, ignoreCase);
            if (i > -1) {
                addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
                start = i + separatorLen;

                //检查是否超出范围（最大运行limit-1)个，剩下一个留给末尾字符串
                if (limit > 0 && list.size() > (limit - 1)) {
                    break;
                }
            } else {
                break;
            }
        }

        //收尾
        return addToList(list, str.substring(start, strLength), isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串为字符串数组
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static String[] splitToArray(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    //-------- Split by Whitespace ---------

    /**
     * 使用空白符切分字符串
     * "a b  cd" => {"a","b","cd"}
     * 切分后的字符串两边不包含空白符，空串或空白符串并不作为元素之一
     *
     * @param str   被切分的字符串
     * @param limit 限制分片数
     */
    public static List<String> split(String str, int limit) {
        if (StringUtils.isEmpty(str)) {
            return ListUtils.newList(IntConstants.ZERO);
        }

        // 如果限制分片数为 1 ，那么直接把字符串放进去，不进行切分
        if (limit == 1) {
            return addToList(ListUtils.newList(IntConstants.ONE), str, Boolean.TRUE, Boolean.TRUE);
        }
        int initialCapacity = str.length();
        final List<String> list = ListUtils.newList(initialCapacity);

        int strLength = str.length();

        // 切分后每个部分的起始
        int start = IntConstants.ZERO;
        for (int i = 0; i < strLength; i++) {
            // 对每一个字符串字符进行遍历，如果与 分隔符字符 separator 相同，则进行切割，并且加入 list 中
            if (CharUtils.isBlankChar(str.charAt(i))) {
                addToList(list, StringUtils.sub(str, start, i), Boolean.TRUE, Boolean.TRUE);
                // i+1 同时将start与i保持一致，下次开始的起始位置
                start = i + 1;
                // 检查是否超出范围（最大运行 limit-1个，剩下一个留给末尾字符串)
                if (limit > 0 && list.size() > (limit - 2)) {
                    break;
                }
            }
        }
        //收尾
        return addToList(list, str.substring(start, strLength), Boolean.TRUE, Boolean.TRUE);
    }

    /**
     * 切分字符串为字符串数组
     *
     * @param str   被切分的字符串
     * @param limit 限制分片数
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, int limit) {
        return toArray(split(str, limit));
    }


    //---------------------------------------------------------------------------------------------- Split by regex

    /**
     * 通过正则切分字符串
     *
     * @param str            字符串
     * @param separatorRegex 分隔符正则
     * @param limit          限制分片数
     * @param isTrim         是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty    是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static List<String> splitByRegex(String str, String separatorRegex, int limit, boolean isTrim, boolean ignoreEmpty) {
        final Pattern pattern = PatternPool.get(separatorRegex);
        return split(str, pattern, limit, isTrim, ignoreEmpty);
    }

    /**
     * 通过正则切分字符串
     *
     * @param str              字符串
     * @param separatorPattern 分隔符正则{@link Pattern}
     * @param limit            限制分片数
     * @param isTrim           是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty      是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static List<String> split(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<>(1), str, isTrim, ignoreEmpty);
        }

        if (null == separatorPattern) {//分隔符为空时按照空白符切分
            return split(str, limit);
        }

        final Matcher matcher = separatorPattern.matcher(str);
        final ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int start = 0;
        while (matcher.find()) {
            addToList(list, str.substring(start, matcher.start()), isTrim, ignoreEmpty);
            start = matcher.end();

            //检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
            if (limit > 0 && list.size() > limit - 2) {
                break;
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    /**
     * 通过正则切分字符串为字符串数组
     *
     * @param str              被切分的字符串
     * @param separatorPattern 分隔符正则{@link Pattern}
     * @param limit            限制分片数
     * @param isTrim           是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty      是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separatorPattern, limit, isTrim, ignoreEmpty));
    }

    //-------- Split Tool  ---------

    /**
     * 将字符串加入给定的 list 中
     *
     * @param list        集合
     * @param part        被加入的部分
     * @param isTrim      是否去除两端空白符
     * @param ignoreEmpty 是否略过空字符串（空字符串不做为一个元素）
     * @return 列表
     */
    private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty) {
        if (isTrim) {
            part = StringUtils.trim(part);
        }
        if (ignoreEmpty && StringUtils.isEmpty(part)) {
            return list;
        }
        list.add(part);
        return list;
    }


    /**
     * List转Array
     *
     * @param list List
     * @return Array
     */
    private static String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }
}
