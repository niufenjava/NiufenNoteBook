package io.niufen.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("io.niufen")
@EnableAsync
@EnableCaching
@EnableScheduling
public class D12SpringBootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(D12SpringBootWebApplication.class, args);
    }

}
