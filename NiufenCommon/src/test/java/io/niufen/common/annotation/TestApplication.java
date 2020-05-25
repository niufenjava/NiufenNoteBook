package io.niufen.common.annotation;

import java.lang.annotation.*;

/**
 * @author haijun.zhang
 * @date 2020/5/25
 * @time 17:05
 */
@TestSpringBootApplication
public class TestApplication {

    @Alias("testName")
    private String name;

}



@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@interface TestComponentScan {

}

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@interface TestSpringBootConfiguration {

}

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@interface TestEnableAutoConfiguration {

}



@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@TestComponentScan
@TestSpringBootConfiguration
@TestEnableAutoConfiguration
@interface TestSpringBootApplication {

}
