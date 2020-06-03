package io.niufen.common.core.util;


import org.junit.Assert;
import org.junit.Test;

public class PinYinUtilTest {

    @Test
    public void toPinYin() {
        Assert.assertEquals("zhanghaijun", PinYinUtil.toPinYin("张海军"));
    }
}
