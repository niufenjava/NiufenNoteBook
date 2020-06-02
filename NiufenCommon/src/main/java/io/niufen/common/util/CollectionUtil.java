package io.niufen.common.util;

import io.niufen.common.collection.ArrayIterator;
import io.niufen.common.collection.EnumerationIterator;
import io.niufen.common.constant.CharConstants;
import io.niufen.common.convert.ConverterRegistry;
import io.niufen.common.exception.UtilException;
import io.niufen.common.text.StringSpliter;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Collection 容器相关工具类
 *
 * @author haijun.zhang
 * @date 2020-05-23
 */
public class CollectionUtil {

    /**
     * 校验集合是否为空
     *
     * @param coll 入参
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 校验集合是否不为空
     *
     * @param coll 入参
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断Map是否为空
     *
     * @param map 入参
     * @return boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断Map是否不为空
     *
     * @param map 入参
     * @return boolean
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断一个集合中是否存在指定元素
     *
     * @param collection 集合对象
     * @param value      集合元素
     * @param <T>        集合类型
     * @return true:存在 否则不存在
     */
    public static <T> boolean contains(Collection<T> collection, T value) {
        return !isEmpty(collection) && collection.contains(value);
    }

    /**
     * 根据比较器比较两个collection中哪些是新增的对象以及删除的对象和没有改变的对象
     *
     * @param newCollection 新list
     * @param oldCollection 旧list
     * @param comparator    集合对象比较器
     * @param <T>           集合元素泛型对象
     * @return 比较结果 {@link CompareResult}
     */
    public static <T> CompareResult<T> compare(Collection<T> newCollection, Collection<T> oldCollection, Comparator<T> comparator) {
        List<T> unmodifiedValue = new ArrayList<>();

        Iterator<T> newIte = newCollection.iterator();
        while (newIte.hasNext()) {
            T newObj = newIte.next();
            //遍历旧数组
            Iterator<T> oldIte = oldCollection.iterator();
            while (oldIte.hasNext()) {
                //如果新旧数组中的对象相同，则为没有改变的对象
                T oldObj = oldIte.next();
                if (comparator.compare(newObj, oldObj) == 0) {
                    unmodifiedValue.add(oldObj);
                    oldIte.remove();
                    newIte.remove();
                }
            }
        }

        return new CompareResult<T>(new ArrayList<>(newCollection), new ArrayList<>(oldCollection), unmodifiedValue);
    }


    /**
     * 列表比较结果对象
     *
     * @param <T>
     */
    public static class CompareResult<T> {
        /**
         * 新增的对象列表
         */
        private final List<T> addValue;
        /**
         * 删除的对象列表
         */
        private final List<T> delValue;
        /**
         * 没有改变的对象列表
         */
        private final List<T> unmodifiedValue;

        public CompareResult(List<T> addValue, List<T> delValue, List<T> unmodifiedValue) {
            this.addValue = addValue;
            this.delValue = delValue;
            this.unmodifiedValue = unmodifiedValue;
        }

        public List<T> getDelValue() {
            return delValue;
        }

        public List<T> getAddValue() {
            return addValue;
        }

        public List<T> getUnmodifiedValue() {
            return unmodifiedValue;
        }
    }

