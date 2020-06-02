package io.niufen.common.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @Description JSON 格式化 相关 工具类
 * @Author haijun.zhang@luckincoffee.com
 * @Date 2019-06-04 16:48:56
 */
public class JsonUtil {

    /**
     * 格式化 JSON 字符串
     *
     * @param resString 未格式化之前的 JSON 字符串
     * @return 格式化后的JSON 字符串
     */
    public static String format(String resString) {

        StringBuilder jsonForMatStr = new StringBuilder();
        int level = 0;
        ////将字符串中的字符逐个按行输出
        for (int index = 0; index < resString.length(); index++)
        {
            //获取s中的每个字符
            char c = resString.charAt(index);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }


    /**
     * 获取 JSON 节点层级
     *
     * @param level 层级
     * @return 对应层级字符串
     */
    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    /**
     * 将对象转为json串
     *
     * @param object 对象
     * @return json
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 将json字符串转为对象
     *
     * @param json  json
     * @param clazz 对象class
     * @param <T>   对象实际类型
     * @return 对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 解析数组json串，为list对象
     *
     * @param json  json串
     * @param clazz list元素class类型
     * @param <T>
     * @return list
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }


}
