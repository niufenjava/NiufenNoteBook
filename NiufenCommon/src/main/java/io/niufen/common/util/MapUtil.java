package io.niufen.common.util;

import io.niufen.common.lang.Editor;
import io.niufen.common.lang.Filter;
import io.niufen.common.map.CamelCaseLinkedMap;
import io.niufen.common.map.CamelCaseMap;

import java.util.*;

/**
 * @Description Map工具类
 * @Author haijun.zhang@luckincoffee.com
 * @Date 2019-06-02 22:55:33
 */
public class MapUtil {

    /**
     * map默认的 capacity
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 默认增长因子，当Map的size达到 容量*增长因子时，开始扩充Map
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static String getStr(Map<?, ?> map, Object key) {
        return get(map, key, String.class);
    }
    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Integer getInt(Map<?, ?> map, Object key) {
        return get(map, key, Integer.class);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param <T>  目标值类型
     * @param map  Map
     * @param key  键
     * @param type 值类型
     * @return 值
     * @since 4.0.6
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type) {
        return null == map ? null : Convert.convert(type, map.get(key));
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }
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
        if (ClassUtil.isAssignable(mapType, AbstractMap.class)) {
            return new HashMap<>();
        } else {
            return (Map<K, V>) ReflectUtil.newInstance(mapType);
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
        Map<String, Object> map = MapUtil.newMap();
        map.put(key, value);
        return map;
    }


    /**
     * 新建一个HashMap
     *
     * @param <K>     Key类型
     * @param <V>     Value类型
     * @param size    初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @param isOrder Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     * @since 3.0.4
     */
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isOrder) {
        int initialCapacity = (int) (size / DEFAULT_LOAD_FACTOR) + 1;
        return isOrder ? new LinkedHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>     Key类型
     * @param <V>     Value类型
     * @param isOrder Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(boolean isOrder) {
        return newHashMap(DEFAULT_INITIAL_CAPACITY, isOrder);
    }
    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param comparator Key比较器
     * @return TreeMap
     * @since 3.2.3
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map
     * @param comparator Key比较器
     * @return TreeMap
     * @since 3.2.3
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Map<K, V> map, Comparator<? super K> comparator) {
        final TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (false == isEmpty(map)) {
            treeMap.putAll(map);
        }
        return treeMap;
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

    /**
     * 创建Map<br>
     * 传入抽象Map{@link AbstractMap}和{@link Map}类将默认创建{@link HashMap}
     *
     * @param <K>     map键类型
     * @param <V>     map值类型
     * @param mapType map类型
     * @return {@link Map}实例
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> createMap(Class<?> mapType) {
        if (mapType.isAssignableFrom(AbstractMap.class)) {
            return new HashMap<>();
        } else {
            return (Map<K, V>) ReflectUtil.newInstance(mapType);
        }
    }


    /**
     * Map的键和值互换<br>
     * 互换键值对不检查值是否有重复，如果有则后加入的元素替换先加入的元素<br>
     * 值的顺序在HashMap中不确定，所以谁覆盖谁也不确定，在有序的Map中按照先后顺序覆盖，保留最后的值
     *
     * @param <K> 键和值类型
     * @param <V> 键和值类型
     * @param map Map对象，键值类型必须一致
     * @return 互换后的Map
     * @since 5.2.6
     */
    public static <K, V> Map<V, K> inverse(Map<K, V> map) {
        final Map<V, K> result = createMap(map.getClass());
        map.forEach((key, value) -> result.put(value, key));
        return result;
    }


    /**
     * 将map转成字符串
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @param otherParams       其它附加参数字符串（例如密钥）
     * @return 连接字符串
     * @since 3.1.1
     */
    public static <K, V> String join(Map<K, V> map, String separator, String keyValueSeparator, String... otherParams) {
        return join(map, separator, keyValueSeparator, false, otherParams);
    }

    /**
     * 根据参数排序后拼接为字符串，常用于签名
     *
     * @param params            参数
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @param isIgnoreNull      是否忽略null的键和值
     * @param otherParams       其它附加参数字符串（例如密钥）
     * @return 签名字符串
     * @since 5.0.4
     */
    public static String sortJoin(Map<?, ?> params, String separator, String keyValueSeparator, boolean isIgnoreNull,
                                  String... otherParams) {
        return join(sort(params), separator, keyValueSeparator, isIgnoreNull, otherParams);
    }

    /**
     * 将map转成字符串，忽略null的键和值
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @param otherParams       其它附加参数字符串（例如密钥）
     * @return 连接后的字符串
     * @since 3.1.1
     */
    public static <K, V> String joinIgnoreNull(Map<K, V> map, String separator, String keyValueSeparator, String... otherParams) {
        return join(map, separator, keyValueSeparator, true, otherParams);
    }

    /**
     * 将map转成字符串
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map，为空返回otherParams拼接
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @param isIgnoreNull      是否忽略null的键和值
     * @param otherParams       其它附加参数字符串（例如密钥）
     * @return 连接后的字符串，map和otherParams为空返回""
     * @since 3.1.1
     */
    public static <K, V> String join(Map<K, V> map, String separator, String keyValueSeparator, boolean isIgnoreNull, String... otherParams) {
        final StringBuilder strBuilder = StrUtil.builder();
        boolean isFirst = true;
        if (isNotEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (false == isIgnoreNull || entry.getKey() != null && entry.getValue() != null) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        strBuilder.append(separator);
                    }
                    strBuilder.append(Convert.toStr(entry.getKey())).append(keyValueSeparator).append(Convert.toStr(entry.getValue()));
                }
            }
        }
        // 补充其它字符串到末尾，默认无分隔符
        if (ArrayUtil.isNotEmpty(otherParams)) {
            for (String otherParam : otherParams) {
                strBuilder.append(otherParam);
            }
        }
        return strBuilder.toString();
    }


    /**
     * 排序已有Map，Key有序的Map，使用默认Key排序方式（字母顺序）
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @return TreeMap
     * @see #newTreeMap(Map, Comparator)
     * @since 4.0.1
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map) {
        return sort(map, null);
    }

    /**
     * 排序已有Map，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map，为null返回null
     * @param comparator Key比较器
     * @return TreeMap，map为null返回null
     * @see #newTreeMap(Map, Comparator)
     * @since 4.0.1
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
        if (null == map) {
            return null;
        }

        TreeMap<K, V> result;
        if (map instanceof TreeMap) {
            // 已经是可排序Map，此时只有比较器一致才返回原map
            result = (TreeMap<K, V>) map;
            if (null == comparator || comparator.equals(result.comparator())) {
                return result;
            }
        } else {
            result = newTreeMap(map, comparator);
        }

        return result;
    }

    /**
     * 获取Map的部分key生成新的Map
     *
     * @param <K>  Key类型
     * @param <V>  Value类型
     * @param map  Map
     * @param keys 键列表
     * @return 新Map，只包含指定的key
     * @since 4.0.6
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getAny(Map<K, V> map, final K... keys) {
        return filter(map, (Filter<Map.Entry<K, V>>) entry -> ArrayUtil.contains(keys, entry.getKey()));
    }
// ----------------------------------------------------------------------------------------------- filter

    /**
     * 过滤<br>
     * 过滤过程通过传入的Editor实现来返回需要的元素内容，这个Editor实现可以实现以下功能：
     *
     * <pre>
     * 1、过滤出需要的对象，如果返回null表示这个元素对象抛弃
     * 2、修改元素对象，返回集合中为修改后的对象
     * </pre>
     *
     * @param <K>    Key类型
     * @param <V>    Value类型
     * @param map    Map
     * @param editor 编辑器接口
     * @return 过滤后的Map
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, Editor<Map.Entry<K, V>> editor) {
        if (null == map || null == editor) {
            return map;
        }

        final Map<K, V> map2 = ObjectUtil.clone(map);
        if (isEmpty(map2)) {
            return map2;
        }

        map2.clear();
        Map.Entry<K, V> modified;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            modified = editor.edit(entry);
            if (null != modified) {
                map2.put(modified.getKey(), modified.getValue());
            }
        }
        return map2;
    }


    /**
     * 过滤<br>
     * 过滤过程通过传入的Editor实现来返回需要的元素内容，这个Filter实现可以实现以下功能：
     *
     * <pre>
     * 1、过滤出需要的对象，如果返回null表示这个元素对象抛弃
     * </pre>
     *
     * @param <K>    Key类型
     * @param <V>    Value类型
     * @param map    Map
     * @param filter 编辑器接口
     * @return 过滤后的Map
     * @since 3.1.0
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, Filter<Map.Entry<K, V>> filter) {
        if (null == map || null == filter) {
            return map;
        }

        final Map<K, V> map2 = ObjectUtil.clone(map);
        if (isEmpty(map2)) {
            return map2;
        }

        map2.clear();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filter.accept(entry)) {
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        return map2;
    }

    /**
     * 过滤Map保留指定键值对，如果键不存在跳过
     *
     * @param <K>  Key类型
     * @param <V>  Value类型
     * @param map  原始Map
     * @param keys 键列表
     * @return Map 结果，结果的Map类型与原Map保持一致
     * @since 4.0.10
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> filter(Map<K, V> map, K... keys) {
        final Map<K, V> map2 = ObjectUtil.clone(map);
        if (isEmpty(map2)) {
            return map2;
        }

        map2.clear();
        for (K key : keys) {
            if (map.containsKey(key)) {
                map2.put(key, map.get(key));
            }
        }
        return map2;
    }
    /**
     * Map的键和值互换
     * 互换键值对不检查值是否有重复，如果有则后加入的元素替换先加入的元素<br>
     * 值的顺序在HashMap中不确定，所以谁覆盖谁也不确定，在有序的Map中按照先后顺序覆盖，保留最后的值
     *
     * @param <T> 键和值类型
     * @param map Map对象，键值类型必须一致
     * @return 互换后的Map
     * @see #inverse(Map)
     * @since 3.2.2
     */
    public static <T> Map<T, T> reverse(Map<T, T> map) {
        return filter(map, (Editor<Map.Entry<T, T>>) t -> new Map.Entry<T, T>() {

            @Override
            public T getKey() {
                return t.getValue();
            }

            @Override
            public T getValue() {
                return t.getKey();
            }

            @Override
            public T setValue(T value) {
                throw new UnsupportedOperationException("Unsupported setValue method !");
            }
        });
    }
    /**
     * 将已知Map转换为key为驼峰风格的Map<br>
     * 如果KEY为非String类型，保留原值
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map 原Map
     * @return 驼峰风格Map
     * @since 3.3.1
     */
    public static <K, V> Map<K, V> toCamelCaseMap(Map<K, V> map) {
        return (map instanceof LinkedHashMap) ? new CamelCaseLinkedMap<>(map) : new CamelCaseMap<>(map);
    }


    /**
     * 将对应Map转换为不可修改的Map
     *
     * @param map Map
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不修改Map
     * @since 5.2.6
     */
    public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
        return Collections.unmodifiableMap(map);
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
