package io.niufen.springboot.module.sys.service;

import io.niufen.common.constant.SysConstants;
import io.niufen.common.enums.StatusEnum;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.ListUtil;
import io.niufen.common.util.MapUtil;
import io.niufen.common.util.UUIDUtil;
import io.niufen.springboot.common.page.PageResult;
import io.niufen.springboot.module.sys.bo.SysUserBO;
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
public class SysUserServiceTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void save() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
    }

    @Test
    public void saveWithNull() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.saveWithNull(sysUserEntity));
    }

    @Test
    public void saveBatch() {
        String password = UUIDUtil.generateUUID();
        List<SysUserEntity> sysUserEntityList = ListUtil.newArrayList();
        for (int i = 0; i < 1001; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            sysUserEntityList.add(sysUserEntity);
        }
        Assert.assertTrue(sysUserService.saveBatch(sysUserEntityList));
    }

    @Test
    public void removeById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Assert.assertTrue(sysUserService.removeById(sysUserEntity.getId()));
    }

    @Test
    public void removeLogicById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Assert.assertTrue(sysUserService.removeLogicById(sysUserEntity.getId()));
    }

    @Test
    public void removeByCriteria() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Assert.assertTrue(sysUserService.removeByCriteria(sysUserEntity));
    }

    @Test
    public void removeLogicByCriteria() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Assert.assertTrue(sysUserService.removeLogicByCriteria(sysUserEntity));
    }

    @Test
    public void removeByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Map<String,Object> params = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
        Assert.assertTrue(sysUserService.removeByMap(params));
    }

    @Test
    public void removeLogicByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Map<String,Object> params = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
        Assert.assertTrue(sysUserService.removeLogicByMap(params));
    }

    @Test
    public void removeByIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 100; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserService.save(sysUserEntity);
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue(sysUserService.removeByIds(idList));
    }

    @Test
    public void removeLogicByIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 100; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserService.save(sysUserEntity);
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue(sysUserService.removeLogicByIds(idList));
    }

    @Test
    public void updateById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        sysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(sysUserService.updateById(sysUserEntity));
    }

    @Test
    public void updateSelectiveById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));

        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setId(sysUserEntity.getId());
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(sysUserService.updateSelectiveById(updateSysUserEntity));
    }

    @Test
    public void updateSelectiveByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));
        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setId(sysUserEntity.getId());
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());

        Map<String,Object> params = MapUtil.getOneItemMap("username",sysUserEntity.getUsername());
        params.put("phone",sysUserEntity.getPhone());
        Assert.assertTrue(sysUserService.updateSelectiveByMap(updateSysUserEntity,params));
    }

    @Test
    public void updateSelectiveByCriteria() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.save(sysUserEntity));
        Assert.assertTrue(ObjectTools.isNotNullAndZero(sysUserEntity.getId()));

        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setId(sysUserEntity.getId());
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());

        SysUserEntity criteria = new SysUserEntity();
        criteria.setUsername(sysUserEntity.getUsername());
        criteria.setPhone(sysUserEntity.getPhone());

        Assert.assertTrue(sysUserService.updateSelectiveByCriteria(updateSysUserEntity,criteria));
    }

    @Test
    public void updateSelectiveBatchIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity updateSysUserEntity = new SysUserEntity();
        updateSysUserEntity.setPassword("password");
        updateSysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        updateSysUserEntity.setUpdateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        updateSysUserEntity.setUpdateTime(new Date());
        Assert.assertTrue(sysUserService.updateSelectiveBatchIds(updateSysUserEntity,idList));
    }

    @Test
    public void saveOrUpdate() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.saveOrUpdate(sysUserEntity));
        Assert.assertTrue(sysUserService.saveOrUpdate(sysUserEntity));

    }

    @Test
    public void getById() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.saveOrUpdate(sysUserEntity));
        SysUserEntity querySysUserEntity = sysUserService.getById(sysUserEntity.getId());
        Assert.assertNotNull(querySysUserEntity);
    }

    @Test
    public void getOneByMap() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.saveOrUpdate(sysUserEntity));
        Map<String,Object> params = MapUtil.newMap();
        params.put("username",sysUserEntity.getUsername());
        SysUserEntity querySysUserEntity = sysUserService.getOneByMap(params);
        Assert.assertNotNull(querySysUserEntity);
    }

    @Test
    public void getOneByCriteria() {
        SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
        Assert.assertTrue(sysUserService.saveOrUpdate(sysUserEntity));
        SysUserEntity criteria = new SysUserEntity();
        criteria.setUsername(sysUserEntity.getUsername());
        SysUserEntity querySysUserEntity = sysUserService.getOneByCriteria(criteria);
        Assert.assertNotNull(querySysUserEntity);
    }

    @Test
    public void listByIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        List<SysUserEntity> sysUserEntities = sysUserService.listByIds(idList);
        Assert.assertTrue((sysUserEntities.size() == 10));
    }

    @Test
    public void mapByIds() {
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));
        Map<Long, SysUserEntity> longSysUserEntityMap = sysUserService.mapByIds(idList);
        Assert.assertTrue((longSysUserEntityMap.size() == 10));
    }

    @Test
    public void listByMap() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        Map<String,Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status", StatusEnum.VALID.getIndex());

        List<SysUserEntity> sysUserEntities = sysUserService.listByMap(params);
        Assert.assertTrue((sysUserEntities.size() == 10));
    }

    @Test
    public void mapByMap() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        Map<String,Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status", StatusEnum.VALID.getIndex());

        Map<Long, SysUserEntity> longSysUserEntityMap = sysUserService.mapByMap(params);
        Assert.assertTrue((longSysUserEntityMap.size() == 10));
    }

    @Test
    public void listByCriteria() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity criteria = new SysUserEntity();
        criteria.setPassword(password);
        criteria.setStatus(StatusEnum.VALID.getIndex());

        List<SysUserEntity> sysUserEntities = sysUserService.listByCriteria(criteria);
        Assert.assertTrue((sysUserEntities.size() == 10));
    }

    @Test
    public void mapByCriteria() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity criteria = new SysUserEntity();
        criteria.setPassword(password);
        criteria.setStatus(StatusEnum.VALID.getIndex());

        Map<Long, SysUserEntity> longSysUserEntityMap = sysUserService.mapByCriteria(criteria);
        Assert.assertTrue((longSysUserEntityMap.size() == 10));
    }


    @Test
    public void countByMap() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        Map<String,Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status", StatusEnum.VALID.getIndex());

        Long aLong = sysUserService.countByMap(params);
        Assert.assertTrue((aLong == 10));
    }

    @Test
    public void countByCriteria() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity criteria = new SysUserEntity();
        criteria.setPassword(password);
        criteria.setStatus(StatusEnum.VALID.getIndex());

        Long aLong = sysUserService.countByCriteria(criteria);
        Assert.assertTrue((aLong == 10));
    }



    @Test
    public void pageByMap() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        Map<String,Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status", StatusEnum.VALID.getIndex());

        PageResult<SysUserEntity> sysUserEntityPageResult = sysUserService.pageByMap(params);
        Assert.assertTrue((sysUserEntityPageResult.getTotal() == 10));
        Assert.assertTrue((sysUserEntityPageResult.getList().size() == 10));
    }

    @Test
    public void pageByCriteria() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity criteria = new SysUserEntity();
        criteria.setPassword(password);
        criteria.setStatus(StatusEnum.VALID.getIndex());

        PageResult<SysUserBO> sysUserEntityPageResult = sysUserService.pageByCriteria(criteria);
        Assert.assertTrue((sysUserEntityPageResult.getTotal() == 10));
        Assert.assertTrue((sysUserEntityPageResult.getList().size() == 10));
    }


    @Test
    public void list() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        SysUserEntity criteria = new SysUserEntity();
        criteria.setPassword(password);
        criteria.setStatus(StatusEnum.VALID.getIndex());

        List<SysUserEntity> sysUserEntities = sysUserService.list();
        System.out.println(sysUserEntities.size());
        Assert.assertTrue((sysUserEntities.size() >= 10));
    }


    @Test
    public void count() {
        String password = UUIDUtil.generateUUID();
        List<Long> idList = ListUtil.newList();
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = SysUserEntity.testNewEntity();
            sysUserEntity.setPassword(password);
            Assert.assertTrue(sysUserService.save(sysUserEntity));
            idList.add(sysUserEntity.getId());
        }
        Assert.assertTrue((10 == idList.size()));

        Map<String,Object> params = MapUtil.newMap();
        params.put("password",password);
        params.put("status", StatusEnum.VALID.getIndex());

        Long aLong = sysUserService.count();
        System.out.println(aLong);
        Assert.assertTrue((aLong >= 10));
    }
}
