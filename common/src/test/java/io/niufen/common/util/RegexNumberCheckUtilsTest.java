package io.niufen.common.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;

public class RegexNumberCheckUtilsTest {

    /**
     *
     */
    @Test
    public void isNumber() {
        Assert.assertEquals(Boolean.TRUE, RegexNumberCheckUtils.isNumber("123"));
        Assert.assertEquals(Boolean.TRUE, RegexNumberCheckUtils.isNumber("00123"));
        Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumber("123.1"));
        Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumber("123A"));
        Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumber(null));
        Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumber(""));
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(Boolean.TRUE, RegexNumberCheckUtils.isNumber("123"));
            Assert.assertEquals(Boolean.TRUE, RegexNumberCheckUtils.isNumber("00123"));
            //Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumber("123.1"));
            //Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumber("123A"));
        }
        System.out.println(System.currentTimeMillis()-startTime);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(Boolean.TRUE, RegexNumberCheckUtils.isNumberCompile("123"));
            Assert.assertEquals(Boolean.TRUE, RegexNumberCheckUtils.isNumberCompile("00123"));
            //Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumberCompile("123.1"));
            //Assert.assertEquals(Boolean.FALSE, RegexNumberCheckUtils.isNumberCompile("123A"));
        }
        System.out.println(System.currentTimeMillis()-startTime);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(Boolean.TRUE, NumberUtils.isCreatable("123"));
            Assert.assertEquals(Boolean.TRUE, NumberUtils.isCreatable("00123"));
            //Assert.assertEquals(Boolean.TRUE, NumberUtils.isCreatable("123.1"));
            //Assert.assertEquals(Boolean.FALSE, NumberUtils.isCreatable("123A"));
        }
        System.out.println(System.currentTimeMillis()-startTime);
    }
}
