package io.niufen.springboot.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * http://www.ityouknow.com/springboot/2016/12/02/spring-boot-scheduler.html
 * 在我们开发项目过程中，经常需要定时任务来帮助我们来做一些内容，
 * Spring Boot 默认已经帮我们实行了，只需要添加相应的注解就可以实现
 *
 * 在启动类上面加上@EnableScheduling即可开启定时
 * @author niufen
 */
@SpringBootApplication
@ComponentScan("io.niufen")
@EnableAsync
@EnableCaching
@EnableScheduling
public class D10SpringBootScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(D10SpringBootScheduleApplication.class, args);
    }

}
