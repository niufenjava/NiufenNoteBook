package io.niufen.springboot.rabbitmq.topicdemo;

import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import io.niufen.springboot.common.module.sys.service.SysUserService;
import io.niufen.springboot.rabbitmq.conf.TopicRabbitMqConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 11:02
 */
@Component
@Slf4j
@RabbitListener(queues = TopicRabbitMqConf.TOPIC_QUEUE_ONE)
public class TopicReceiverOne {

    @Autowired
    private SysUserService sysUserService;

    @RabbitHandler
    public void process(SysUserEntity sysUserEntity) {
        log.error("TopicReceiverOne-userName:{}",sysUserEntity.getUsername());
        try {
            sysUserService.save(sysUserEntity);
        } catch (Exception e){
            e.getMessage();
        }
    }

}
