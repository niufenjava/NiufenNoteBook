package io.niufen.common.util;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * List 相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/12
 * @time 10:03
 */
public class ListUtils {
    /**
     * 默认 List容量大小
     */
    public static final int defaultInitialCapacity = 16;
//    ————————————————————————————————— new —————————————————————————————————————

    /**
     * 创建一个无元素的 ArrayList 并返回
     *
     * @param <E> 泛型
     * @return empty ArrayList
     */
    public static <E> ArrayList<E> newList() {
        return newArrayList();
    }
    /**
     * 创建一个无元素的 ArrayList 并返回
     *
     * @param <E> 泛型
     * @return empty ArrayList
     */
    public static <E> ArrayList<E> newList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }


    /**
     * 创建一个无元素的 ArrayList 并返回
     *
     * @param <E> 泛型
     * @return empty ArrayList
     */
    @SafeVarargs
    public static <E> ArrayList<E> newList(E... elements) {
        return newArrayList(elements);
    }

    /**
     * 新建一个空List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> newList(boolean isLinked) {
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }

    /**
     * 创建一个无元素的 ArrayList 并返回
     *
     * @param <E> 泛型
     * @return empty ArrayList
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 创建一个无元素的 LinkedList 并返回
     *
     * @param <E> 泛型
     * @return empty ArrayList
     */
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    /**
     * 新建一个 非排序的 ArrayList
     *
     * @param elements 多参数
     * @param <E>      泛型
     * @return 返回一个创建好的 ArrayList
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return (ArrayList<E>) newList(Boolean.FALSE, elements);
    }

    /**
     * 新建一个 非排序的 ArrayList
     *
     * @param collection 集合
     * @param <E>        泛型
     * @return 返回一个创建好的 ArrayList
     */
    public static <E> ArrayList<E> newArrayList(Collection<E> collection) {
        return (ArrayList<E>) newList(Boolean.FALSE, collection);
    }

    /**
     * 新建一个 非排序的 ArrayList
     *
     * @param iterator 迭代器
     * @param <E>      泛型
     * @return 返回一个创建好的 ArrayList
     */
    public static <E> ArrayList<E> newArrayList(Iterator<E> iterator) {
        return (ArrayList<E>) newList(Boolean.FALSE, iterator);
    }

    /**
     * 新建一个 排序的 LinkedList
     *
     * @param elements 多参数
     * @param <E>      泛型
     * @return 返回一个创建好的 ArrayList
     */
    @SafeVarargs
    public static <E> LinkedList<E> newLinkedList(E... elements) {
        return (LinkedList<E>) newList(Boolean.TRUE, elements);
    }

    /**
     * 新建一个 排序的 LinkedList
     *
     * @param iterator 迭代器
     * @param <E>      泛型
     * @return 返回一个创建好的 ArrayList
     */
    public static <E> LinkedList<E> newLinkedList(Iterator<E> iterator) {
        return (LinkedList<E>) newList(Boolean.TRUE, iterator);
    }

    /**
     * 新建一个 排序的 LinkedList
     *
     * @param collection 集合
     * @param <E>        泛型
     * @return 返回一个创建好的 ArrayList
     */
    public static <E> LinkedList<E> newLinkedList(Collection<E> collection) {
        return (LinkedList<E>) newList(Boolean.TRUE, collection);
    }

    /**
     * 新建一个ArrayList
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否有序，有序返回 {@link LinkedList}，否则返回{@link ArrayList}
     * @param iterator     {@link Iterator}
     * @return ArrayList对象
     * @since 3.0.8
     */
    public static <E> List<E> newList(boolean isLinked, Iterator<E> iterator) {
        if (null == iterator) {
            return newList(isLinked);
        }
        final List<E> list = isLinked ? new LinkedList<>() : new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 通过多参数，直接创建一个 ArrayList
     * 如果 isSorted 是 true，那么创建一个 LinkedList
     * 如果 isSorted 是 false, 那么创建一个 ArrayList
     *
     * @param isLinked 是否按添加顺序有序，有序返回 {@link LinkedList}，否则返回{@link ArrayList}
     * @param elements 数组
     * @param <E>      list 中的 <泛型>元素
     * @return 返回一个创建好的 ArrayList
     */
    @SafeVarargs
    public static <E> List<E> newList(boolean isLinked, E... elements) {
        return newList(isLinked, Arrays.asList(elements));
    }

    /**
     * 通过多参数，直接创建一个 ArrayList
     * 如果 isSorted 是 true，那么创建一个 LinkedList
     * 如果 isSorted 是 false, 那么创建一个 ArrayList
     *
     * @param isLinked   是否按添加顺序有序，有序返回 {@link LinkedList}，否则返回{@link ArrayList}
     * @param collection 集合
     * @param <E>        list 中的 <泛型>元素
     * @return 返回一个创建好的 ArrayList
     */
    public static <E> List<E> newList(boolean isLinked, Collection<E> collection) {
        if (null == collection || collection.size() == 0) {
            return newList(isLinked);
        }
        final List<E> list = isLinked ? new LinkedList<>() : new ArrayList<>(collection.size());
        list.addAll(collection);
        return list;
    }

    /**
     * 新建一个CopyOnWriteArrayList
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return {@link CopyOnWriteArrayList}
     */
    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList(Collection<T> collection) {
        return (null == collection) ? (new CopyOnWriteArrayList<>()) : (new CopyOnWriteArrayList<>(collection));
    }

    /**
     * 针对List排序，排序会修改原List
     *
     * @param <T>  元素类型
     * @param list 被排序的List
     * @param c    {@link Comparator}
     * @see Collections#sort(List, Comparator)
     */
    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
    }

}
