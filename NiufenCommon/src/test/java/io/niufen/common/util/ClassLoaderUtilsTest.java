package io.niufen.common.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassLoaderUtilsTest {

    @Test
    public void getContextClassLoader() {
    }

    @Test
    public void getClassLoader() {
        ClassLoader contextClassLoader = ClassLoaderUtils.getContextClassLoader();
        assert null != contextClassLoader;
        ClassLoader classLoader = ClassLoaderUtils.getClassLoader();
        assert classLoader == contextClassLoader;
    }

    @Test
    public void loadClass() {
        String name = ClassLoaderUtils.loadClass("java.lang.Thread.State").getName();
        org.junit.Assert.assertEquals("java.lang.Thread$State", name);

        name = ClassLoaderUtils.loadClass("java.lang.Thread$State").getName();
        Assert.assertEquals("java.lang.Thread$State", name);
    }

    @Test
    public void loadPrimitiveClass() {
        Class<?> aClass = ClassLoaderUtils.loadPrimitiveClass("int");
        assert aClass.getSimpleName().equals("int");
        Class<?> vClass = ClassLoaderUtils.loadPrimitiveClass("void");
        assert "void".equals(vClass.getSimpleName());
    }

    @Test
    public void isPresent() {
        assert ClassLoaderUtils.isPresent("int");
        assert !ClassLoaderUtils.isPresent("integer");
    }

}
