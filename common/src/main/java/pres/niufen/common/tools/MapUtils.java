package pres.niufen.common.tools;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Description Map工具类
 * @Author haijun.zhang@luckincoffee.com
 * @Date 2019-06-02 22:55:33
 */
public class MapUtils {

    /**
     * map默认的 capacity
     */
    public static final int MAP_INITIAL_CAPACITY = 16;

    /**
     * 根据key、value获取唯一的map对象
     *
     * @param key
     * @param value
     * @return
     */
    public static Map getOneItemMap(String key, String value) {
        Map map = MapUtils.newMap();
        map.put(key, value);
        return map;
    }


    /**
     * 返回 一个<String,Object> 的LinkedHashMap
     *
     * @return new LinkedHashMap()
     */
    public static LinkedHashMap<String, Object> newLinkedMap() {
        return new LinkedHashMap();
    }


    /**
     * 返回 一个<String,Object> 的 Map 初始化容量为 16
     *
     * @return new HashMap()
     */
    public static HashMap<String, Object> newMap() {
        return new HashMap<>(16);
    }


//    /**
//     * 将任意vo转化成map
//     *
//     * @param t vo对象
//     * @return
//     */
//    public static <T> Map<String, Object> convert2Map(T t) {
//        Map<String, Object> params = (JSONObject) JSON.toJSON(t);
//        Integer pageNo = ObjectTool.toInteger(params.get("pageNo"));
//        Integer pageSize = ObjectTool.toInteger(params.get("pageSize"));
//        if (ObjectTool.isNotNull(pageNo) && ObjectTool.isNotNull(pageSize)) {
//            params.put("pageNo", getStartIndex(pageNo, pageSize));
//        }
//        return params;
//    }


    /**
     * 获取数据开始位置
     *
     * @return 开始位置
     */
    public static int getStartIndex(Integer pageNo, Integer pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }


    /**
     * 获取数据结束位置
     *
     * @return 结束位置
     */
    public static int getEndIndex(Integer pageNo, Integer pageSize) {
        return pageNo * pageSize;
    }


    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<Integer,String> sortMapByKey(Map<Integer,String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Integer,String> sortMap = new TreeMap<Integer,String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

}

/**
 * 比较器类
 */
class MapKeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer str1, Integer str2) {
        return str1.compareTo(str2);
    }


}
