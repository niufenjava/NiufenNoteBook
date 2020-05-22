package io.niufen.springboot.rabbitmq.topicdemo;

import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import io.niufen.springboot.rabbitmq.conf.TopicRabbitMqConf;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 使用 TOPIC_QUEUE_START 同时匹配两个队列，
 * TOPIC_QUEUE_ONE 只匹配 “TOPIC_QUEUE_ONE” 队列
 * TOPIC_QUEUE_TWO 只匹配 “TOPIC_QUEUE_TWO” 队列
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 11:02
 */
@Component
public class TopicSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送 sendOneTopic 会匹配到 TOPIC_QUEUE_ONE，
     */
    public void sendOneTopic(SysUserEntity sysUserEntity){
        rabbitTemplate.convertAndSend(TopicRabbitMqConf.TOPIC_EXCHANGE_NAME,TopicRabbitMqConf.TOPIC_QUEUE_ONE,sysUserEntity);
    }

    /**
     * 发送 sendTwoTopic 会匹配到 TOPIC_QUEUE_TWO
     */
    public void sendTwoTopic(SysUserEntity sysUserEntity){
        rabbitTemplate.convertAndSend(TopicRabbitMqConf.TOPIC_EXCHANGE_NAME,TopicRabbitMqConf.TOPIC_QUEUE_TWO,sysUserEntity);
    }
}
