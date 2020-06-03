package io.niufen.common.core.annotation;

import java.lang.annotation.*;

/**
 * 别名注解，使用此注解的 字段、方法、参数 等会有一个别名，用于 Bean 拷贝、Bean 转 Map 等
 *
 * 1. @Documented 注解是否将包含在 JavaDoc 中
 * 2. @Retention：什么时候使用该注解
 *  2.1 RetentionPolicy.RUNTIME : 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。我们自定义的注解通常使用这种方式。
 * 3. @Target：注解用于什么地方
 *  3.1 ElementType.FIELD：成员变量、对象、属性（包括enum实例）
 *  3.2 ElementType.METHOD：用于描述方法
 *  3.3 ElementType.PARAMETER：用于描述参数
 *
 * @author niufen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Alias {

    /**
     * 别名值，即使用此注解要替换成的别名名称
     *
     * @return 别名值
     */
    String value();
}
