package io.niufen.springboot.rabbitmq.fanout;

import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import io.niufen.springboot.rabbitmq.conf.FanoutRabbitMqConf;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 13:33
 */
@Component
public class FanoutSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(SysUserEntity sysUserEntity){
        rabbitTemplate.convertAndSend(FanoutRabbitMqConf.FANOUT_EXCHANGE_NAME,"",sysUserEntity);
    }
//
//    public void send2(){
//        rabbitTemplate.convertAndSend(FanoutRabbitMqConf.FANOUT_EXCHANGE_NAME,"hello");
//    }
}
