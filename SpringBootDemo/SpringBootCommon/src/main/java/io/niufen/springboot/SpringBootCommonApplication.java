package io.niufen.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * http://www.ityouknow.com/springboot/2016/05/01/spring-boot-thymeleaf.html
 */
@SpringBootApplication
@ComponentScan("io.niufen")
@EnableAsync
@EnableCaching
@EnableScheduling
public class SpringBootCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCommonApplication.class, args);
    }

}
