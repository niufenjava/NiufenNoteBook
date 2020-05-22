package io.niufen.springboot.redis;

import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import io.niufen.springboot.common.module.sys.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 14:43
 */
@Service
@Slf4j
public class SysUserCacheService {

    public static final String CACHE_NAME_KEY = "sysUser";

    @Autowired
    private SysUserMapper sysUserMapper;

    @CachePut(value = CACHE_NAME_KEY, key = "#entity.id")
    public SysUserEntity create(SysUserEntity entity){
        sysUserMapper.insert(entity);
        return entity;
    }

    @CacheEvict(value = CACHE_NAME_KEY, key = "#id")
    public void deleteById(Serializable id){
        sysUserMapper.deleteById(id);
    }

    @CachePut(value = CACHE_NAME_KEY, key = "#entity.id")
    public SysUserEntity updateById(SysUserEntity entity){
        sysUserMapper.updateById(entity);
        return getById(entity.getId());
    }

    @Cacheable(value = CACHE_NAME_KEY, key = "#id")
    public SysUserEntity getById(Serializable id){
        return sysUserMapper.selectById(id);
    }
}
