package io.niufen.common.core.annotation;

import org.junit.Test;

public class AnnotationUtilTest {

    @Test
    public void toCombination() {
        TestApplication testApplication = new TestApplication();
        assert AnnotationUtil.toCombination(testApplication.getClass()) instanceof CombinationAnnotationElement;
        assert AnnotationUtil.toCombination(TestApplication.class) instanceof CombinationAnnotationElement;
        assert AnnotationUtil.toCombination(testApplication.getClass()).getAnnotations().length == 4;
    }

    @Test
    public void getAnnotations() {
        TestApplication testApplication = new TestApplication();
        assert 4 == AnnotationUtil.getAnnotations(testApplication.getClass(),Boolean.TRUE).length;
        assert 1 == AnnotationUtil.getAnnotations(testApplication.getClass(),Boolean.FALSE).length;
        assert null == AnnotationUtil.getAnnotations(null,Boolean.FALSE);

        assert 1 ==testApplication.getClass().getAnnotations().length;

    }

    @Test
    public void getAnnotation() {
        TestApplication testApplication = new TestApplication();
        assert AnnotationUtil.getAnnotation(testApplication.getClass(),TestSpringBootApplication.class) instanceof TestSpringBootApplication;
        assert AnnotationUtil.getAnnotation(testApplication.getClass(),TestComponentScan.class) instanceof TestComponentScan;
        assert AnnotationUtil.getAnnotation(testApplication.getClass(),TestSpringBootConfiguration.class) instanceof TestSpringBootConfiguration;
        assert AnnotationUtil.getAnnotation(testApplication.getClass(),TestEnableAutoConfiguration.class) instanceof TestEnableAutoConfiguration;

        // 通过JDK原生的方法，不能获取到组合注解中的子注解
        assert testApplication.getClass().getAnnotation(TestSpringBootApplication.class) instanceof TestSpringBootApplication;
        assert null == testApplication.getClass().getAnnotation(TestComponentScan.class);
        assert null == testApplication.getClass().getAnnotation(TestSpringBootConfiguration.class);
        assert null == testApplication.getClass().getAnnotation(TestEnableAutoConfiguration.class);

    }


}

