package io.niufen.springbootconfig.property;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 自定义属性获取
 * 这里如果用 lombok 的 @Data注解，那么Set就会被覆盖，@Value会失效。所以不能 @Setter
 * @author haijun.zhang
 * @date 2020/5/8
 * @time 18:08
 */

@Component
@Getter
@ToString
public class UserDefinedProperties {

    @Value("${niufen.project.name}")
    private String niufenProjectName;

    @Value("${niufen.project.port}")
    private Integer niufenProjectPort;

    @Value("${niufen.project.title}")
    private String niufenProjectTitle;

}
