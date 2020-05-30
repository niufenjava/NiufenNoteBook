package io.niufen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class ArrayUtilsTest {

    @Test
    public void isEmpty() {
    }

    @Test
    public void isNotEmpty() {
    }

    @Test
    public void testIsEmpty() {
    }

    @Test
    public void testIsNotEmpty() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void testContains() {
    }

    @Test
    public void indexOf() {
    }

    @Test
    public void toStringTest() {
        int[] iArray = {0, 1, 2, 3};
        Object[] oArray = {"1", 1, 1.2, Boolean.FALSE};
        log.debug("iArray print:{}",iArray); // [0, 1, 2, 3]
        log.debug("oArray print:{}",oArray); // 1
        log.debug("ArrayUtils.toString(iArray):{}",ArrayUtils.toString(iArray));
        log.debug("ArrayUtils.toString(oArray):{}",ArrayUtils.toString(oArray));
        assert "[0, 1, 2, 3]".equals(ArrayUtils.toString(iArray));
        assert "[1, 1, 1.2, false]".equals(ArrayUtils.toString(oArray));

    }

    @Test
    public void isArray() {
        int[] iArray = {0, 1, 2, 3};
        Object[] oArray = {"1", 1, 1.2, Boolean.FALSE};
        assert ArrayUtils.isArray(iArray);
        assert ArrayUtils.isArray(oArray);
        assert !ArrayUtils.isArray(new Object());
    }
}
