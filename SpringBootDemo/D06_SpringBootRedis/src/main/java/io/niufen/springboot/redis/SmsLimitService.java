package io.niufen.springboot.redis;

import io.niufen.common.constant.LongConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.springboot.redis.utils.RedisUtils;
import org.springframework.stereotype.Service;

/**
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 09:16
 */
@Service
public class SmsLimitService {

    public static final String SMS_LIMIT_REDIS_KEY_PREFIX = "sms-send-limit";

    public static final String SMS_LIMIT_PHONE_VERIFY_MSG = "手机号码不能为空";

    public static final String SMS_LIMIT_PHONE_LIMIT_MSG = "每分钟只能发送一次短信";
    public static final String SERVER_ERROR = "服务不可用";

    public static final Long SMS_LIMIT_PHONE_LIMIT_MIN = 3L;

    /**
     * 登录、找回密码等发送短信限制功能
     * 发送一次短信后，60秒内不能再次发送
     * @param phone 发送短信手机号码
     */
    public void smsSendLimitVerify(String phone){
        Long increment = 0L;
        if(ObjectTools.isBlank(phone)){
            throw new IllegalArgumentException(SMS_LIMIT_PHONE_VERIFY_MSG);
        }
        String key = SMS_LIMIT_REDIS_KEY_PREFIX + phone;
        try{
            increment = RedisUtils.increment(key, LongConstants.ONE);
            if(increment == 1){
                RedisUtils.expire(key,SMS_LIMIT_PHONE_LIMIT_MIN);
            }
        } catch (Exception e){
            throw new IllegalArgumentException(SERVER_ERROR);
        }
        if(increment>1){
            throw new IllegalArgumentException(SMS_LIMIT_PHONE_LIMIT_MSG);
        }
    }
}
