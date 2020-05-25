package io.niufen.common.util;


import org.junit.Assert;
import org.junit.Test;

public class PinYinUtilsTest {

    @Test
    public void toPinYin() {
        Assert.assertEquals("zhanghaijun",PinYinUtils.toPinYin("张海军"));
    }
}
