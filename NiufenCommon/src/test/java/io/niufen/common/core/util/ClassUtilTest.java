package io.niufen.common.core.util;

import io.niufen.common.core.convert.AbstractConverter;
import io.niufen.common.core.convert.IConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

@Slf4j
public class ClassUtilTest {

    @Test
    public void testGetClass() {
        assert null == ClassUtil.getClass(null);
        assert null != ClassUtil.getClass(this);
        assert ClassUtilTest.class == ClassUtil.getClass(this);
    }

    @Test
    public void getClasses() {
        Class<?>[] classes = ClassUtil.getClasses(null, 1, "a", new Object());
        assert 4 == classes.length;
        assert Object.class == classes[0];
        assert Integer.class == classes[1];
        assert String.class == classes[2];
        assert Object.class == classes[3];
    }

    @Test
    public void getEnclosingClass() {
        assert ClassUtilTest.class == ClassUtil.getEnclosingClass(InnerTestClass.class);
    }

    @Test
    public void isTopLevelClass() {
        assert ClassUtil.isTopLevelClass(ClassUtilTest.class);
        assert !ClassUtil.isTopLevelClass(InnerTestClass.class);
    }

    @Test
    public void getClassName() {
    }

    @Test
    public void classNameEquals() {
        assert ClassUtil.equals(ClassUtil.class,"io.niufen.common.util.ClassUtils",Boolean.FALSE);
        assert ClassUtil.equals(ClassUtil.class,"ClassUtils",Boolean.FALSE);
        assert ClassUtil.equals(ClassUtil.class,"io.niufen.common.util.classUtils",Boolean.TRUE);
        assert ClassUtil.equals(ClassUtil.class,"classUtils",Boolean.TRUE);
    }

    @Test
    public void getClassLoader() {
    }

    @Test
    public void getDefaultClassLoader() {
    }

    @Test
    public void isAssignable() {
        assert ClassUtil.isAssignable(IConverter.class, AbstractConverter.class);
        assert ClassUtil.isAssignable(Collection.class, List.class);
    }

    class InnerTestClass{
        class InnerInnerTestClass{

        }
    }
}
