package io.niufen.springboot.rabbitmq.fanout;

import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.module.sys.service.SysUserService;
import io.niufen.springboot.rabbitmq.conf.FanoutRabbitMqConf;
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
@RabbitListener(queues = FanoutRabbitMqConf.FANOUT_QUEUE_A)
public class FanoutReceiverA {

    @Autowired
    private SysUserService sysUserService;

    @RabbitHandler
    public void process(SysUserEntity sysUserEntity) {
        log.error("FanoutReceiverA-userName:{}",sysUserEntity.getUsername());
        try {
            //  订阅类的应该自己做自己的业务操作
            sysUserService.save(sysUserEntity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    @RabbitHandler
//    public void process(String hello) {
//        log.error("FanoutReceiverC-userName:{}",hello);
//    }

}
