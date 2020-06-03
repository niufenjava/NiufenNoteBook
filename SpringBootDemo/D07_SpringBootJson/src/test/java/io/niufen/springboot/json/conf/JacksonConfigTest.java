package io.niufen.springboot.json.conf;

import io.niufen.springboot.json.Base64ApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JacksonConfigTest extends Base64ApiTest {

    @Test
    public void getObjectMapper() {
        String url = "/sysUser/detail/10000";
        get(url);
    }
}
