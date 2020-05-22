package io.niufen.springboot.mail.recevier;

import io.niufen.springboot.mail.conf.MailMqConf;
import io.niufen.springboot.mail.dto.SendMailDTO;
import io.niufen.springboot.mail.service.impl.MailAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 22:01
 */
@Slf4j
@Component
@RabbitListener(queues = MailMqConf.SIMPLE_QUEUE_NAME)
public class MailMqReceiver {

    @Autowired
    private MailAsyncService mailAsyncService;

    @RabbitHandler
    public void process(SendMailDTO sendMailDTO) {
        log.error("MailMqReceiver:"+sendMailDTO.getTitle());
        mailAsyncService.sendSimpleMail(sendMailDTO.getToMailAddr(),sendMailDTO.getTitle(),sendMailDTO.getContent());
    }
}
