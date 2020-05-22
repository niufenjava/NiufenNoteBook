package io.niufen.springboot.json.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * 我们都知道，在Spring中使用@ResponseBody注解可以将方法返回的对象序列化成JSON，比如：
 * 可看到时间默认以时间戳的形式输出，如果想要改变这个默认行为，我们可以自定义一个ObjectMapper来替代：
 * 上面配置获取了ObjectMapper对象，并且设置了时间格式。再次访问getuser，页面输出：
 * https://mrbird.cc/Spring-Boot%20JSON.html
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 17:15
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }


//    @Bean("otherMapper")
//    public ObjectMapper getObjectMapperOther(){
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        return mapper;
//    }
}
