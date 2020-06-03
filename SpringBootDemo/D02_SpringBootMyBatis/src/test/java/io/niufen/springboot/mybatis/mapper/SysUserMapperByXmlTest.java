package io.niufen.springboot.mybatis.mapper;

import io.niufen.common.core.collection.ListUtil;
import io.niufen.common.core.constant.IntConstants;
import io.niufen.common.core.constant.SysConstants;
import io.niufen.common.core.enums.StatusEnum;
import io.niufen.common.core.enums.YesOrNoEnum;
import io.niufen.common.core.map.MapUtil;
import io.niufen.common.core.util.DateUtils;
import io.niufen.common.core.util.ObjectCompareUtil;
import io.niufen.common.core.util.ObjectUtil;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.module.sys.mapper.SysUserMapperByXml;
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
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
    }

    @Test
    public void deleteById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.deleteById(sysUserEntity.getId())));
    }

    @Test
    public void deleteByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertNotNull(sysUserMapper.getById(sysUserEntity.getId()));

//        Map<String,Object> deleteParams = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
//        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.deleteByMap(deleteParams)));
//        Assert.assertNull(sysUserMapper.getById(sysUserEntity.getId()));
    }

    @Test
    public void deleteBatchIds() {
        List<Long> idList = ListUtil.list(true);
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        Assert.assertTrue(ObjectUtil.equal(IntConstants.TEN, sysUserMapper.deleteBatchIds(idList)));
    }

    @Test
    public void updateById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        SysUserEntity querySysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        querySysUserEntity.setPassword("password");
        querySysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        querySysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        querySysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.updateById(querySysUserEntity)));
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
        Map<String, Object> params = MapUtil.newHashMap();
        params.put("status",1);
        params.put("delFlag",0);
        params.put("password","-----");
        params.put("startCreateTime", DateUtils.getDate(2018,10,10));
        params.put("endCreateTime", DateUtils.curTime());
        Assert.assertTrue(10 <= sysUserMapper.update(updateSysUserEntity,params));
    }

    @Test
    public void getById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        SysUserEntity querySysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void getOne() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        SysUserEntity querySysUserEntity = sysUserMapper.getById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void getByUsername() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
//        Map params = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
//        SysUserEntity querySysUserEntity = sysUserMapper.getOne(params);
//        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void listBatchIds() {
        List<Long> idList = ListUtil.list(true);
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(ObjectUtil.equal(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        Assert.assertTrue(ObjectUtil.equal(IntConstants.TEN, sysUserMapper.listBatchIds(idList).size()));
    }

    @Test
    public void listByParams() {
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserMapper.insert(sysUserEntity);
        }
        Map<String, Object> params = MapUtil.newHashMap();
        params.put("status",1);
        params.put("delFlag",0);
        params.put("startCreateTime", DateUtils.getDate(2018,10,10));
        params.put("endCreateTime", DateUtils.curTime());
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
        Map<String, Object> params = MapUtil.newHashMap();
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
