package io.niufen.common.core.convert;

import org.junit.Test;

public class BasicTypeTest {

    @Test
    public void wrap() {
        assert Byte.class == BasicType.wrap(byte.class);
        assert Short.class == BasicType.wrap(short.class);
        assert Integer.class == BasicType.wrap(int.class);
        assert Long.class == BasicType.wrap(long.class);
        assert Float.class == BasicType.wrap(float.class);
        assert Double.class == BasicType.wrap(double.class);
        assert Character.class == BasicType.wrap(char.class);
        assert Boolean.class == BasicType.wrap(boolean.class);
    }

    @Test
    public void unWrap() {
        assert byte.class == BasicType.unWrap(Byte.class);
        assert short.class == BasicType.unWrap(Short.class);
        assert int.class == BasicType.unWrap(Integer.class);
        assert long.class == BasicType.unWrap(Long.class);
        assert float.class == BasicType.unWrap(Float.class);
        assert double.class == BasicType.unWrap(Double.class);
        assert char.class == BasicType.unWrap(Character.class);
        assert boolean.class == BasicType.unWrap(Boolean.class);
    }
}
