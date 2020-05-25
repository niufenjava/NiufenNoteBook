package io.niufen.common.util;

import java.util.regex.Pattern;

/**
 * 正则表达式，数字相关校验检查类
 * @author haijun.zhang
 * @date 2020/4/28
 * @time 15:36
 */
public class RegexNumberCheckUtils {

    /**
     * 正则表达式，^[0-9]*$ 是否为数字
     */
    public static final String REGEX_IS_NUMBER = "^[0-9]*$";

    public static final Pattern REGEX_PATTEN_IS_NUMBER = Pattern.compile(REGEX_IS_NUMBER);

    /**
     * 校验传入的字符串中的字符是否都是数字
     * @param content 待验证字符串
     * @return TRUE-传入的字符串中的字符都是数字
     */
    public static Boolean isNumber(String content){
        if(MyStringUtils.isBlank(content)){
            return Boolean.FALSE;
        }
        return REGEX_PATTEN_IS_NUMBER.matcher(content).matches();
    }


    /**
     * 校验传入的字符串中的字符是否都是数字
     * @param content 待验证字符串
     * @return TRUE-传入的字符串中的字符都是数字
     */
    public static Boolean isNumberCompile(String content){
        if(MyStringUtils.isBlank(content)){
            return Boolean.FALSE;
        }
        return Pattern.matches(REGEX_IS_NUMBER, content);
    }
}
