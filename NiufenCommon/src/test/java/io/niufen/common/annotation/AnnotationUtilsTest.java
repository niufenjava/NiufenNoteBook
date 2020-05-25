package io.niufen.common.annotation;

import org.junit.Test;

public class AnnotationUtilsTest {

    @Test
    public void toCombination() {
        TestApplication testApplication = new TestApplication();
        assert AnnotationUtils.toCombination(testApplication.getClass()) instanceof CombinationAnnotationElement;
        assert AnnotationUtils.toCombination(TestApplication.class) instanceof CombinationAnnotationElement;
        assert AnnotationUtils.toCombination(testApplication.getClass()).getAnnotations().length == 4;
    }

    @Test
    public void getAnnotations() {
        TestApplication testApplication = new TestApplication();
        assert 4 == AnnotationUtils.getAnnotations(testApplication.getClass(),Boolean.TRUE).length;
        assert 1 == AnnotationUtils.getAnnotations(testApplication.getClass(),Boolean.FALSE).length;
        assert null == AnnotationUtils.getAnnotations(null,Boolean.FALSE);

        assert 1 ==testApplication.getClass().getAnnotations().length;

    }

    @Test
    public void getAnnotation() {
        TestApplication testApplication = new TestApplication();
        assert AnnotationUtils.getAnnotation(testApplication.getClass(),TestSpringBootApplication.class) instanceof TestSpringBootApplication;
        assert AnnotationUtils.getAnnotation(testApplication.getClass(),TestComponentScan.class) instanceof TestComponentScan;
        assert AnnotationUtils.getAnnotation(testApplication.getClass(),TestSpringBootConfiguration.class) instanceof TestSpringBootConfiguration;
        assert AnnotationUtils.getAnnotation(testApplication.getClass(),TestEnableAutoConfiguration.class) instanceof TestEnableAutoConfiguration;

        // 通过JDK原生的方法，不能获取到组合注解中的子注解
        assert testApplication.getClass().getAnnotation(TestSpringBootApplication.class) instanceof TestSpringBootApplication;
        assert null == testApplication.getClass().getAnnotation(TestComponentScan.class);
        assert null == testApplication.getClass().getAnnotation(TestSpringBootConfiguration.class);
        assert null == testApplication.getClass().getAnnotation(TestEnableAutoConfiguration.class);

    }


}

