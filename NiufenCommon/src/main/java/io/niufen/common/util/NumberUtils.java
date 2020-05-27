package io.niufen.common.util;

/**
 * 数字相关工具类
 * @author haijun.zhang
 * @date 2020/5/26
 * @time 21:12
 */
public class NumberUtils {

    /**
     * 比较两个字符是否相同
     * @param c1 字符1
     * @param c2 字符2
     * @param ignoreCase 是否忽略大小写
     * @return true-相同；false-不相同
     * @see CharUtils#equals(char, char, boolean)
     */
    public static boolean equals(char c1,char c2,boolean ignoreCase){
        return CharUtils.equals(c1,c2,ignoreCase);
    }
}
