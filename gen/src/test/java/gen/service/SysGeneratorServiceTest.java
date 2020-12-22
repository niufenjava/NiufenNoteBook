package gen.service;

import com.linkdoc.gen.GenApplication;
import com.linkdoc.gen.service.SysGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GenApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SysGeneratorServiceTest {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    @Test
    public void generatorCode() {
        String[] tableNames = {"tbl_json"};
        sysGeneratorService.localgeneratorCode(tableNames);
    }
}
