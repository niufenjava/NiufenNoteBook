package io.niufen.common.core.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author haijun.zhang
 * @date 2020/5/23
 * @time 14:15
 */
@Slf4j
public class AliasTest {

    @Test
    public void aliasFieldTest(){

        // getDeclaredFields 取得本类的全部属性，包括私有的
        // declare 声明
        for (Field field : TestClass.class.getDeclaredFields()) {

            // 测试类的 name属性 使用 @Alias 进行注解
            if("name".equals(field.getName())){
                // 判断是否存在传入的注解
                // present 存在、出现
                assert field.isAnnotationPresent(Alias.class);
                // 验证字段别名
                assert "testName".equals(field.getAnnotation(Alias.class).value());
                assert !"name".equals(field.getAnnotation(Alias.class).value());
            }else {
                // 测试类的 password属性 未使用 @Alias 进行注解
                assert !field.isAnnotationPresent(Alias.class);
            }
        }
    }

    @Test
    public void aliasMethodTest(){
        log.debug(TestClass.class.getDeclaredMethods().length+"");
        for (Method declaredMethod : TestClass.class.getDeclaredMethods()) {
            if("getName".equals(declaredMethod.getName())){
                // 判断是否存在传入的注解
                assert declaredMethod.isAnnotationPresent(Alias.class);
                // 验证字段别名
                assert "getTestName".equals(declaredMethod.getAnnotation(Alias.class).value());
                assert !"getName".equals(declaredMethod.getAnnotation(Alias.class).value());
            }else {
                // 测试类的 password属性 未使用 @Alias 进行注解
                assert !declaredMethod.isAnnotationPresent(Alias.class);
            }
        }
    }

    @Test
    public void aliasArgumentsTest(){
        log.debug(TestClass.class.getDeclaredMethods().length+"");
        for (Method declaredMethod : TestClass.class.getDeclaredMethods()) {
            for (Parameter parameter : declaredMethod.getParameters()) {
                log.debug("declaredMethod:{},parameter:{}", declaredMethod.getName(), parameter);
                if("setName".equals(declaredMethod.getName())){
                    assert parameter.isAnnotationPresent(Alias.class);
                }else {
                    assert !parameter.isAnnotationPresent(Alias.class);
                }
            }
        }
    }
}

class TestClass {
    @Alias("testName")
    private String name;

    private String password;

    @Alias("getTestName")
    public String getName() {
        return name;
    }

    public void setName(@Alias("testName") String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
