package io.niufen.springboot.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"io.niufen"})
@EnableAsync
public class SpringBootCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCommonApplication.class, args);
    }

}
