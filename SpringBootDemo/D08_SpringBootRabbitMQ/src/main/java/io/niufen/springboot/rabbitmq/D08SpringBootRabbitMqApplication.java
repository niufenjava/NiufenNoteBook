package io.niufen.springboot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("io.niufen")
@EnableAsync
@EnableCaching
public class D08SpringBootRabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(D08SpringBootRabbitMqApplication.class, args);
    }

}
