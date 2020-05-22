package io.niufen.springboot.rabbitmq.simplemq;

import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import io.niufen.springboot.rabbitmq.conf.SimpleRabbitMqConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 简单MQ是单线程，5个接收者一次性接收；相当于启动了5个监听，收到不同的消息
 * 一个发送者，N个接受者,经过测试会均匀的将消息发送到N个接收者中
 * 多对多和一对多一样，接收端仍然会均匀接收到消息
 * Spring Boot 以及完美的支持对象的发送和接收，不需要格外的配置。
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 21:58
 */
@Component
@Slf4j
public class SimpleSender {

    /**
     * rabbitTemplate 是 Spring Boot 提供的默认实现
     */
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(SysUserEntity sysUserEntity) {
        //简单对列的情况下routingKey即为Q名
        this.rabbitTemplate.convertAndSend(SimpleRabbitMqConf.SIMPLE_QUEUE_NAME, sysUserEntity);
    }
}
