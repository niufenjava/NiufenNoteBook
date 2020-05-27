package io.niufen.common.convert;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTypeEnumTest {

    @Test
    public void wrap() {
        assert Byte.class == BasicTypeEnum.wrap(byte.class);
        assert Short.class == BasicTypeEnum.wrap(short.class);
        assert Integer.class == BasicTypeEnum.wrap(int.class);
        assert Long.class == BasicTypeEnum.wrap(long.class);
        assert Float.class == BasicTypeEnum.wrap(float.class);
        assert Double.class == BasicTypeEnum.wrap(double.class);
        assert Character.class == BasicTypeEnum.wrap(char.class);
        assert Boolean.class == BasicTypeEnum.wrap(boolean.class);
    }

    @Test
    public void unWrap() {
        assert byte.class == BasicTypeEnum.unWrap(Byte.class);
        assert short.class == BasicTypeEnum.unWrap(Short.class);
        assert int.class == BasicTypeEnum.unWrap(Integer.class);
        assert long.class == BasicTypeEnum.unWrap(Long.class);
        assert float.class == BasicTypeEnum.unWrap(Float.class);
        assert double.class == BasicTypeEnum.unWrap(Double.class);
        assert char.class == BasicTypeEnum.unWrap(Character.class);
        assert boolean.class == BasicTypeEnum.unWrap(Boolean.class);
    }
}
