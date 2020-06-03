package io.niufen.common.util;

import io.niufen.common.lang.Matcher;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * List 相关工具类
 *
 * @author haijun.zhang
 * @date 2020/5/12
 * @time 10:03
 */
public class ListUtil {
    /**
     * 默认 List容量大小
     */
    public static final int defaultInitialCapacity = 16;
//    ————————————————————————————————— new —————————————————————————————————————

    /**
     * 新建一个空List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked) {
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }

    /**
     * 新建一个List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     * @since 4.1.2
     */
    @SafeVarargs
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return list(isLinked);
        }
        final List<T> arrayList = isLinked ? new LinkedList<>() : new ArrayList<>(values.length);
        Collections.addAll(arrayList, values);
        return arrayList;
    }

    /**
     * 新建一个List
     *
     * @param <T>        集合元素类型
     * @param isLinked   是否新建LinkedList
     * @param collection 集合
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked, Collection<T> collection) {
        if (null == collection) {
            return list(isLinked);
        }
        return isLinked ? new LinkedList<>(collection) : new ArrayList<>(collection);
    }
    /**
     * 新建一个ArrayList<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>      集合元素类型
     * @param iterable {@link Iterable}
     * @return ArrayList对象
     * @since 3.1.0
     */
    public static <T> ArrayList<T> toList(Iterable<T> iterable) {
        return (ArrayList<T>) list(false, iterable);
    }
    /**
     * 新建一个ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> toList(T... values) {
        return (ArrayList<T>) list(false, values);
    }

    /**
     * 新建一个List<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param iterable {@link Iterable}
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked, Iterable<T> iterable) {
        if (null == iterable) {
            return list(isLinked);
        }
        return list(isLinked, iterable.iterator());
    }

    /**
     * 新建一个ArrayList<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param iter     {@link Iterator}
     * @return ArrayList对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked, Iterator<T> iter) {
        final List<T> list = list(isLinked);
        if (null != iter) {
            while (iter.hasNext()) {
                list.add(iter.next());
            }
        }
        return list;
    }

    /**
     * 新建一个List<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>        集合元素类型
     * @param isLinked   是否新建LinkedList
     * @param enumration {@link Enumeration}
     * @return ArrayList对象
     * @since 3.0.8
     */
    public static <T> List<T> list(boolean isLinked, Enumeration<T> enumration) {
        final List<T> list = list(isLinked);
        if (null != enumration) {
            while (enumration.hasMoreElements()) {
                list.add(enumration.nextElement());
            }
        }
        return list;
    }
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

    /**
     * 截取集合的部分
     *
     * @param <T>   集合元素类型
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @return 截取后的数组，当开始位置超过最大时，返回空的List
     */
    public static <T> List<T> sub(List<T> list, int start, int end) {
        return sub(list, start, end, 1);
    }

    /**
     * 截取集合的部分
     *
     * @param <T>   集合元素类型
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @param step  步进
     * @return 截取后的数组，当开始位置超过最大时，返回空的List
     * @since 4.0.6
     */
    public static <T> List<T> sub(List<T> list, int start, int end, int step) {
        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return new ArrayList<>(0);
        }

        final int size = list.size();
        if (start < 0) {
            start += size;
        }
        if (end < 0) {
            end += size;
        }
        if (start == size) {
            return new ArrayList<>(0);
        }
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }
        if (end > size) {
            if (start >= size) {
                return new ArrayList<>(0);
            }
            end = size;
        }

        if (step <= 1) {
            return list.subList(start, end);
        }

        final List<T> result = new ArrayList<>();
        for (int i = start; i < end; i += step) {
            result.add(list.get(i));
        }
        return result;
    }
    /**
     * 设置或增加元素。当index小于List的长度时，替换指定位置的值，否则在尾部追加
     *
     * @param <T>     元素类型
     * @param list    List列表
     * @param index   位置
     * @param element 新元素
     * @return 原List
     * @since 4.1.2
     */
    public static <T> List<T> setOrAppend(List<T> list, int index, T element) {
        if (index < list.size()) {
            list.set(index, element);
        } else {
            list.add(element);
        }
        return list;
    }
    /**
     * 获取匹配规则定义中匹配到元素的所有位置
     *
     * @param <T>     元素类型
     * @param list    列表
     * @param matcher 匹配器，为空则全部匹配
     * @return 位置数组
     * @since 5.2.5
     */
    public static <T> int[] indexOfAll(List<T> list, Matcher<T> matcher) {
        final List<Integer> indexList = new ArrayList<>();
        if (null != list) {
            int index = 0;
            for (T t : list) {
                if (null == matcher || matcher.match(t)) {
                    indexList.add(index);
                }
                index++;
            }
        }
        return Convert.convert(int[].class, indexList);
    }

}
