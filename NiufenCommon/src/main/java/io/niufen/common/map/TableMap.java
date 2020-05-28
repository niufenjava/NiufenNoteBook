package io.niufen.common.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 可重复键和值的Map<br/>
 * 通过键值单独建立 List 方法，使键值对意义对应，事项正向和反向两种查询<br>
 * 无论是正向还是泛型，都是遍历列表查找过程，相比标准的 HashMap 要慢，数据越多越慢
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author haijun.zhang
 * @date 2020/5/27
 * @time 22:27
 */
public class TableMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>>, Serializable {
    private static final long serialVersionUID = 7695638910301575119L;

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
