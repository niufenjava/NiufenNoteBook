package io.niufen.springboot.druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootDruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDruidApplication.class, args);
    }

}
