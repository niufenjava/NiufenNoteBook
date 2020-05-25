package io.niufen.springbootr.unner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/22
 * @time 22:15
 */
@Component
@Order(2)
@Slf4j
public class BeanOrderRunnerTwo implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.error("BeanOrderRunnerTwo Run Start");
        if(null != args){
            for (String arg : args) {
                log.debug("arg:"+arg);
            }
        }
        log.error("BeanOrderRunnerTwo Run End");
    }
}
