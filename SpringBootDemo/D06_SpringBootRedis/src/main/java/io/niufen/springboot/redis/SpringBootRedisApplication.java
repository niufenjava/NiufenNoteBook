package io.niufen.springboot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("io.niufen")
@EnableAsync
@EnableCaching
public class SpringBootRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisApplication.class, args);
    }

}
