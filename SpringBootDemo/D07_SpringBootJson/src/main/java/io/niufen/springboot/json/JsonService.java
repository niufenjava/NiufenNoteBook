package io.niufen.springboot.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * https://mrbird.cc/Spring-Boot%20JSON.html
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 17:27
 */
@Service
public class JsonService {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 序列化
     * Jackson通过使用mapper的writeValueAsString方法将Java对象序列化为JSON格式字符串：
     * @param o 被序列的对象
     * @return 序列化后的字符串
     * @throws JsonProcessingException Json处理过程异常
     */
    public String serializeToStr(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    /**
     * 使用@ResponseBody注解可以使对象序列化为JSON格式字符串，除此之外，Jackson也提供了反序列化方法。
     *
     * 树遍历
     * 当采用树遍历的方式时，JSON被读入到JsonNode对象中，可以像操作XML DOM那样读取JSON。
     *
     * readTree方法可以接受一个字符串或者字节数组、文件、InputStream等， 返回JsonNode作为根节点，你可以像操作XML DOM那样操作遍历JsonNode以获取数据。
     * @param jsonStr json字符串对象
     * @return 测试结果
     * @throws JsonProcessingException Json处理过程异常
     */
    public String readJsonString(String jsonStr) throws JsonProcessingException {
        JsonNode node = this.objectMapper.readTree(jsonStr);
        String name = node.get("username").asText();
        int status = node.get("status").asInt();

        //readTree方法可以接受一个字符串或者字节数组、文件、InputStream等， 返回JsonNode作为根节点，你可以像操作XML DOM那样操作遍历JsonNode以获取数据。
        String json = "{\"name\":\"mrbird\",\"hobby\":{\"first\":\"sleep\",\"second\":\"eat\"}}";;
        JsonNode node1 = this.objectMapper.readTree(json);
        JsonNode hobby = node.get("hobby");
        String first = hobby.get("first").asText();
        return name + " " + status;
    }

    /**
     * 绑定对象
     * 我们也可以将Java对象和JSON数据进行绑定，如下所示：
     * @return String test
     * @throws JsonProcessingException Json处理过程异常
     */
    public String readJsonAsObject() throws JsonProcessingException {
        String json = "{\"name\":\"mrbird\",\"age\":26}";
        SysUserEntity user = objectMapper.readValue(json, SysUserEntity.class);
        String name = user.getUsername();
        int age = user.getStatus();
        return name + " " + age;
    }
}
