package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

public class OOSAliConfigTest extends BaseTest {

    @Autowired
    private OOSAliConfig oosAliConfig;

    @Test
    public void configTest() {
        Assert.assertNotNull(oosAliConfig);
    }
}
