package io.niufen.common.util;

/**
 * @author haijun.zhang
 * @date 2020/5/18
 * @time 22:14
 */
public class ThreadUtils {

    /**
     * 使当前线程沉睡 second 秒
     * @param second 当前线程沉睡时间，单位 秒
     */
    public static void sleep(long second){
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
