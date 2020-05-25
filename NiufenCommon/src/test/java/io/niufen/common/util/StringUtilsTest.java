package io.niufen.common.util;

import io.niufen.common.constant.StringConstants;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void isBlank() {
        String blank = "	  　";
        assert StringUtils.isBlank(blank);
        assert StringUtils.isBlank(StringConstants.Mark.SPACE);
        assert StringUtils.isBlank(StringConstants.Mark.SPACE_CN);
        assert !StringUtils.isBlank(blank+"1");

        String blank2 = "\u202a";
        Assert.assertTrue(StringUtils.isBlank(blank2));
    }

    @Test
    public void isBlankIfStr() {
        Object blank = "	  　";
        assert StringUtils.isBlankIfStr(blank);
        assert StringUtils.isBlankIfStr(StringConstants.Mark.SPACE);
        assert StringUtils.isBlankIfStr(StringConstants.Mark.SPACE_CN);
        assert !StringUtils.isBlankIfStr(blank+"1");
    }

    @Test
    public void isNotBlank() {
        String blank = "	  　";
        assert !StringUtils.isNotBlank(blank);
        assert !StringUtils.isNotBlank(StringConstants.Mark.SPACE);
        assert !StringUtils.isNotBlank(StringConstants.Mark.SPACE_CN);
        assert StringUtils.isNotBlank(blank+"1");
    }

    @Test
    public void hasBlank() {
        String blank = "	  　";
        assert StringUtils.hasBlank(blank);
        assert StringUtils.hasBlank(StringConstants.Mark.SPACE);
        assert StringUtils.hasBlank(StringConstants.Mark.SPACE_CN);
        assert StringUtils.hasBlank("a","b","");
        assert !StringUtils.hasBlank("a","b");
    }

    @Test
    public void isAllBlank() {
        String blank = "	  　";
        assert StringUtils.isAllBlank(blank);
        assert StringUtils.isAllBlank(StringConstants.Mark.SPACE);
        assert StringUtils.isAllBlank(StringConstants.Mark.SPACE_CN);
        assert !StringUtils.isAllBlank("a","b","");
        assert StringUtils.isAllBlank(""," ","   ");
    }

    @Test
    public void isEmpty() {
        String blank = "	  　";
        assert !StringUtils.isEmpty(blank);
        assert !StringUtils.isEmpty(StringConstants.Mark.SPACE);
        assert !StringUtils.isEmpty(StringConstants.Mark.SPACE_CN);

        assert StringUtils.isEmpty("");
        assert StringUtils.isEmpty(null);
    }

    @Test
    public void isEmptyIfStr() {

        assert StringUtils.isEmptyIfStr("");
        assert StringUtils.isEmptyIfStr(null);
    }
    @Test
    public void isNotEmpty() {
        assert StringUtils.isNotEmpty(" ");

        assert !StringUtils.isNotEmpty("");
        assert !StringUtils.isNotEmpty(null);

    }

}
