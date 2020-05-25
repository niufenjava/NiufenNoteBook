package io.niufen.springboot.rabbitmq.simplemq;

import io.niufen.springboot.module.sys.entity.SysUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleSenderTest {

    @Autowired
    private SimpleSender simpleSender;

    @Test
    public void send() {
        for (int i = 0; i < 10000; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
//            ThreadUtils.sleep(1);
            simpleSender.send(sysUserEntity);
        }
    }
}
