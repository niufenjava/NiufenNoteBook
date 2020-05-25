package io.niufen.springboot.common.page;

import io.niufen.springboot.common.response.R;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * 分页查询结果
 * @author niufen
 * @param <T> 数据类型
 */

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1547312481740277599L;
    /**
     * 返回数据总数
     */
    private Long total;

    /**
     * 返回数据
     */
    private Collection<T> list;

    /**
     * 创建分页返回数据
     * @param total 总数
     * @param list 数据
     */
    public PageResult(Long total, Collection<T> list) {
        this.total = total;
        this.list = list;
    }

    /**
     * 转换为Result结果对象
     *
     * @return {@link R}
     */
    public R toResult() {
        return R.ok(this);
    }
}

