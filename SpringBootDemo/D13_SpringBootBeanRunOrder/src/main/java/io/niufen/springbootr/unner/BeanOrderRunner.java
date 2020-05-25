package io.niufen.springbootr.unner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/22
 * @time 22:15
 */
@Component
@Slf4j
public class BeanOrderRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.error("BeanOrderRunner Run Start");
        if(null != args){
            for (String arg : args) {
                log.debug("arg:"+arg);
            }
        }
        log.error("BeanOrderRunner Run End");
    }
}
