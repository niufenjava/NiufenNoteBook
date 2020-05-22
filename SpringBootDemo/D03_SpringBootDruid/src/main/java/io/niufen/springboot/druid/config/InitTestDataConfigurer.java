package io.niufen.springboot.druid.config;

import io.niufen.springboot.druid.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("default")
public class InitTestDataConfigurer {
    @Autowired
    private SysUserService sysUserService;

    /**
     * Constructor >> @Autowired >> @PostConstruct
     * PostConstruct 注解的方法将会在依赖注入完成后被自动调用。只会被执行一次
     */
    @PostConstruct
    public  void  init(){
        for (int i = 0; i <= 2; i++) {
            sysUserService.batchSave();
            sysUserService.slowSql();
        }
    }
}
