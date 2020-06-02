package io.niufen.common.util;

import io.niufen.common.tool.ObjectTools;
import org.junit.Assert;
import org.junit.Test;

public class FakerUtilTest {

    @Test
    public void numberRandom() {
        Assert.assertEquals(0, FakerUtil.numberRandom(0,0));
        Assert.assertTrue(ObjectTools.in(FakerUtil.numberRandom(0,2),0,1,2));
        Assert.assertTrue(ObjectTools.in(FakerUtil.numberRandom(0,3),0,1,2,3));
        Assert.assertTrue(ObjectTools.in(FakerUtil.numberRandom(1,2),1,2));
        Assert.assertFalse(ObjectTools.in(FakerUtil.numberRandom(1,2),0));
    }

    @Test
    public void nameCN() {
        Assert.assertFalse(ObjectTools.isChineseStr(FakerUtil.nameEN()));
        Assert.assertTrue(ObjectTools.isChineseStr(FakerUtil.nameCN()));
    }

    @Test
    public void nameEN() {
        Assert.assertFalse(ObjectTools.isChineseStr(FakerUtil.nameEN()));
        Assert.assertTrue(ObjectTools.isChineseStr(FakerUtil.nameCN()));
    }

    @Test
    public void sex() {
        Assert.assertTrue(ObjectTools.in(FakerUtil.sex(),1,2));
        Assert.assertFalse(ObjectTools.in(FakerUtil.sex(),0));
    }

    @Test
    public void namePinYin() {
        Assert.assertFalse(ObjectTools.isChineseStr(FakerUtil.namePinYin()));
    }

    @Test
    public void cellPhone() {
        String cellPhone = FakerUtil.cellPhone();
        Assert.assertTrue(ObjectTools.isNumber(cellPhone));
        Assert.assertTrue(ObjectTools.equals(11,cellPhone.length()));
    }

    @Test
    public void email() {
        String email = FakerUtil.email();
        Assert.assertTrue(email.contains("@"));
    }
}
