package io.niufen.common.util;


import java.util.*;

/**
 * {@link Iterable} 和 {@link Iterator} 相关工具类
 *
 * @author Looly
 * @since 3.1.0
 */
public class IterUtil {

	/**
	 * Iterable是否为空
	 *
	 * @param iterable Iterable对象
	 * @return 是否为空
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return null == iterable || isEmpty(iterable.iterator());
	}

	/**
	 * Iterator是否为空
	 *
	 * @param Iterator Iterator对象
	 * @return 是否为空
	 */
	public static boolean isEmpty(Iterator<?> Iterator) {
		return null == Iterator || false == Iterator.hasNext();
	}

	/**
	 * Iterable是否为空
	 *
	 * @param iterable Iterable对象
	 * @return 是否为空
	 */
	public static boolean isNotEmpty(Iterable<?> iterable) {
		return null != iterable && isNotEmpty(iterable.iterator());
	}

	/**
	 * Iterator是否为空
	 *
	 * @param Iterator Iterator对象
	 * @return 是否为空
	 */
	public static boolean isNotEmpty(Iterator<?> Iterator) {
		return null != Iterator && Iterator.hasNext();
	}

	/**
	 * 是否包含{@code null}元素
	 *
	 * @param iter 被检查的{@link Iterable}对象，如果为{@code null} 返回true
	 * @return 是否包含{@code null}元素
	 */
	public static boolean hasNull(Iterable<?> iter) {
		return hasNull(null == iter ? null : iter.iterator());
	}

