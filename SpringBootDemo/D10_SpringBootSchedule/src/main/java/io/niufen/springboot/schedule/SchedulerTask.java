package io.niufen.springboot.schedule;

import io.niufen.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 16:54
 */
@Component
@Slf4j
public class SchedulerTask {

    private int count = 0;

    /**
     * @Scheduled 参数可以接受两种定时的设置，一种是我们常用的 cron= ""
     */
    @Scheduled(cron = "*/6 * * * * ?")
    private void taskA(){
        log.error("SchedulerTask-taskA:"+count++);
    }

    /**
     * 一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。
     * -@Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
     * -@Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
     * -@Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按 fixedRate 的规则每6秒执行一次
     */
    @Scheduled(fixedRate = 6000)
    private void taskB(){
        log.error("SchedulerTask-taskB:"+ DateUtil.nowStr());
    }
}