    /**
     * 创建新的集合对象
     *
     * @param collectionType 集合类型
     * @param <T>            集合类型
     * @return 集合类型对应的实例
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Collection<T> newCollection(Class<?> collectionType) {

        // 抽象集合默认使用 ArrayList
        if (ClassUtil.isAssignable(collectionType, AbstractCollection.class)) {
            return ListUtil.newArrayList();
        }
        // SET
        if (ClassUtil.isAssignable(collectionType, HashSet.class)) {
            return SetUtil.newSet();
        }
        if (ClassUtil.isAssignable(collectionType, LinkedHashSet.class)) {
            return SetUtil.newLinkedHashSet();
        }
        if (ClassUtil.isAssignable(collectionType, EnumSet.class)) {
            return (Collection<T>) EnumSet.noneOf((Class<Enum>) ClassUtil.getTypeArgument(collectionType));
        }

        // List
        if (ClassUtil.isAssignable(collectionType, ArrayList.class)) {
            return ListUtil.newArrayList();
        }
        if (ClassUtil.isAssignable(collectionType, LinkedList.class)) {
            return ListUtil.newLinkedList();
        }
        // Others，直接实例化
        try {
            return (Collection<T>) ReflectionUtil.newInstance(collectionType);
        } catch (Exception e) {
            throw new UtilException(e);
        }

    }

    /**
     * 将制定对象全部加入到集合中<br>
     * 提供的对象如果为集合类型，会自动转换为目标原始类型<br>
     * 如果为String，支持类似[1,2,3,4] 或者 1,2,3,4 这种格式
     *
     * @param collection  被加入的集合
     * @param value       对象，可能为 Iterator、Iterable、Enumeration、Array，或者与集合元素类型一致
     * @param elementType 元素类型，为空时，使用 Object 类型来接纳所有类型
     * @param <T>         元素类型
     * @return 被加入集合
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Collection<T> addAll(Collection<T> collection, Object value, Type elementType) {
        if (null == collection || null == value) {
            return collection;
        }
        if (TypeUtil.isUnknownType(elementType)) {
            elementType = Object.class;
        }

        Iterator iterator;
        if (value instanceof Iterator) {
            iterator = (Iterator) value;
        } else if (value instanceof Iterable) {
            iterator = ((Iterable) value).iterator();
        } else if (value instanceof Enumeration) {
            iterator = new EnumerationIterator<>((Enumeration) value);
        } else if (ArrayUtil.isArray(value)) {
            iterator = new ArrayIterator<>(value);
        } else if (value instanceof CharSequence) {
            // String 按照逗号分隔的列表对待
            final String arrayStr = StrUtil.unWrap((CharSequence) value, '[', ']');
            // todo iter = StrUtil.splitTrim(ArrayStr, CharUtil.COMMA).iterator();
            iterator = StringSpliter.splitTrim(arrayStr, CharConstants.COMMA).iterator();
        } else {
            // 其它类型按照单一元素处理
            iterator = ListUtil.newArrayList(value).iterator();
        }
        final ConverterRegistry converter = ConverterRegistry.getInstance();
        while (iterator.hasNext()) {
            collection.add(converter.convert(elementType, iterator.next()));
        }
        return collection;
    }


    /**
     * 以 conjunction 为分隔符将集合转换为字符串<br>
     * 如果集合元素为数组、{@link Iterable}或{@link Iterator}，则递归组合其为字符串
     *
     * @param <T>         集合元素类型
     * @param iterable    {@link Iterable}
     * @param conjunction 分隔符
     * @return 连接后的字符串
     * @see IteratorUtil#join(Iterator, CharSequence)
     */
    public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
        if (null == iterable) {
            return null;
        }
        return IteratorUtil.join(iterable.iterator(), conjunction);
    }

    /**
     * 以 conjunction 为分隔符将集合转换为字符串
     *
     * @param <T>         集合元素类型
     * @param iterable    {@link Iterable}
     * @param conjunction 分隔符
     * @param prefix      每个元素添加的前缀，null表示不添加
     * @param suffix      每个元素添加的后缀，null表示不添加
     * @return 连接后的字符串
     * @since 5.3.0
     */
    public static <T> String join(Iterable<T> iterable, CharSequence conjunction, String prefix, String suffix) {
        if (null == iterable) {
            return null;
        }
        return IteratorUtil.join(iterable.iterator(), conjunction, prefix, suffix);
    }

    /**
     * 以 conjunction 为分隔符将集合转换为字符串<br>
     * 如果集合元素为数组、{@link Iterable}或{@link Iterator}，则递归组合其为字符串
     *
     * @param <T>         集合元素类型
     * @param iterator    集合
     * @param conjunction 分隔符
     * @return 连接后的字符串
     * @deprecated 请使用IteratorUtils#join(Iterator, CharSequence)
     */
    @Deprecated
    public static <T> String join(Iterator<T> iterator, CharSequence conjunction) {
        return IteratorUtil.join(iterator, conjunction);
    }

    /**
     * {@link Iterable}转为{@link Collection}<br>
     * 首先尝试强转，强转失败则构建一个新的{@link ArrayList}
     *
     * @param <E>      集合元素类型
     * @param iterable {@link Iterable}
     * @return {@link Collection} 或者 {@link ArrayList}
     * @since 3.0.9
     */
    public static <E> Collection<E> toCollection(Iterable<E> iterable) {
        return (iterable instanceof Collection) ? (Collection<E>) iterable : ListUtil.newArrayList(iterable.iterator());
    }

    /**
     * 获取集合中指定下标的元素值，下标可以为负数，例如-1表示最后一个元素<br>
     * 如果元素越界，返回null
     *
     * @param <T>        元素类型
     * @param collection 集合
     * @param index      下标，支持负数
     * @return 元素值
     * @since 4.0.6
     */
    public static <T> T get(Collection<T> collection, int index) {
        if (null == collection) {
            return null;
        }

        final int size = collection.size();
        if (0 == size) {
            return null;
        }

        if (index < 0) {
            index += size;
        }

        // 检查越界
        if (index >= size) {
            return null;
        }

        if (collection instanceof List) {
            final List<T> list = ((List<T>) collection);
            return list.get(index);
        } else {
            int i = 0;
            for (T t : collection) {
                if (i > index) {
                    break;
                } else if (i == index) {
                    return t;
                }
                i++;
            }
        }
        return null;
    }

}
