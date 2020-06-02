package io.niufen.springboot.module.sys.mapper;

import io.niufen.common.constant.IntConstants;
import io.niufen.common.constant.SysConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.*;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
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
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Test
    public void insert() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
    }


    @Test
    public void insertSelective() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insertSelective(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
    }


    @Test
    public void batchInsert() {
        List<SysUserEntity> sysUserEntityList = ListUtil.newLinkedList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntityList.add(sysUserEntity);
        }
        Assert.assertTrue(ObjectTools.equals(IntConstants.TEN, sysUserMapper.batchInsert(sysUserEntityList)));
    }


    @Test
    public void insertBySql() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        String sql = "insert into t_sys_user (username) value ('"+sysUserEntity.getUsername()+"')";
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insertBySql(sql)));
    }

    @Test
    public void deleteById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteById(sysUserEntity.getId())));
    }

    @Test
    public void deleteLogicById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteLogicById(sysUserEntity.getId())));
    }

    @Test
    public void deleteByCriteria() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteByCriteria(sysUserEntity)));
    }

    @Test
    public void deleteByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        Map<String, Object> params = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
        params.put("password",sysUserEntity.getPassword());
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteByMap(params)));
    }

    @Test
    public void deleteBatchIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        Assert.assertTrue(ObjectTools.equals(IntConstants.TEN, sysUserMapper.deleteBatchIds(idList)));
    }


    @Test
    public void deleteBySql() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapper.insert(sysUserEntity);
        String sql = "delete from t_sys_user where id =" + sysUserEntity.getId();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.deleteBySql(sql)));
    }

    @Test
    public void updateById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapper.selectById(sysUserEntity.getId());
        querySysUserEntity.setPassword("password");
        querySysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        querySysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        querySysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.updateById(querySysUserEntity)));
        SysUserEntity updateSysUserEntity = sysUserMapper.selectById(sysUserEntity.getId());
        Assert.assertEquals("password",updateSysUserEntity.getPassword());
        Assert.assertNotNull(updateSysUserEntity.getUpdateUserId());
        Assert.assertNotNull(updateSysUserEntity.getUpdateUserName());
        Assert.assertNotNull(updateSysUserEntity.getUpdateTime());

    }

    @Test
    public void updateSelectiveById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setId(sysUserEntity.getId());
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.updateSelectiveById(updateSysUserEntity)));
        SysUserEntity selectSysUserEntity = sysUserMapper.selectById(sysUserEntity.getId());
        Assert.assertEquals("password",selectSysUserEntity.getPassword());
        Assert.assertNotNull(selectSysUserEntity.getUpdateUserId());
        Assert.assertNotNull(selectSysUserEntity.getUpdateUserName());
        Assert.assertNotNull(selectSysUserEntity.getUpdateTime());

    }

    @Test
    public void updateSelectiveByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setId(sysUserEntity.getId());
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());

        Map<String,Object> params = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
        params.put("phone",sysUserEntity.getPhone());

        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.updateSelectiveByMap(updateSysUserEntity,params)));
        SysUserEntity selectSysUserEntity = sysUserMapper.selectById(sysUserEntity.getId());
        Assert.assertEquals("password",selectSysUserEntity.getPassword());
        Assert.assertNotNull(selectSysUserEntity.getUpdateUserId());
        Assert.assertNotNull(selectSysUserEntity.getUpdateUserName());
        Assert.assertNotNull(selectSysUserEntity.getUpdateTime());

    }


    @Test
    public void updateSelectiveByCriteria() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setId(sysUserEntity.getId());
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());

        SysUserEntity criteria = new SysUserEntity();
        criteria.setUsername(sysUserEntity.getUsername());
        criteria.setPassword(sysUserEntity.getPassword());

        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.updateSelectiveByCriteria(updateSysUserEntity,criteria)));
        SysUserEntity selectSysUserEntity = sysUserMapper.selectById(sysUserEntity.getId());
        Assert.assertEquals("password",selectSysUserEntity.getPassword());
        Assert.assertNotNull(selectSysUserEntity.getUpdateUserId());
        Assert.assertNotNull(selectSysUserEntity.getUpdateUserName());
        Assert.assertNotNull(selectSysUserEntity.getUpdateTime());

    }

    @Test
    public void updateSelectiveBatchIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(ObjectTools.equals(IntConstants.TEN, sysUserMapper.updateSelectiveBatchIds(updateSysUserEntity,idList)));
    }

    @Test
    public void updateBySql() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapper.insert(sysUserEntity);
        String sql = "update t_sys_user set status = 0 where id =" + sysUserEntity.getId();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.updateBySql(sql)));
    }

    @Test
    public void selectById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(ObjectTools.equals(IntConstants.ONE, sysUserMapper.insert(sysUserEntity)));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity querySysUserEntity = sysUserMapper.selectById(sysUserEntity.getId());
        Assert.assertTrue(ObjectCompareUtil.equals(sysUserEntity,querySysUserEntity));
    }

    @Test
    public void selectBatchIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserMapper.insert(sysUserEntity);
            idList.add(sysUserEntity.getId());
        }

        List<SysUserEntity> sysUserEntities = sysUserMapper.selectBatchIds(idList);
        Assert.assertTrue(IntConstants.TEN == sysUserEntities.size());
    }

    @Test
    public void selectOneByMap() {

        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapper.insert(sysUserEntity);

        Map<String, Object> params = MapUtil.newMap();
        params.put("username",sysUserEntity.getUsername());
        SysUserEntity selectSysUserEntity = sysUserMapper.selectOneByMap(params);
        Assert.assertNotNull(selectSysUserEntity);
    }

    @Test
    public void selectOneByCriteria() {

        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        sysUserMapper.insert(sysUserEntity);

        SysUserEntity selectSysUserEntity = sysUserMapper.selectOneByCriteria(sysUserEntity);
        Assert.assertNotNull(selectSysUserEntity);
    }

    @Test
    public void selectByMap() {
        String password = UUIDUtil.generateUUID();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            sysUserMapper.insert(sysUserEntity);
        }
        Map<String, Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status",1);
        params.put("delFlag",0);
        params.put("startCreateTime", DateUtil.getDate(2018,10,10));
        params.put("endCreateTime", DateUtil.curTime());
        params.put("pageSortOrder","id desc");
        params.put("pageStartIndex",0);
        params.put("pageLimit",10);
        List<SysUserEntity> list = sysUserMapper.selectByMap(params);
        Assert.assertNotNull(list);
        Assert.assertTrue((list.size()== 10));
    }

    @Test
    public void selectByCriteria() {
        String password = UUIDUtil.generateUUID();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            sysUserMapper.insert(sysUserEntity);
        }
        SysUserEntity selectSysUserEntity = new SysUserEntity();
        selectSysUserEntity.setPassword(password);
        selectSysUserEntity.setStatus(1);
        selectSysUserEntity.setDelFlag(0);
        List<SysUserEntity> list = sysUserMapper.selectByCriteria(selectSysUserEntity);
        Assert.assertNotNull(list);
        Assert.assertTrue((list.size()== 10));
    }

    @Test
    public void selectCountByMap() {
        String password = UUIDUtil.generateUUID();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            sysUserMapper.insert(sysUserEntity);
        }
        Map<String, Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status",1);
        params.put("delFlag",0);
        params.put("startCreateTime", DateUtil.getDate(2018,10,10));
        params.put("endCreateTime", DateUtil.curTime());
        params.put("pageSortOrder","id desc");
        params.put("pageStartIndex",0);
        params.put("pageLimit",10);
        Long count = sysUserMapper.selectCountByMap(params);
        Assert.assertNotNull(count);
        Assert.assertTrue((count == 10));
    }

    @Test
    public void selectCountByCriteria() {
        String password = UUIDUtil.generateUUID();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            sysUserMapper.insert(sysUserEntity);
        }
        SysUserEntity selectSysUserEntity = new SysUserEntity();
        selectSysUserEntity.setPassword(password);
        Long count = sysUserMapper.selectCountByCriteria(selectSysUserEntity);
        Assert.assertNotNull(count);
        Assert.assertTrue((count == 10L));
    }

    @Test
    public void selectAll() {
        List<SysUserEntity> sysUserEntities = sysUserMapper.selectAll(null);
        Assert.assertNotNull(sysUserEntities);
        Assert.assertTrue((sysUserEntities.size() >= IntConstants.TEN));
    }
}
