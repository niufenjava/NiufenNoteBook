package io.niufen.springboot.aoplog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.niufen"})
public class D05SpringbootAopLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(D05SpringbootAopLogApplication.class, args);
    }

}
