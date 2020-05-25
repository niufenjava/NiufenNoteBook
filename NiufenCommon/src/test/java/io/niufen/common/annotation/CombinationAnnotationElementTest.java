package io.niufen.common.annotation;

import org.junit.Test;

import java.lang.annotation.*;

public class CombinationAnnotationElementTest {

    @Test
    public void combinationAnnotationElement(){
        TestApplication testApplication = new TestApplication();
        CombinationAnnotationElement combinationAnnotationElement = new CombinationAnnotationElement(testApplication.getClass());
        assert null != combinationAnnotationElement.getAnnotation(TestSpringBootApplication.class);
        assert null != combinationAnnotationElement.getAnnotation(TestComponentScan.class);
        assert null != combinationAnnotationElement.getAnnotation(TestSpringBootConfiguration.class);
        assert null != combinationAnnotationElement.getAnnotation(TestEnableAutoConfiguration.class);

        assert null == combinationAnnotationElement.getAnnotation(Target.class);
        assert null == combinationAnnotationElement.getAnnotation(Retention.class);
        assert null == combinationAnnotationElement.getAnnotation(Documented.class);

    }

    @Test
    public void isAnnotationPresent() {
        TestApplication testApplication = new TestApplication();
        CombinationAnnotationElement combinationAnnotationElement = new CombinationAnnotationElement(testApplication.getClass());
        assert combinationAnnotationElement.isAnnotationPresent(TestSpringBootApplication.class);
        assert combinationAnnotationElement.isAnnotationPresent(TestComponentScan.class);
        assert combinationAnnotationElement.isAnnotationPresent(TestSpringBootConfiguration.class);
        assert combinationAnnotationElement.isAnnotationPresent(TestEnableAutoConfiguration.class);

        assert !combinationAnnotationElement.isAnnotationPresent(Target.class);
        assert !combinationAnnotationElement.isAnnotationPresent(Retention.class);
        assert !combinationAnnotationElement.isAnnotationPresent(Documented.class);
    }

    @Test
    public void getAnnotation() {
        TestApplication testApplication = new TestApplication();
        CombinationAnnotationElement combinationAnnotationElement = new CombinationAnnotationElement(testApplication.getClass());
        assert null != combinationAnnotationElement.getAnnotation(TestSpringBootApplication.class);
        assert null !=  combinationAnnotationElement.getAnnotation(TestComponentScan.class);
        assert null !=  combinationAnnotationElement.getAnnotation(TestSpringBootConfiguration.class);
        assert null !=  combinationAnnotationElement.getAnnotation(TestEnableAutoConfiguration.class);

        assert null ==  combinationAnnotationElement.getAnnotation(Target.class);
        assert null ==  combinationAnnotationElement.getAnnotation(Retention.class);
        assert null ==  combinationAnnotationElement.getAnnotation(Documented.class);
    }

    @Test
    public void getAnnotations() {
        TestApplication testApplication = new TestApplication();
        CombinationAnnotationElement combinationAnnotationElement = new CombinationAnnotationElement(testApplication.getClass());
        assert combinationAnnotationElement.getAnnotations().length == 4;
    }

    @Test
    public void getDeclaredAnnotations() {
        TestApplication testApplication = new TestApplication();
        CombinationAnnotationElement combinationAnnotationElement = new CombinationAnnotationElement(testApplication.getClass());
        assert combinationAnnotationElement.getDeclaredAnnotations().length == 4;
    }
}
