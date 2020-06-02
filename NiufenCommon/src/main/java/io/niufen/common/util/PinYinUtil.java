package io.niufen.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 14:45
 */
public class PinYinUtil {

    /**
     * 汉字转为拼音
     * @param cnStr 中文
     * @return 拼音
     */
    public static String toPinYin(String cnStr){
        StringBuilder pinyinStr = new StringBuilder();
        char[] charArray = cnStr.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : charArray) {
            try {
                pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString();
    }
}
