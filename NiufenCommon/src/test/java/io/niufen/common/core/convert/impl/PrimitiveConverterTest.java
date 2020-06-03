package io.niufen.common.core.convert.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PrimitiveConverterTest {

    @Test
    public void convert() {
        PrimitiveConverter converter = new PrimitiveConverter(int.class);
        int convert = (int) converter.convert("1", null);
        assert convert == 1;

        // 如果超过int的最大值，则返回0
        convert = (int) converter.convert("10000000000", null);
        assert convert == 0;
    }
}
