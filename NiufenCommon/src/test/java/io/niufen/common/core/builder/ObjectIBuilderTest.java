package io.niufen.common.core.builder;

import org.junit.Test;

public class ObjectIBuilderTest {

    @Test
    public void build() {
        ObjectIBuilder objectBuilder = new ObjectIBuilder();
        assert null != objectBuilder.build();
    }
}
