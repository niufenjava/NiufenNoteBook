package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.BaseTest;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDefinedPropertiesTest extends BaseTest {

    @Autowired
    private UserDefinedProperties userDefinedProperties;

    @Test
    public void configTest() {
        Assert.assertNotNull(userDefinedProperties);
        Assert.assertEquals("NiufenProjectName",userDefinedProperties.getNiufenProjectName());
        Assert.assertTrue((1000 == userDefinedProperties.getNiufenProjectPort()));
        Assert.assertEquals(userDefinedProperties.getNiufenProjectName()+":"+userDefinedProperties.getNiufenProjectPort(),userDefinedProperties.getNiufenProjectTitle());
    }
}
