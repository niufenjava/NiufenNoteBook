package io.niufen.springboot.redis.utils;

import io.niufen.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 * @author haijun.zhang
 *
 */
@Slf4j
@Component
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static  RedisTemplate<String, Object> redisStaticTemplate;


	/**
	 * spring 启动以后
	 * Constructor >> @Autowired >> @PostConstruct
	 */
	@PostConstruct
	private void init(){
		redisStaticTemplate = redisTemplate;
	}

	/**
	 * 普通缓存放入
	 *
	 * @param key   键
	 * @param value 值
	 * @return true-成功 false-失败
	 */
	public static Boolean set(String key, Object value) {
		Boolean result = Boolean.TRUE;
		try {
			redisStaticTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			result = Boolean.FALSE;
		} finally {
			log.debug("set -> redisStaticTemplate.opsForValue().set(key, value) -> key:{}, value:{}",key, JsonUtil.toJSONString(value));
		}
		return result;
	}

	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true-成功 false-失败
	 */
	public static Boolean set(String key, Object value, long time) {
		Boolean result = Boolean.TRUE;
		try {
			if (time > 0) {
				redisStaticTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
				log.debug("set -> redisStaticTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS) -> key:{}, value:{}, time:{}",key, JsonUtil.toJSONString(value),time);
			} else {
				set(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = Boolean.FALSE;
		}
		return result;
	}

	/**
	 * 普通缓存获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Object get(String key) {
		Object result =redisStaticTemplate.opsForValue().get(key);
		log.debug("get -> redisStaticTemplate.opsForValue().get(key) -> key:{}, value:{}", key, JsonUtil.toJSONString(result));
		return result;
	}


	/**
	 * 指定缓存失效时间
	 *
	 * @param key  键
	 * @param time 时间(秒)
	 * @return TRUE-设置成功；FALSE-设置失败
	 */
	public static Boolean expire(String key, long time) {
		try {
			if (time > 0) {
				log.debug("expire -> redisStaticTemplate.expire(key, time, TimeUnit.SECONDS) -> key:{}, time:{}", key, time);
				redisStaticTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据 key 获取过期时间
	 *
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public static Long getExpire(String key) {
		Long expire = redisStaticTemplate.getExpire(key, TimeUnit.SECONDS);
		log.debug("getExpire -> redisStaticTemplate.getExpire(key, TimeUnit.SECONDS) -> key:{} -> result expire:{}", key, expire);
		return expire;
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public static Boolean hasKey(String key) {
		Boolean result = Boolean.TRUE;
		try {
			result = redisStaticTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			result = Boolean.FALSE;
		} finally {
			log.debug("hasKey -> redisStaticTemplate.hasKey(key) -> key:{} -> result:{}", key, result);
		}
		return result;
	}

	/**
	 * 删除缓存
	 *
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public static void del(String... key) {

		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisStaticTemplate.delete(key[0]);
			} else {
				redisStaticTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
		log.debug("delByKeys -> redisStaticTemplate.delete(keys); -> key:{}", JsonUtil.toJSONString(key));
	}

	/**
	 * 递增
	 * @param key 键
	 * @param delta 递增数值
	 */
	public static Long increment(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		Long increment = redisStaticTemplate.opsForValue().increment(key, delta);
		log.debug("increment -> redisStaticTemplate.opsForValue().increment(key, delta) -> key:{}, delta:{} -> result:{}", key, delta, increment);
		return increment;
	}

	/**
	 * 递减
	 * @param key 键
	 * @param delta 递减数值
	 */
	public static Long decrement(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		Long decrement = redisStaticTemplate.opsForValue().increment(key, -delta);
		log.debug("decrement -> redisStaticTemplate.opsForValue().increment(key, -delta) -> key:{}, delta:{} -> result:{}", key, -delta, decrement);
		return decrement;
	}

	// ================================Map=================================

	/**
	 * HashSet
	 *
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public static Boolean setHashMap(String key, Map<String, Object> map) {
		Boolean result = Boolean.TRUE;
		try {
			redisStaticTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {
			e.printStackTrace();
			result = Boolean.FALSE;
		} finally {
			log.debug("setHashMap -> redisStaticTemplate.opsForHash().putAll(key, map); -> key:{}, map:{} -> result:{}", key, JsonUtil.toJSONString(map), result);
		}
		return result;
	}

	/**
	 * 获取hashKey对应的所有键值
	 *
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public static Map<Object, Object> getHashMap(String key) {
		Map<Object, Object> entries = redisStaticTemplate.opsForHash().entries(key);
		log.debug("getHashMap -> redisStaticTemplate.opsForHash().entries(key) -> key:{}-> result:{}", key, JsonUtil.toJSONString(entries));

		return entries;
	}

	/**
	 * HashSet 并设置时间
	 *
	 * @param key  键
	 * @param map  对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public static Boolean setHashMap(String key, Map<String, Object> map, long time) {
		try {
			redisStaticTemplate.opsForHash().putAll(key, map);
			log.debug("setHashMap -> redisStaticTemplate.opsForHash().putAll(key, map); -> key:{}, map:{}", key, JsonUtil.toJSONString(map));
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张 hash 表中放入数据,如果不存在将创建
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public static Boolean setHashItem(String key, String item, Object value) {
		try {
			redisStaticTemplate.opsForHash().put(key, item, value);
			log.debug("setHashItem -> redisStaticTemplate.opsForHash().put(key, item, value); -> key:{}, item:{}, value:{}", key, item, JsonUtil.toJSONString(value));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public static Boolean setHashItem(String key, String item, Object value, long time) {
		try {
			log.debug("setHashItem -> redisStaticTemplate.opsForHash().put(key, item, value); -> key:{}, item:{}, value:{}", key, item, JsonUtil.toJSONString(value));
			redisStaticTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashGet
	 *
	 * @param key  键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public static Object getHashItem(String key, String item) {
		Object result = redisStaticTemplate.opsForHash().get(key, item);
		log.debug("getHashItem -> redisStaticTemplate.opsForHash().get(key, item); -> key:{}, item:{}, itemValue:{}", key, item, JsonUtil.toJSONString(result));
		return result;
	}

	/**
	 * 删除hash表中的值
	 *
	 * @param key  键 不能为null
	 * @param items 项 可以使多个 不能为null
	 */
	public static void delHashItems(String key, Object... items) {
		log.debug("delHashItems -> redisStaticTemplate.opsForHash().delete(key, item); -> key:{}, item:{}", key, JsonUtil.toJSONString(items));
		redisStaticTemplate.opsForHash().delete(key, items);
	}

	/**
	 * 判断hash表中是否有该项的值
	 *
	 * @param key  键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public static Boolean hasHashItemByKey(String key, String item) {
		Boolean result = redisStaticTemplate.opsForHash().hasKey(key, item);
		log.debug("hasHashItemByKey -> redisStaticTemplate.opsForHash().hasKey(key, item); -> key:{}, item:{} -> result:{}", key, item, result);
		return result;
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta   要增加几(大于0)
	 * @return 递增后的数值
	 */
	public static Double incrementHashItem(String key, String item, Double delta) {
		Double increment = redisStaticTemplate.opsForHash().increment(key, item, delta);
		log.debug("incrementHashItem -> redisStaticTemplate.opsForHash().increment(key, item, by); -> key:{}, item:{}, by:{} -> result:{}", key, item, delta ,increment);
		return increment;
	}

	/**
	 * hash递减
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta   要减少记(小于0)
	 * @return 递增后的数值
	 */
	public static double decrementHashItem(String key, String item, double delta) {
		Double decrement = redisStaticTemplate.opsForHash().increment(key, item, -delta);
		log.debug("decrementHashItem -> redisStaticTemplate.opsForHash().increment(key, item, by); -> key:{}, item:{}, by:{} -> result:{}", key, item, delta ,decrement);
		return decrement;
	}

	// ============================set=============================

	/**
	 * 将数据放入set缓存
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public static Long setSets(String key, Object... values) {
		Long setSize = 0L;
		try {
			setSize = redisStaticTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("setSets -> redisStaticTemplate.opsForSet().add(key, values); -> key:{}, values:{}-> setSize:{}", key, JsonUtil.toJSONString(values), setSize);
		return setSize;
	}

	/**
	 * 将set数据放入缓存
	 *
	 * @param key    键
	 * @param time   时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public static Long setSets(String key, Long time, Object... values) {
		Long setSize = 0L;
		try {
			setSize = redisStaticTemplate.opsForSet().add(key, values);
			log.debug("setSets -> redisStaticTemplate.opsForSet().add(key, values); -> key:{}, values:{}-> setSize:{}", key, JsonUtil.toJSONString(values), setSize);

			if (time > 0) {
				expire(key, time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setSize;
	}

	/**
	 * 根据key获取Set中的所有值
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Set<Object> getSets(String key) {
		try {
			Set<Object> members = redisStaticTemplate.opsForSet().members(key);
			log.debug("getSets -> redisStaticTemplate.opsForSet().members(key); -> key:{}-> members:{}", key, JsonUtil.toJSONString(members));
			return members;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 *
	 * @param key   键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public static Boolean hasValueInSet(String key, Object value) {
		Boolean hasValue = Boolean.FALSE;
		try {
			hasValue = redisStaticTemplate.opsForSet().isMember(key, value);
			log.debug("hasValueInSet -> redisStaticTemplate.opsForSet().isMember(key, value); -> key:{}, value:{} -> hasValue:{}", key, JsonUtil.toJSONString(value), hasValue);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return hasValue;
	}

	/**
	 * 获取set缓存的长度
	 *
	 * @param key 键
	 * @return set 的长度
	 */
	public static Long getSetSize(String key) {
		Long setSize = 0L;
		try {
			setSize = redisStaticTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("getSetSize -> redisStaticTemplate.opsForSet().size(key); -> key:{} -> setSize:{}", key, setSize);
		return setSize;
	}

	/**
	 * 移除值为value的
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public static Long delValueInSet(String key, Object... values) {
		Long removeSize = 0L;
		try {
			removeSize = redisStaticTemplate.opsForSet().remove(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("delValueInSet -> redisStaticTemplate.opsForSet().remove(key, values); -> key:{}, values:{}  -> removeSize:{}", key, JsonUtil.toJSONString(values), removeSize);
		return removeSize;

	}

	// ===============================list=================================



	/**
	 * 将list放入缓存
	 */
	public static Boolean setList(String key, Object value) {
		try {
			log.debug("setList -> redisStaticTemplate.opsForList().rightPushAll(key,value; -> key:{}, value:{}", key, JsonUtil.toJSONString(value));
			redisStaticTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒)
	 * @return true-成功；false-失败
	 */
	public static Boolean setList(String key, Object value, long time) {
		try {
			log.debug("setList -> redisStaticTemplate.opsForList().rightPushAll(key,value; -> key:{}, value:{}", key, JsonUtil.toJSONString(value));
			redisStaticTemplate.opsForList().rightPush(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 */
	public static Boolean setList(String key, List<Object> value) {
		try {
			log.debug("setList -> redisStaticTemplate.opsForList().rightPushAll(key,value; -> key:{}, value:{}", key, JsonUtil.toJSONString(value));
			redisStaticTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒)
	 * @return true-成功；false-失败
	 */
	public static Boolean setList(String key, List<Object> value, long time) {
		try {
			log.debug("setList -> redisStaticTemplate.opsForList().rightPushAll(key,value; -> key:{}, value:{}", key, JsonUtil.toJSONString(value));
			redisStaticTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取list缓存的内容
	 *
	 * @param key   键
	 * @param start 开始
	 * @param end   结束 0 到 -1代表所有值
	 * @return List<Object>
	 */
	public static List<Object> getList(String key, long start, long end) {
		List<Object> rangeList = null;
		try {
			rangeList = redisStaticTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("getList -> redisStaticTemplate.opsForList().range(key, start, end); -> key:{}, start:{}, end:{}  -> rangeList:{}", key, start, end, JsonUtil.toJSONString(rangeList));
		return rangeList;
	}

	/**
	 * 获取list缓存的长度
	 *
	 * @param key 键
	 * @return list的长度
	 */
	public static Long getListSize(String key) {
		Long size = 0L;
		try {
			size = redisStaticTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("getListSize -> redisStaticTemplate.opsForList().size(key); -> key:{} -> size:{}", key, size);
		return size;
	}

	/**
	 * 通过索引 获取list中的值
	 *
	 * @param key   键
	 * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return 获取list中的值
	 */
	public static Object getListItemByIndex(String key, long index) {
		Object value = null;
		try {
			value = redisStaticTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("getListItemByIndex -> redisStaticTemplate.opsForList().index(key, index); -> key:{}, index:{}  -> value:{}", key, index, JsonUtil.toJSONString(value));
		return value;
	}

	/**
	 * 根据索引修改list中的某条数据
	 *
	 * @param key   键
	 * @param index 索引
	 * @param value 值
	 * @return true 修改成功; false 修改失败
	 */
	public static Boolean updateListItemValueByIndex(String key, long index, Object value) {
		try {
			redisStaticTemplate.opsForList().set(key, index, value);
			log.debug("updateListItemValueByIndex -> redisStaticTemplate.opsForList().set(key, index, value); -> key:{}, index:{}, value:{}", key, index, JsonUtil.toJSONString(value));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 *
	 * @param key   键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public static Long delListCount(String key, long count, Object value) {
		Long removeSize = 0L;
		try {
			removeSize = redisStaticTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("delListCount -> redisStaticTemplate.opsForList().remove(key, count, value); -> key:{}, count:{}, value:{}", key, count, JsonUtil.toJSONString(value));
		return removeSize;
	}
}
