package io.niufen.common.core.builder;

/**
 * 没有实际意义的一个类，只是为了 实现一下 IBuilder 这个接口
 *
 * @author haijun.zhang
 * @date 2020/5/23
 * @time 13:57
 */
public class ObjectIBuilder implements IBuilder<Object> {

    private static final long serialVersionUID = 8045742682718682901L;

    /**
     * 构建 Object
     * @return Object 对象
     */
    @Override
    public Object build() {
        return new Object();
    }
}
