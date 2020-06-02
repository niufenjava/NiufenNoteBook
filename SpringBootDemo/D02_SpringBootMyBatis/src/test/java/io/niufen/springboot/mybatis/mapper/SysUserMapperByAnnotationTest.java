package io.niufen.springboot.mybatis.mapper;

import io.niufen.common.constant.IntConstants;
import io.niufen.common.constant.SysConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.ObjectCompareUtil;
import io.niufen.common.util.MapUtil;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.module.sys.mapper.SysUserMapperByAnnotation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserMapperByAnnotationTest {

    @Autowired
    private SysUserMapperByAnnotation sysUserMapperByAnnotation;

    @Test
    public void insert() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
    }

    @Test
    public void deleteById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.deleteById(sysUserEntity.getId())));
    }

    @Test
    public void updateById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapperByAnnotation.getById(sysUserEntity.getId());
        querySysUserEntity.setPassword("password");
        querySysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        querySysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        querySysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.updateById(querySysUserEntity)));
        SysUserEntity updateSysUserEntity = sysUserMapperByAnnotation.getById(sysUserEntity.getId());
        Assert.assertEquals("password",updateSysUserEntity.getPassword());
        Assert.assertNotNull(updateSysUserEntity.getUpdateUserId());
        Assert.assertNotNull(updateSysUserEntity.getUpdateUserName());
        Assert.assertNotNull(updateSysUserEntity.getUpdateTime());

    }

    @Test
    public void getById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapperByAnnotation.getById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void selectById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapperByAnnotation.selectById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void getByUsername() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapperByAnnotation.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapperByAnnotation.getByUsername(sysUserEntity.getUsername());
        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void listAll() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapperByAnnotation.insert(sysUserEntity);
        List<SysUserEntity> list = sysUserMapperByAnnotation.listAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void count() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapperByAnnotation.insert(sysUserEntity);
        Long count = sysUserMapperByAnnotation.count();
        Assert.assertNotNull(count);
        Assert.assertTrue(count>0);
    }

    @Test
    public void listByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapperByAnnotation.insert(sysUserEntity);
        Map<String, Object> params = MapUtil.newMap();
        params.put("status",1);
        params.put("delFlag",0);
        params.put("orderCols","id");
        params.put("limit","0,10");
        List<SysUserEntity> list = sysUserMapperByAnnotation.listByMap(params);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);
    }
}
