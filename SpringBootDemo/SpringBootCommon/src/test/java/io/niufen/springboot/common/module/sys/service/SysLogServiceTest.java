package io.niufen.springboot.common.module.sys.service;

import io.niufen.springboot.common.module.sys.entity.SysLogEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogServiceTest {

    @Autowired
    private SysLogService sysLogService;


    @Test
    public void save() {
        SysLogEntity sysLogEntity = SysLogEntity.testNewEntity();
        Assert.assertTrue(sysLogService.save(sysLogEntity));
    }
}
