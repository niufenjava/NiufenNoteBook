package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class ProjectPropertiesTest extends BaseTest {

    @Autowired
    private ProjectProperties projectProperties;

    @Test
    public void configTest(){
        Assert.assertEquals(projectProperties.getName(),"SpringBootConfig");
        Assert.assertTrue((projectProperties.getLevel() == 3));
    }
}
