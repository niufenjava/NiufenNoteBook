package io.niufen.common.util;

import io.niufen.common.convert.AbstractConverter;
import io.niufen.common.convert.IConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

@Slf4j
public class ClassUtilsTest {

    @Test
    public void testGetClass() {
        assert null == ClassUtils.getClass(null);
        assert null != ClassUtils.getClass(this);
        assert ClassUtilsTest.class == ClassUtils.getClass(this);
    }

    @Test
    public void getClasses() {
        Class<?>[] classes = ClassUtils.getClasses(null, 1, "a", new Object());
        assert 4 == classes.length;
        assert Object.class == classes[0];
        assert Integer.class == classes[1];
        assert String.class == classes[2];
        assert Object.class == classes[3];
    }

    @Test
    public void getEnclosingClass() {
        assert ClassUtilsTest.class == ClassUtils.getEnclosingClass(InnerTestClass.class);
    }

    @Test
    public void isTopLevelClass() {
        assert ClassUtils.isTopLevelClass(ClassUtilsTest.class);
        assert !ClassUtils.isTopLevelClass(InnerTestClass.class);
    }

    @Test
    public void getClassName() {
        ClassUtils classUtils = new ClassUtils();
        assert "io.niufen.common.util.ClassUtils".equals(ClassUtils.getClassName(classUtils));
        assert "ClassUtils".equals(ClassUtils.getSimpleClassName(classUtils));
        assert "i.n.c.u.ClassUtils".equals(ClassUtils.getShortClassName(classUtils));
    }

    @Test
    public void classNameEquals() {
        assert ClassUtils.equals(ClassUtils.class,"io.niufen.common.util.ClassUtils",Boolean.FALSE);
        assert ClassUtils.equals(ClassUtils.class,"ClassUtils",Boolean.FALSE);
        assert ClassUtils.equals(ClassUtils.class,"io.niufen.common.util.classUtils",Boolean.TRUE);
        assert ClassUtils.equals(ClassUtils.class,"classUtils",Boolean.TRUE);
    }

    @Test
    public void getClassLoader() {
    }

    @Test
    public void getDefaultClassLoader() {
    }

    @Test
    public void isAssignable() {
        assert ClassUtils.isAssignable(IConverter.class, AbstractConverter.class);
        assert ClassUtils.isAssignable(Collection.class, List.class);
    }

    class InnerTestClass{
        class InnerInnerTestClass{

        }
    }
}
