package io.niufen.springboot.druid.config;

import io.niufen.springboot.druid.service.DemoSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("default")
public class InitTestDataConfigurer {
    @Autowired
    private DemoSysUserService demoSysUserService;

    /**
     * Constructor >> @Autowired >> @PostConstruct
     * PostConstruct 注解的方法将会在依赖注入完成后被自动调用。只会被执行一次
     */
    @PostConstruct
    public  void  init(){
        for (int i = 0; i <= 2; i++) {
            demoSysUserService.batchSave();
            demoSysUserService.slowSql();
        }
    }
}
