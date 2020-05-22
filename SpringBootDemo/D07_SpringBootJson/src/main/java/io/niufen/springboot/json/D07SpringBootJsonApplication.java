package io.niufen.springboot.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication

@ComponentScan("io.niufen")
@EnableAsync
@EnableCaching
public class D07SpringBootJsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(D07SpringBootJsonApplication.class, args);
    }

}
