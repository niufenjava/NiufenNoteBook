package io.niufen.springbootconfig.property;

import io.niufen.springbootconfig.Base64Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

public class OOSAliConfigTest extends Base64Test {

    @Autowired
    private OOSAliConfig oosAliConfig;

    @Test
    public void configTest() {
        Assert.assertNotNull(oosAliConfig);
    }
}
