package io.niufen.common.core.builder;

import java.io.Serializable;

/**
 * 建造者模式定义接口
 *
 * @author niufen
 */
public interface IBuilder<T> extends Serializable {

    /**
     * 构建
     * @return 被构建的对象
     */
    T build();
}
