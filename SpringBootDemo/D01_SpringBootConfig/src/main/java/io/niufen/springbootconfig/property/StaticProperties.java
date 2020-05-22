package io.niufen.springbootconfig.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author haijun.zhang
 * @date 2020/5/8
 * @time 18:08
 */
@Component
public class StaticProperties {

    public static String springProfilesActive;

    @Value("${spring.profiles.active}")
    public void setSpringProfilesValue(String springProfilesActive) {
        StaticProperties.springProfilesActive = springProfilesActive;
    }
}
