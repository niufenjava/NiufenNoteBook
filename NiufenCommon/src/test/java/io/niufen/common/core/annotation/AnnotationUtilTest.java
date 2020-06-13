package io.niufen.common.core.annotation;

import org.junit.Test;

public class AnnotationUtilTest {
    @Test
    public void MyAnnotationTest() {
        // 这里是检查Annotation类是否有注解，这里需要使用反射才能完成对Annotation类的检查
        if (AnnotationUse.class.isAnnotationPresent(MyAnnotation.class)) {
            /*
             * MyAnnotation是一个类，这个类的实例对象annotation是通过反射得到的，这个实例对象是如何创建的呢？
             * 一旦在某个类上使用了@MyAnnotation，那么这个MyAnnotation类的实例对象annotation就会被创建出来了
             * 假设很多人考驾照，教练在有些学员身上贴一些绿牌子、黄牌子，贴绿牌子的表示送礼送得比较多的，
             * 贴黄牌子的学员表示送礼送得比较少的，不贴牌子的学员表示没有送过礼的，通过这个牌子就可以标识出不同的学员
             * 教官在考核时一看，哦，这个学员是有牌子的，是送过礼给他的，优先让有牌子的学员过，此时这个牌子就是一个注解
             * 一个牌子就是一个注解的实例对象，实实在在存在的牌子就是一个实实在在的注解对象，把牌子拿下来(去掉注解)注解对象就不存在了
             */
            MyAnnotation annotation = (MyAnnotation) AnnotationUse.class.getAnnotation(MyAnnotation.class);
            System.out.println(annotation);// 打印MyAnnotation对象，这里输出的结果为：@cn.itcast.day2.MyAnnotation()
        }
    }

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
        assert 4 == AnnotationUtil.getAnnotations(testApplication.getClass(), Boolean.TRUE).length;
        assert 1 == AnnotationUtil.getAnnotations(testApplication.getClass(), Boolean.FALSE).length;
        assert null == AnnotationUtil.getAnnotations(null, Boolean.FALSE);

        assert 1 == testApplication.getClass().getAnnotations().length;

    }

    @Test
    public void getAnnotation() {
        TestApplication testApplication = new TestApplication();
        assert AnnotationUtil.getAnnotation(testApplication.getClass(), TestSpringBootApplication.class) instanceof TestSpringBootApplication;
        assert AnnotationUtil.getAnnotation(testApplication.getClass(), TestComponentScan.class) instanceof TestComponentScan;
        assert AnnotationUtil.getAnnotation(testApplication.getClass(), TestSpringBootConfiguration.class) instanceof TestSpringBootConfiguration;
        assert AnnotationUtil.getAnnotation(testApplication.getClass(), TestEnableAutoConfiguration.class) instanceof TestEnableAutoConfiguration;

        // 通过JDK原生的方法，不能获取到组合注解中的子注解
        assert testApplication.getClass().getAnnotation(TestSpringBootApplication.class) instanceof TestSpringBootApplication;
        assert null == testApplication.getClass().getAnnotation(TestComponentScan.class);
        assert null == testApplication.getClass().getAnnotation(TestSpringBootConfiguration.class);
        assert null == testApplication.getClass().getAnnotation(TestEnableAutoConfiguration.class);

    }
}

@MyAnnotation
class AnnotationUse {

}

