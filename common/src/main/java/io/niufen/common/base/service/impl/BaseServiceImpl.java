package io.niufen.common.base.service.impl;

import io.niufen.common.base.mapper.BaseMapper;
import io.niufen.common.base.service.BaseService;
import io.niufen.common.util.CollectionUtils;
import io.niufen.common.util.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @author haijun.zhang
 * @date 2020/5/14
 * @time 22:45
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T, B> implements BaseService<T, B> {

    @Autowired
    private M baseMapper;

    @Override
    public B entityToBO(T entity) {
        Object o = new Object();
        BeanUtils.copyProperties(entity,o);
        return (B)o;
    }

    @Override
    public Collection<B> entityListToBOList(Collection<T> entityList) {
        List<B> boList = ListUtils.newLinkedList();
        if(CollectionUtils.isNotEmpty(entityList)){
            for (T t : entityList) {
                boList.add(entityToBO(t));
            }
        }
        return boList;
    }

    @Override
    public BaseMapper<T> getBaseMapper() {
        return this.baseMapper;
    }

}
