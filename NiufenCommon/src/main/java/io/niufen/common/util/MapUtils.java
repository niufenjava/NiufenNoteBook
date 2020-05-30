package io.niufen.common.util;

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
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    /**
     * 创建 Map <br>
     * 传入抽象 Map {@link AbstractMap}和{@link Map}类将默认创建{@link HashMap}
     *
     * @param mapType map类型
     * @param <K>     map键类型
     * @param <V>     map值类型
     * @return {@link Map}实例
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> newMap(Class<?> mapType) {
        if (ClassUtils.isAssignable(mapType, AbstractMap.class)) {
            return new HashMap<>();
        } else {
            return (Map<K, V>) ReflectionUtils.newInstance(mapType);
        }
    }

    /**
     * 根据key、value获取唯一的map对象
     *
     * @param key
     * @param value
     * @return
     */
    public static Map<String, Object> getOneItemMap(String key, Object value) {
        Map<String, Object> map = MapUtils.newMap();
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
    public static Map<Integer, String> sortMapByKey(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Integer, String> sortMap = new TreeMap<Integer, String>(
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
