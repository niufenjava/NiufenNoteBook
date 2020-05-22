package io.niufen.springboot.thymeleaf.conf;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 22:54
 */
@Component
public class LayoutConf {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
