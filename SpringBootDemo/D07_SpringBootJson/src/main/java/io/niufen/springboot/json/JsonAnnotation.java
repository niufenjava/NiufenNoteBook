package io.niufen.springboot.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

/**
 * https://mrbird.cc/Spring-Boot%20JSON.html
 * Jackson 注解详解
 * Jackson包含了一些实用的注解
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 17:46
 */

/**
 * "@JsonIgnoreProperties" 忽略一组属性，作用于类上，比如JsonIgnoreProperties({ "password", "age" })。
 */
@JsonIgnoreProperties({ "password", "age" })
/**
 * @JsonNaming，用于指定一个命名策略，作用于类或者属性上。
 * Jackson自带了多种命名策略，你可以实现自己的命名策略，
 * 比如输出的key 由Java命名方式转为下面线命名方法 —— userName转化为user-name。
 * {"user_name":"mrbird","bth":"2018-04-02 10:52:12"}
 */
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class JsonAnnotation {

    /**
     * "@JsonProperty" 作用在属性上，用来为JSON Key指定一个别名。
     * 通过 ObjectMapper 序列化转字符串以后
     * {"name":"mrbird"}
     * userName 已经被替换为了 name。
     */
    @JsonProperty("name")
    private String userName;

    /**
     * "@JsonIgnore"，作用在属性上，用来忽略此属性。
     */
    @JsonIgnore
    private String password;

    private Integer age;

    /**
     * "@JsonFormat"，用于日期格式化
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
}
