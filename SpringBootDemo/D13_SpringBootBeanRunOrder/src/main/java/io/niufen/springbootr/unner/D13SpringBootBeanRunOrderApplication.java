package io.niufen.springbootr.unner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot启动时执行任务CommandLineRunner
 * 平常开发中有可能需要实现在项目启动后执行的功能，
 * SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
 *
 * 如果有多个类实现CommandLineRunner接口，如何保证顺序
 * > SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，
 * 如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序
 *
 * > 根据控制台结果可判断，@Order 注解的执行优先级是按value值从小到大顺序。
 */
@SpringBootApplication
@Slf4j
public class D13SpringBootBeanRunOrderApplication {

    public static void main(String[] args) {
        log.error("D13SpringBootBeanRunOrderApplication start");
        SpringApplication.run(D13SpringBootBeanRunOrderApplication.class, args);
        log.error("D13SpringBootBeanRunOrderApplication end");
    }

}
