package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.Base64Test;
import org.junit.Assert;
import org.junit.Test;

public class StaticPropertiesTest extends Base64Test {

    @Test
    public void configTest(){
        Assert.assertTrue("dev".equals(StaticProperties.springProfilesActive)
                || "prod".equals(StaticProperties.springProfilesActive));
    }
}
