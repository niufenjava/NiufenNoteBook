package io.niufen.springboot.mybatis.mapper;

import io.niufen.common.constant.IntConstants;
import io.niufen.common.constant.SysConstants;
import io.niufen.common.enums.StatusEnum;
import io.niufen.common.enums.YesOrNoEnum;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.*;
import io.niufen.springboot.common.module.sys.mapper.SysUserMapperByXml;
import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
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
public class SysUserMapperByXmlTest {

    @Autowired
    private SysUserMapperByXml sysUserMapper;

    @Test
    public void insert() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
    }

    @Test
    public void deleteById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteById(sysUserEntity.getId())));
    }

    @Test
    public void deleteByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertNotNull(sysUserMapper.getById(sysUserEntity.getId()));

        Map<String,Object> deleteParams = MapUtils.getOneItemMap("username",sysUserEntity.getUsername());
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteByMap(deleteParams)));
        Assert.assertNull(sysUserMapper.getById(sysUserEntity.getId()));
    }

    @Test
    public void deleteBatchIds() {
        List<Long> idList = ListUtils.newLongList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.TEN, sysUserMapper.deleteBatchIds(idList)));
    }

    @Test
    public void updateById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        querySysUserEntity.setPassword("password");
        querySysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        querySysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        querySysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.updateById(querySysUserEntity)));
        SysUserEntity updateSysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        Assert.assertEquals("password",updateSysUserEntity.getPassword());
        Assert.assertNotNull(updateSysUserEntity.getUpdateUserId());
        Assert.assertNotNull(updateSysUserEntity.getUpdateUserName());
        Assert.assertNotNull(updateSysUserEntity.getUpdateTime());
    }

    @Test
    public void update(){
        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setDelFlag(YesOrNoEnum.YES.getIndex());
        updateSysUserEntity.setStatus(StatusEnum.INVALID.getIndex());
        updateSysUserEntity.setPassword("********");

        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword("-----");
            sysUserMapper.insert(sysUserEntity);
        }
        Map<String, Object> params = MapUtils.newMap();
        params.put("status",1);
        params.put("delFlag",0);
        params.put("password","-----");
        params.put("startCreateTime",DateUtils.getDate(2018,10,10));
        params.put("endCreateTime",DateUtils.curTime());
        Assert.assertTrue(10 <= sysUserMapper.update(updateSysUserEntity,params));
    }

    @Test
    public void getById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtils.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void getOne() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtils.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void getByUsername() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Map params = MapUtils.getOneItemMap("username",sysUserEntity.getUsername());
        SysUserEntity querySysUserEntity = sysUserMapper.getOne(params);
        Assert.assertTrue(ObjectCompareUtils.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void listBatchIds() {
        List<Long> idList = ListUtils.newLongList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.TEN, sysUserMapper.listBatchIds(idList).size()));
    }

    @Test
    public void listByParams() {
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserMapper.insert(sysUserEntity);
        }
        Map<String, Object> params = MapUtils.newMap();
        params.put("status",1);
        params.put("delFlag",0);
        params.put("startCreateTime",DateUtils.getDate(2018,10,10));
        params.put("endCreateTime",DateUtils.curTime());
        params.put("pageSortOrder","id desc");
        params.put("pageStartIndex",0);
        params.put("pageLimit",10);
        List<SysUserEntity> list = sysUserMapper.listByParams(params);
        Assert.assertNotNull(list);
        Assert.assertTrue((list.size()== 10));
    }

    @Test
    public void countByParams() {
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserMapper.insert(sysUserEntity);
        }
        Map<String, Object> params = MapUtils.newMap();
        params.put("status",1);
        params.put("delFlag",0);
        params.put("pageSortOrder","id desc");
        params.put("pageStartIndex",0);
        params.put("pageLimit",10);
        Long count = sysUserMapper.countByParams(params);
        Assert.assertNotNull(count);
        Assert.assertTrue((count >=10));
    }

}
