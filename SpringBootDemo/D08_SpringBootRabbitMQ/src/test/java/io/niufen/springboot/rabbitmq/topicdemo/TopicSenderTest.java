package io.niufen.springboot.rabbitmq.topicdemo;

import io.niufen.springboot.module.sys.entity.SysUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TopicSenderTest {

    @Autowired
    private TopicSender topicSender;

    /**
     * 发送send1会匹配到topic.#和topic.message 两个Receiver都可以收到消息，
     * 发送send2只有topic.#可以匹配所有只有Receiver2监听到消息
     */
    @Test
    public void sendOneTopic() {
        SysUserEntity entity = SysUserEntity.testNewEntity();
        topicSender.sendOneTopic(entity);
    }

    @Test
    public void sendTwoTopic() {
        SysUserEntity entity = SysUserEntity.testNewEntity();
        topicSender.sendTwoTopic(entity);
    }
}
