package io.niufen.common.core.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author hml
 * @date 2018/6/27 下午2:40
 */
public class UUIDUtil {

    /**
     * 生成uuid，并去除横线
     * @return uuid
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
