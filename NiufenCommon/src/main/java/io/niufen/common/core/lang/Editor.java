package io.niufen.common.core.lang;

/**
 * 编辑器接口，常用于对接和中的元素作统一编辑
 * 此编辑器两个作用：
 * 1、如果返回值为 null，表示此值被抛弃
 * 2、对对象做修改
 *
 * @param <T> 被编辑对象的类型
 * @author niufen
 */
@FunctionalInterface
public interface Editor<T> {

    /**
     * 修改过滤后的结果
     *
     * @param t 被过滤的对象
     * @return 修改后的对象，如果被过滤，返回 null
     */
    T edit(T t);
}
