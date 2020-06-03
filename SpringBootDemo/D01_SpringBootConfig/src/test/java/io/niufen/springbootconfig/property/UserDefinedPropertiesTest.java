package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.Base64Test;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDefinedPropertiesTest extends Base64Test {

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
