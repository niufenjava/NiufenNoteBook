package io.niufen.springboot.rabbitmq.fanout;

import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FanoutSenderTest {

    @Autowired
    private FanoutSender fanoutSender;

    @Test
    public void send() {
        for (int i = 0; i < 1; i++) {

            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            fanoutSender.send(sysUserEntity);
        }
    }
    @Test
    public void send2() {
        for (int i = 0; i < 100; i++) {

            //fanoutSender.send2();
        }
    }
}
