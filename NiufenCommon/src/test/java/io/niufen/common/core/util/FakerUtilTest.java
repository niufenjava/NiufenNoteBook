package io.niufen.common.core.util;

import org.junit.Assert;
import org.junit.Test;

public class FakerUtilTest {

    @Test
    public void numberRandom() {
        Assert.assertEquals(0, FakerUtil.numberRandom(0,0));
        Assert.assertTrue(ObjectUtil.in(FakerUtil.numberRandom(0,2),0,1,2));
        Assert.assertTrue(ObjectUtil.in(FakerUtil.numberRandom(0,3),0,1,2,3));
        Assert.assertTrue(ObjectUtil.in(FakerUtil.numberRandom(1,2),1,2));
        Assert.assertFalse(ObjectUtil.in(FakerUtil.numberRandom(1,2),0));
    }

    @Test
    public void nameCN() {
    }

    @Test
    public void nameEN() {
    }

    @Test
    public void sex() {
        Assert.assertTrue(ObjectUtil.in(FakerUtil.sex(),1,2));
        Assert.assertFalse(ObjectUtil.in(FakerUtil.sex(),0));
    }

    @Test
    public void namePinYin() {
    }

    @Test
    public void cellPhone() {
        String cellPhone = FakerUtil.cellPhone();
    }

    @Test
    public void email() {
        String email = FakerUtil.email();
        Assert.assertTrue(email.contains("@"));
    }
}
