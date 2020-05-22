package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class StaticPropertiesTest extends BaseTest {

    @Test
    public void configTest(){
        Assert.assertTrue("dev".equals(StaticProperties.springProfilesActive)
                || "prod".equals(StaticProperties.springProfilesActive));
    }
}
