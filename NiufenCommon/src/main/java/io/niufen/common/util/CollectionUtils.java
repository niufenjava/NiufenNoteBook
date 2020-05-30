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
public class CollectionUtils {

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
        if (ClassUtils.isAssignable(collectionType, AbstractCollection.class)) {
            return ListUtils.newArrayList();
        }
        // SET
        if (ClassUtils.isAssignable(collectionType, HashSet.class)) {
            return SetUtils.newSet();
        }
        if (ClassUtils.isAssignable(collectionType, LinkedHashSet.class)) {
            return SetUtils.newLinkedHashSet();
        }
        if (ClassUtils.isAssignable(collectionType, EnumSet.class)) {
            return (Collection<T>) EnumSet.noneOf((Class<Enum>) ClassUtils.getTypeArgument(collectionType));
        }

        // List
        if (ClassUtils.isAssignable(collectionType, ArrayList.class)) {
            return ListUtils.newArrayList();
        }
        if (ClassUtils.isAssignable(collectionType, LinkedList.class)) {
            return ListUtils.newLinkedList();
        }
        // Others，直接实例化
        try {
            return (Collection<T>) ReflectionUtils.newInstance(collectionType);
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
        if (TypeUtils.isUnknownType(elementType)) {
            elementType = Object.class;
        }

        Iterator iterator;
        if (value instanceof Iterator) {
            iterator = (Iterator) value;
        } else if (value instanceof Iterable) {
            iterator = ((Iterable) value).iterator();
        } else if (value instanceof Enumeration) {
            iterator = new EnumerationIterator<>((Enumeration) value);
        } else if (ArrayUtils.isArray(value)) {
            iterator = new ArrayIterator<>(value);
        } else if (value instanceof CharSequence) {
            // String 按照逗号分隔的列表对待
            final String arrayStr = StringUtils.unWrap((CharSequence) value, '[', ']');
            // todo iter = StrUtil.splitTrim(ArrayStr, CharUtil.COMMA).iterator();
            iterator = StringSpliter.splitTrim(arrayStr, CharConstants.COMMA).iterator();
        } else {
            // 其它类型按照单一元素处理
            iterator = ListUtils.newArrayList(value).iterator();
        }
        final ConverterRegistry converter = ConverterRegistry.getInstance();
        while (iterator.hasNext()) {
            collection.add(converter.convert(elementType, iterator.next()));
        }
        return collection;
    }


}
