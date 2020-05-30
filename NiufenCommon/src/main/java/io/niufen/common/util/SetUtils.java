package io.niufen.common.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Set 相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/12
 * @time 10:03
 */
public class SetUtils {

//    ————————————————————————————————— new —————————————————————————————————————

    /**
     * 创建一个无元素的 HashSet 并返回
     *
     * @param <E> 泛型
     * @return empty HashSet
     */
    public static <E> HashSet<E> newSet() {
        return newHashSet();
    }

    /**
     * 创建一个无元素的 HashSet 并返回
     *
     * @param <E> 泛型
     * @return empty HashSet
     */
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }

    /**
     * 创建一个无元素的 LinkedHashSet 并返回
     *
     * @param <E> 泛型
     * @return empty HashSet
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    /**
     * 新建一个 非排序的 HashSet
     *
     * @param elements 多参数
     * @param <E>      泛型
     * @return 返回一个创建好的 HashSet
     */
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
        return newSet(Boolean.FALSE, elements);
    }

    /**
     * 新建一个 非排序的 HashSet
     *
     * @param collection 集合
     * @param <E>        泛型
     * @return 返回一个创建好的 HashSet
     */
    public static <E> HashSet<E> newHashSet(Collection<E> collection) {
        return newSet(Boolean.FALSE, collection);
    }

    /**
     * 新建一个 非排序的 HashSet
     *
     * @param iterator 迭代器
     * @param <E>      泛型
     * @return 返回一个创建好的 HashSet
     */
    public static <E> HashSet<E> newHashSet(Iterator<E> iterator) {
        return newSet(Boolean.FALSE, iterator);
    }

    /**
     * 新建一个 排序的 LinkedHashSet
     *
     * @param elements 多参数
     * @param <E>      泛型
     * @return 返回一个创建好的 HashSet
     */
    @SafeVarargs
    public static <E> LinkedHashSet<E> newLinkedHashSet(E... elements) {
        return (LinkedHashSet<E>) newSet(Boolean.TRUE, elements);
    }

    /**
     * 新建一个 排序的 LinkedHashSet
     *
     * @param iterator 迭代器
     * @param <E>      泛型
     * @return 返回一个创建好的 HashSet
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterator<E> iterator) {
        return (LinkedHashSet<E>) newSet(Boolean.TRUE, iterator);
    }

    /**
     * 新建一个 排序的 LinkedHashSet
     *
     * @param collection 集合
     * @param <E>        泛型
     * @return 返回一个创建好的 HashSet
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(Collection<E> collection) {
        return (LinkedHashSet<E>) newSet(Boolean.TRUE, collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <E>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param iter     {@link Iterator}
     * @return HashSet对象
     * @since 3.0.8
     */
    public static <E> HashSet<E> newSet(boolean isSorted, Iterator<E> iter) {
        if (null == iter) {
            return newSet(isSorted, (E[]) null);
        }
        final HashSet<E> set = isSorted ? new LinkedHashSet<>() : new HashSet<>();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    /**
     * 通过多参数，直接创建一个 HashSet
     * 如果 isSorted 是 true，那么创建一个 LinkedHashSet
     * 如果 isSorted 是 false, 那么创建一个 HashSet
     *
     * @param isSorted 是否按添加顺序有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param elements 数组
     * @param <E>      set 中的 <泛型>元素
     * @return 返回一个创建好的 HashSet
     */
    @SafeVarargs
    public static <E> HashSet<E> newSet(boolean isSorted, E... elements) {
        return newSet(isSorted, Arrays.asList(elements));
    }

    /**
     * 通过多参数，直接创建一个 HashSet
     * 如果 isSorted 是 true，那么创建一个 LinkedHashSet
     * 如果 isSorted 是 false, 那么创建一个 HashSet
     *
     * @param isSorted   是否按添加顺序有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param collection 集合
     * @param <E>        set 中的 <泛型>元素
     * @return 返回一个创建好的 HashSet
     */
    public static <E> HashSet<E> newSet(boolean isSorted, Collection<E> collection) {
        if (null == collection || collection.size() == 0) {
            return isSorted ? new LinkedHashSet<>() : new HashSet<>();
        }

        int initialCapacity = Math.max((int) (collection.size() / .75f) + 1, 16);
        final HashSet<E> set = isSorted ? new LinkedHashSet<>(initialCapacity) : new HashSet<>(initialCapacity);
        set.addAll(collection);
        return set;
    }

}
