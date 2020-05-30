package io.niufen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class BooleanUtilsTest {

    @Test
    public void negate() {
        assert BooleanUtils.negate(false);
        assert BooleanUtils.negate(Boolean.FALSE);
        assert !BooleanUtils.negate(true);
        assert !BooleanUtils.negate(Boolean.TRUE);
    }

    @Test
    public void toByte() {
        assert 0 == BooleanUtils.toByte(false);
        assert 0 == BooleanUtils.toByte(Boolean.FALSE);
        assert 1 == BooleanUtils.toByte(true);
        assert 1 == BooleanUtils.toByte(Boolean.TRUE);
    }
}
