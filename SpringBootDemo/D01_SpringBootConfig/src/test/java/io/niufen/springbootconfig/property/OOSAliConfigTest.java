package io.niufen.springbootconfig.property;

import io.niufen.common.util.MyStringUtils;
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
        Assert.assertTrue(MyStringUtils.isNotBlank(oosAliConfig.getAccessKey()));
        Assert.assertTrue(MyStringUtils.isNotBlank(oosAliConfig.getAccessKeySecret()));
        Assert.assertTrue(MyStringUtils.isNotBlank(oosAliConfig.getEndPoint()));
        Assert.assertTrue(MyStringUtils.isNotBlank(oosAliConfig.getBucketName()));
    }
}
