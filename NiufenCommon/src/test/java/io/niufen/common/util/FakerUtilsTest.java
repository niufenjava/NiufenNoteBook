package io.niufen.common.util;

import io.niufen.common.tool.ObjectTools;
import org.junit.Assert;
import org.junit.Test;

public class FakerUtilsTest {

    @Test
    public void numberRandom() {
        Assert.assertEquals(0,FakerUtils.numberRandom(0,0));
        Assert.assertTrue(ObjectTools.in(FakerUtils.numberRandom(0,2),0,1,2));
        Assert.assertTrue(ObjectTools.in(FakerUtils.numberRandom(0,3),0,1,2,3));
        Assert.assertTrue(ObjectTools.in(FakerUtils.numberRandom(1,2),1,2));
        Assert.assertFalse(ObjectTools.in(FakerUtils.numberRandom(1,2),0));
    }

    @Test
    public void nameCN() {
        Assert.assertFalse(ObjectTools.isChineseStr(FakerUtils.nameEN()));
        Assert.assertTrue(ObjectTools.isChineseStr(FakerUtils.nameCN()));
    }

    @Test
    public void nameEN() {
        Assert.assertFalse(ObjectTools.isChineseStr(FakerUtils.nameEN()));
        Assert.assertTrue(ObjectTools.isChineseStr(FakerUtils.nameCN()));
    }

    @Test
    public void sex() {
        Assert.assertTrue(ObjectTools.in(FakerUtils.sex(),1,2));
        Assert.assertFalse(ObjectTools.in(FakerUtils.sex(),0));
    }

    @Test
    public void namePinYin() {
        Assert.assertFalse(ObjectTools.isChineseStr(FakerUtils.namePinYin()));
    }

    @Test
    public void cellPhone() {
        String cellPhone = FakerUtils.cellPhone();
        Assert.assertTrue(ObjectTools.isNumber(cellPhone));
        Assert.assertTrue(ObjectTools.equals(11,cellPhone.length()));
    }

    @Test
    public void email() {
        String email = FakerUtils.email();
        Assert.assertTrue(email.contains("@"));
    }
}
