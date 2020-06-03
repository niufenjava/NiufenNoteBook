package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.Base64Test;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectPropertiesTest extends Base64Test {

    @Autowired
    private ProjectProperties projectProperties;

    @Test
    public void configTest(){
        Assert.assertEquals(projectProperties.getName(),"SpringBootConfig");
        Assert.assertTrue((projectProperties.getLevel() == 3));
    }
}