	/**
	 * 是否包含{@code null}元素
	 *
	 * @param iter 被检查的{@link Iterator}对象，如果为{@code null} 返回true
	 * @return 是否包含{@code null}元素
	 */
	public static boolean hasNull(Iterator<?> iter) {
		if (null == iter) {
			return true;
		}
		while (iter.hasNext()) {
			if (null == iter.next()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 是否全部元素为null
	 *
	 * @param iter iter 被检查的{@link Iterable}对象，如果为{@code null} 返回true
	 * @return 是否全部元素为null
	 * @since 3.3.0
	 */
	public static boolean isAllNull(Iterable<?> iter) {
		return isAllNull(null == iter ? null : iter.iterator());
	}

	/**
	 * 是否全部元素为null
	 *
	 * @param iter iter 被检查的{@link Iterator}对象，如果为{@code null} 返回true
	 * @return 是否全部元素为null
	 * @since 3.3.0
	 */
	public static boolean isAllNull(Iterator<?> iter) {
		if (null == iter) {
			return true;
		}

		while (iter.hasNext()) {
			if (null != iter.next()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据集合返回一个元素计数的 {@link Map}<br>
	 * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
	 * 例如：[a,b,c,c,c] 得到：<br>
	 * a: 1<br>
	 * b: 1<br>
	 * c: 3<br>
	 *
	 * @param <T>  集合元素类型
	 * @param iter {@link Iterable}，如果为null返回一个空的Map
	 * @return {@link Map}
	 * @deprecated 如果对象同时实现Iterable和Iterator接口会产生歧义，请使用CollUtil.countMap
	 */
	@Deprecated
	public static <T> Map<T, Integer> countMap(Iterable<T> iter) {
		return countMap(null == iter ? null : iter.iterator());
	}

	/**
	 * 根据集合返回一个元素计数的 {@link Map}<br>
	 * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
	 * 例如：[a,b,c,c,c] 得到：<br>
	 * a: 1<br>
	 * b: 1<br>
	 * c: 3<br>
	 *
	 * @param <T>  集合元素类型
	 * @param iter {@link Iterator}，如果为null返回一个空的Map
	 * @return {@link Map}
	 */
	public static <T> Map<T, Integer> countMap(Iterator<T> iter) {
		final HashMap<T, Integer> countMap = new HashMap<>();
		if (null != iter) {
			Integer count;
			T t;
			while (iter.hasNext()) {
				t = iter.next();
				count = countMap.get(t);
				if (null == count) {
					countMap.put(t, 1);
				} else {
					countMap.put(t, count + 1);
				}
			}
		}
		return countMap;
	}


	/**
	 * 以 conjunction 为分隔符将集合转换为字符串
	 *
	 * @param <T>         集合元素类型
	 * @param iterable    {@link Iterable}
	 * @param conjunction 分隔符
	 * @return 连接后的字符串
	 * @deprecated 如果对象同时实现Iterable和Iterator接口会产生歧义，请使用CollUtil.join
	 */
	@Deprecated
	public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
		if (null == iterable) {
			return null;
		}
		return join(iterable.iterator(), conjunction);
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
	 * @since 4.0.10
	 * @deprecated 如果对象同时实现Iterable和Iterator接口会产生歧义，请使用CollUtil.join
	 */
	@Deprecated
	public static <T> String join(Iterable<T> iterable, CharSequence conjunction, String prefix, String suffix) {
		if (null == iterable) {
			return null;
		}
		return join(iterable.iterator(), conjunction, prefix, suffix);
	}

	/**
	 * 以 conjunction 为分隔符将集合转换为字符串<br>
	 * 如果集合元素为数组、{@link Iterable}或{@link Iterator}，则递归组合其为字符串
	 *
	 * @param <T>         集合元素类型
	 * @param iterator    集合
	 * @param conjunction 分隔符
	 * @return 连接后的字符串
	 */
	public static <T> String join(Iterator<T> iterator, CharSequence conjunction) {
		return join(iterator, conjunction, null, null);
	}

	/**
	 * 以 conjunction 为分隔符将集合转换为字符串<br>
	 * 如果集合元素为数组、{@link Iterable}或{@link Iterator}，则递归组合其为字符串
	 *
	 * @param <T>         集合元素类型
	 * @param iterator    集合
	 * @param conjunction 分隔符
	 * @param prefix      每个元素添加的前缀，null表示不添加
	 * @param suffix      每个元素添加的后缀，null表示不添加
	 * @return 连接后的字符串
	 * @since 4.0.10
	 */
	public static <T> String join(Iterator<T> iterator, CharSequence conjunction, String prefix, String suffix) {
		if (null == iterator) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		T item;
		while (iterator.hasNext()) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}

			item = iterator.next();
			if (ArrayUtil.isArray(item)) {
				sb.append(ArrayUtil.join(ArrayUtil.wrap(item), conjunction, prefix, suffix));
			} else if (item instanceof Iterable<?>) {
				sb.append(join((Iterable<?>) item, conjunction, prefix, suffix));
			} else if (item instanceof Iterator<?>) {
				sb.append(join((Iterator<?>) item, conjunction, prefix, suffix));
			} else {
				sb.append(StrUtil.wrap(String.valueOf(item), prefix, suffix));
			}
		}
		return sb.toString();
	}

	/**
	 * 返回一个空Iterator
	 *
	 * @param <T> 元素类型
	 * @return 空Iterator
	 * @see Collections#emptyIterator()
	 * @since 5.3.1
	 */
	public static <T> Iterator<T> empty() {
		return Collections.emptyIterator();
	}


	/**
	 * Iterator转List<br>
	 * 不判断，直接生成新的List
	 *
	 * @param <E>  元素类型
	 * @param iter {@link Iterator}
	 * @return List
	 * @since 4.0.6
	 */
	public static <E> List<E> toList(Iterable<E> iter) {
		if (null == iter) {
			return null;
		}
		return toList(iter.iterator());
	}

	/**
	 * Iterator转List<br>
	 * 不判断，直接生成新的List
	 *
	 * @param <E>  元素类型
	 * @param iter {@link Iterator}
	 * @return List
	 * @since 4.0.6
	 */
	public static <E> List<E> toList(Iterator<E> iter) {
		final List<E> list = new ArrayList<>();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}

}
