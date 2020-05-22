package io.niufen.springbootconfig;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootConfigApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootConfigApplication.class);
        // 禁止Banner打印
        app.setBannerMode(Banner.Mode.OFF);
        // 禁止命令行参数
        app.setAddCommandLineProperties(false);
        app.run(args);
    }

}
