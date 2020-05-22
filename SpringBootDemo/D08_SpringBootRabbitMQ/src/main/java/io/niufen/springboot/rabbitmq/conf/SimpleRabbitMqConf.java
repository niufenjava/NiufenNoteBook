package io.niufen.springboot.rabbitmq.conf;

import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 21:55
 */
@Configuration
public class SimpleRabbitMqConf {

    public static final String SIMPLE_QUEUE_NAME = "simpleQueue";

    @Bean
    public Queue simpleQueue(){
        return new Queue(SIMPLE_QUEUE_NAME);
    }
}
