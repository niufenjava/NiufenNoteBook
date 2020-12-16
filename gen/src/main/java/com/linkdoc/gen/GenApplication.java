package com.linkdoc.gen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.linkdoc.gen.dao")
public class GenApplication {
    public static void main(String[] args) {
        SpringApplication.run(GenApplication.class, args);
    }
}
