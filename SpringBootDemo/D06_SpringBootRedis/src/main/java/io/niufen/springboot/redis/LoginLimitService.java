package io.niufen.springboot.redis;

import io.niufen.common.constant.LongConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.RedisUtils;
import org.springframework.stereotype.Service;

/**
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 09:16
 */
@Service
public class LoginLimitService {

    public static final String LOGIN_LIMIT_REDIS_KEY_PREFIX = "user-login-limit";

    public static final String LOGIN_LIMIT_ACCOUNT_VERIFY_MSG = "登录账号不能为空";

    public static final String LOGIN_LIMIT_ACCOUNT_LIMIT_MSG = "登录尝试次数过多，账号锁定五分钟";

    public static final String SERVER_ERROR = "服务不可用";

    public static final Long LOGIN_LIMIT_ACCOUNT_LIMIT_MIN = 5L;

    public static final int LOGIN_LIMIT_ACCOUNT_COUNT_LIMIT = 5;

    /**
     * 用户登录次数校验
     * 用户输错用户名密码大于5次，那么该账号锁定5分钟
     * @param userAccount 用户登录账号
     */
    public void userLoginLimitVerify(String userAccount){
        Long increment = 0L;
        if(ObjectTools.isBlank(userAccount)){
            throw new IllegalArgumentException(LOGIN_LIMIT_ACCOUNT_VERIFY_MSG);
        }
        String key = LOGIN_LIMIT_REDIS_KEY_PREFIX + userAccount;
        try{
            increment = RedisUtils.increment(key, LongConstants.ONE);
            RedisUtils.expire(key,LOGIN_LIMIT_ACCOUNT_LIMIT_MIN);
        } catch (Exception e){
            throw new IllegalArgumentException(SERVER_ERROR);
        }

        if(increment > LOGIN_LIMIT_ACCOUNT_COUNT_LIMIT){
            throw new IllegalArgumentException(LOGIN_LIMIT_ACCOUNT_LIMIT_MSG);
        }
    }

}
