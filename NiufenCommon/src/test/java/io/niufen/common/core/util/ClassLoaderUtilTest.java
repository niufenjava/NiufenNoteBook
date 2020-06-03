package io.niufen.common.core.util;

import org.junit.Assert;
import org.junit.Test;

public class ClassLoaderUtilTest {

    @Test
    public void getContextClassLoader() {
    }

    @Test
    public void getClassLoader() {
        ClassLoader contextClassLoader = ClassLoaderUtil.getContextClassLoader();
        assert null != contextClassLoader;
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        assert classLoader == contextClassLoader;
    }

    @Test
    public void loadClass() {
        String name = ClassLoaderUtil.loadClass("java.lang.Thread.State").getName();
        org.junit.Assert.assertEquals("java.lang.Thread$State", name);

        name = ClassLoaderUtil.loadClass("java.lang.Thread$State").getName();
        Assert.assertEquals("java.lang.Thread$State", name);
    }

    @Test
    public void loadPrimitiveClass() {
        Class<?> aClass = ClassLoaderUtil.loadPrimitiveClass("int");
        assert aClass.getSimpleName().equals("int");
        Class<?> vClass = ClassLoaderUtil.loadPrimitiveClass("void");
        assert "void".equals(vClass.getSimpleName());
    }

    @Test
    public void isPresent() {
        assert ClassLoaderUtil.isPresent("int");
        assert !ClassLoaderUtil.isPresent("integer");
    }

}
