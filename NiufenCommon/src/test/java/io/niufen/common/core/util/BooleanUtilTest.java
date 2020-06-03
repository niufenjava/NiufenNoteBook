package io.niufen.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class BooleanUtilTest {

    @Test
    public void negate() {
        assert BooleanUtil.negate(false);
        assert BooleanUtil.negate(Boolean.FALSE);
        assert !BooleanUtil.negate(true);
        assert !BooleanUtil.negate(Boolean.TRUE);
    }

    @Test
    public void toByte() {
        assert 0 == BooleanUtil.toByte(false);
        assert 0 == BooleanUtil.toByte(Boolean.FALSE);
        assert 1 == BooleanUtil.toByte(true);
        assert 1 == BooleanUtil.toByte(Boolean.TRUE);
    }
}
