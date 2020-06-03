package io.niufen.springboot.redis;

import io.niufen.common.core.constant.SysConstants;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserCacheServiceTest {

    @Autowired
    private SysUserCacheService sysUserCacheService;

    @Test
    public void create() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserCacheService.create(sysUserEntity);
        String key = SysUserCacheService.CACHE_NAME_KEY + "::" + sysUserEntity.getId();
        assert RedisUtils.hasKey(key);

        SysUserEntity cacheEntity = sysUserCacheService.getById(sysUserEntity.getId());
        assert sysUserEntity.getId().equals(cacheEntity.getId());

        SysUserEntity redisSysUserCache = (SysUserEntity)RedisUtils.get(key);
        assert null != redisSysUserCache;
        assert redisSysUserCache.getId().equals(cacheEntity.getId());

    }

    @Test
    public void deleteById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserCacheService.create(sysUserEntity);

        String key = SysUserCacheService.CACHE_NAME_KEY + "::" + sysUserEntity.getId();
        assert RedisUtils.hasKey(key);

        sysUserCacheService.deleteById(sysUserEntity.getId());
        assert !RedisUtils.hasKey(key);
    }

    @Test
    public void updateById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserCacheService.create(sysUserEntity);

        String key = SysUserCacheService.CACHE_NAME_KEY + "::" + sysUserEntity.getId();
        assert RedisUtils.hasKey(key);

        sysUserEntity.setUpdateTime(new Date());
        sysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysUserCacheService.updateById(sysUserEntity);

        SysUserEntity cacheSysUser = (SysUserEntity) RedisUtils.get(key);
        assert cacheSysUser != null;
        assert SysConstants.SYSTEM_DEFAULT_USER_ID.equals(cacheSysUser.getUpdateUserId());
        assert SysConstants.SYSTEM_DEFAULT_USER_NAME.equals(cacheSysUser.getUpdateUserName());
    }

    @Test
    public void getById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserCacheService.create(sysUserEntity);
        String key = SysUserCacheService.CACHE_NAME_KEY + "::" + sysUserEntity.getId();

        SysUserEntity getByIdSysUserEntity = sysUserCacheService.getById(sysUserEntity.getId());

        SysUserEntity redisSysUserEntity = (SysUserEntity) RedisUtils.get(key);
    }
}
