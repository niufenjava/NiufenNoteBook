package io.niufen.springboot.rabbitmq.simplemq;

import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.module.sys.service.SysUserService;
import io.niufen.springboot.rabbitmq.conf.SimpleRabbitMqConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 一个发送者，N个接受者,经过测试会均匀的将消息发送到N个接收者中
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 22:01
 */
@Slf4j
@Component
@RabbitListener(queues = SimpleRabbitMqConf.SIMPLE_QUEUE_NAME)
public class SimpleReceiver1 {

    @Autowired
    private SysUserService sysUserService;

    @RabbitHandler
    public void process(SysUserEntity sysUserEntity) {
        log.error("SimpleReceiver1:"+sysUserEntity.getUsername());
//        ThreadUtils.sleep(LongConstants.ONE);
        try {
            sysUserService.save(sysUserEntity);
        } catch (Exception e){
            e.getMessage();
        }
    }
}
