package io.niufen.springboot.redis.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author niufen
 */
@SuppressWarnings("ALL")
@Component
public class RedisLock {

    public static final String LOCK_PREFIX = "redis_lock";

    public static final int LOCK_EXPIRE_TIME = 1000;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

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
     *  最终加强分布式锁
     *
     * @param key key值
     * @return 是否获取到
     */
    public static boolean lock(String key){
        String lock = LOCK_PREFIX + key;

        return (Boolean) redisStaticTemplate.execute((RedisCallback) connection -> {

            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE_TIME + 1;
            // 只有在 key 不存在时设置 key 的值。
            // 不存在，并且设置成功返回 true； 存在则不设置返回 false
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                // 如果第一次尝试set失败，则获取
                byte[] value = connection.get(lock.getBytes());
                // value 如果存在
                if (Objects.nonNull(value) && value.length > 0) {
                    // 取得加锁的 过期时间
                    long expireTime = Long.parseLong(new String(value));
                    //如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE_TIME + 1).getBytes());
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * 删除锁
     *
     * @param key
     */
    public static void unlock(String key) {
        redisStaticTemplate.delete(LOCK_PREFIX + key);
    }

}
