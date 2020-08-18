package io.niufen.springboot.redis;

import io.niufen.common.core.constant.IntConstants;
import io.niufen.common.core.constant.LongConstants;
import io.niufen.common.core.map.MapUtil;
import io.niufen.common.core.thread.ThreadUtil;
import io.niufen.common.core.util.FakerUtil;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisUtilsTest {

    @Test
    public void set() {
        String key = "redis-utils-key-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value));
        Assert.assertEquals(value, RedisUtils.get(key));

        String newValue = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, newValue));
        Assert.assertEquals(newValue, RedisUtils.get(key));

        Assert.assertTrue(RedisUtils.set(key, null));
        Assert.assertNull(RedisUtils.get(key));

        Assert.assertFalse(RedisUtils.set(null, null));

        SysUserEntity entity = SysUserEntity.testNewEntity();
        key = "redis-utils-key-"+ entity.getUsername();
        Assert.assertTrue(RedisUtils.set(key, entity));
        Assert.assertEquals(entity, (SysUserEntity) RedisUtils.get(key));

        key = "redis-utils-key-"+ FakerUtil.uuid();
        Long longValue = 10000000000L;
        Assert.assertTrue(RedisUtils.set(key, longValue));
        // 如果小于INT的最大值，即使(Long) 强转也会异常。尽量都用String存储
        Assert.assertEquals(longValue, (Long) RedisUtils.get(key));

        key = "redis-utils-key-"+ FakerUtil.uuid();
        String strValue = "10000000000";
        Assert.assertTrue(RedisUtils.set(key, strValue));
        // 如果小于INT的最大值，即使(Long) 强转也会异常。尽量都用String存储
        Assert.assertEquals(strValue, (String) RedisUtils.get(key));
    }

    @Test
    public void setByExpire() {
        String key = "redis-utils-key-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value, 1));
        Assert.assertEquals(value, RedisUtils.get(key));
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(value, RedisUtils.get(key));

    }

    @Test
    public void get() {
        String key = "redis-utils-key-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value));
        Assert.assertEquals(value, RedisUtils.get(key));

        SysUserEntity entity = SysUserEntity.testNewEntity();
        Assert.assertTrue(RedisUtils.set(key, entity));
        Assert.assertEquals(entity, (SysUserEntity) RedisUtils.get(key));
    }

    @Test
    public void expire() {

        String key = "redis-utils-key-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value));
        Assert.assertEquals(value, (String) RedisUtils.get(key));
        Assert.assertTrue(RedisUtils.expire(key,1));
        Assert.assertEquals(value, (String) RedisUtils.get(key));
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(value, (String) RedisUtils.get(key));
    }

    @Test
    public void getExpire() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value, 2));
        Assert.assertTrue(RedisUtils.getExpire(key) <= 2);
    }

    @Test
    public void hasKey() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value, IntConstants.ONE));
        Assert.assertTrue(RedisUtils.hasKey(key));
        ThreadUtil.sleep(IntConstants.ONE);
        Assert.assertFalse(RedisUtils.hasKey(key));
    }

    @Test
    public void del() {
        // case 1 删除1个
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String value = FakerUtil.nameEN();
        Assert.assertTrue(RedisUtils.set(key, value));
        Assert.assertTrue(RedisUtils.hasKey(key));
        RedisUtils.del(key);
        Assert.assertFalse(RedisUtils.hasKey(key));

        // case 2 删除多个
        String key2 = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        RedisUtils.set(key2, FakerUtil.nameEN());
        Assert.assertTrue(RedisUtils.hasKey(key2));
        String key3 = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        RedisUtils.set(key3, FakerUtil.nameEN());
        Assert.assertTrue(RedisUtils.hasKey(key3));
        RedisUtils.del(key2,key3);
        Assert.assertFalse(RedisUtils.hasKey(key2));
        Assert.assertFalse(RedisUtils.hasKey(key3));
    }

    @Test
    public void increment() {
        String key1 = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        RedisUtils.set(key1, 1);
        Assert.assertTrue((RedisUtils.increment(key1, LongConstants.ONE) == 2));
        Assert.assertTrue(((Integer) RedisUtils.get(key1) == 2));

        Assert.assertTrue((RedisUtils.increment(key1, 10000000000L) == 10000000002L));
        Assert.assertTrue(((Long) RedisUtils.get(key1) == 10000000002L));
    }

    @Test
    public void decrement() {
        String key1 = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        RedisUtils.set(key1, 1);
        Assert.assertTrue((RedisUtils.decrement(key1, LongConstants.ONE) == 0));
        Assert.assertTrue(((Integer) RedisUtils.get(key1) == 0));

        RedisUtils.set(key1, 10000000000L);
        Assert.assertTrue((RedisUtils.decrement(key1, 10000000000L) == 0));
        Assert.assertTrue(((Integer)RedisUtils.get(key1) == 0));
    }

    @Test
    public void setHashMap() {
        String key1 = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("key1", FakerUtil.idNumberCN());
        map.put("key2", FakerUtil.idNumberCN());

        Assert.assertTrue(RedisUtils.setHashMap(key1,map));
        Assert.assertTrue(RedisUtils.hasKey(key1));
    }

    @Test
    public void getHashMap() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashMap(key).get(itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashMap(key).get(itemKey2));

    }

    @Test
    public void setHashMapByExpire() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);

        Assert.assertTrue(RedisUtils.setHashMap(key,map,LongConstants.ONE));
        Assert.assertEquals(itemValue1,RedisUtils.getHashMap(key).get(itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashMap(key).get(itemKey2));

        ThreadUtil.sleep(LongConstants.ONE);
        Assert.assertFalse(RedisUtils.hasKey(key));

    }

    @Test
    public void setHashItem() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashMap(key).get(itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashMap(key).get(itemKey2));

        String itemValue3 = FakerUtil.idNumberCN();
        Assert.assertTrue(RedisUtils.setHashItem(key,itemKey2,itemValue3));
        Assert.assertEquals(itemValue3,RedisUtils.getHashMap(key).get(itemKey2));

    }

    @Test
    public void setHashItemByExpire() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);

        Assert.assertTrue(RedisUtils.setHashMap(key,map,LongConstants.ONE));
        Assert.assertEquals(itemValue1,RedisUtils.getHashMap(key).get(itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashMap(key).get(itemKey2));

        String itemValue3 = FakerUtil.idNumberCN();
        Assert.assertTrue(RedisUtils.setHashItem(key,itemKey2,itemValue3,LongConstants.ONE));
        Assert.assertEquals(itemValue3,RedisUtils.getHashMap(key).get(itemKey2));

        ThreadUtil.sleep(LongConstants.ONE);
        Assert.assertFalse(RedisUtils.hasKey(key));
    }

    @Test
    public void getHashItem() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashItem(key,itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashItem(key,itemKey2));

    }

    @Test
    public void delHashItems() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemKey3 = "item3";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        String itemValue3 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);
        map.put(itemKey3,itemValue3);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashItem(key,itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashItem(key,itemKey2));
        Assert.assertEquals(itemValue3,RedisUtils.getHashItem(key,itemKey3));

        RedisUtils.delHashItems(key,itemKey2,itemKey3);
        Assert.assertEquals(itemValue1,RedisUtils.getHashItem(key,itemKey1));
        Assert.assertNull(RedisUtils.getHashItem(key,itemKey2));
        Assert.assertNull(RedisUtils.getHashItem(key,itemKey3));
    }

    @Test
    public void hasHashItemByKey() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemKey3 = "item3";
        String itemValue1 = FakerUtil.idNumberCN();
        String itemValue2 = FakerUtil.idNumberCN();
        String itemValue3 = FakerUtil.idNumberCN();
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);
        map.put(itemKey3,itemValue3);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashItem(key,itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashItem(key,itemKey2));
        Assert.assertEquals(itemValue3,RedisUtils.getHashItem(key,itemKey3));

        RedisUtils.delHashItems(key,itemKey2,itemKey3);
        Assert.assertTrue(RedisUtils.hasHashItemByKey(key,itemKey1));
        Assert.assertFalse(RedisUtils.hasHashItemByKey(key,itemKey2));
        Assert.assertFalse(RedisUtils.hasHashItemByKey(key,itemKey3));
    }

    @Test
    public void incrementHashItem() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemKey3 = "item3";
        Double itemValue1 = 1.00;
        Double itemValue2 = 2.00;
        Double itemValue3 = 3.00;
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);
        map.put(itemKey3,itemValue3);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashItem(key,itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashItem(key,itemKey2));
        Assert.assertEquals(itemValue3,RedisUtils.getHashItem(key,itemKey3));

        Assert.assertTrue((RedisUtils.incrementHashItem(key,itemKey1,1.00) == 2.00));
        Assert.assertTrue((RedisUtils.incrementHashItem(key,itemKey2,1.00) == 3.00));
        Assert.assertTrue((RedisUtils.incrementHashItem(key,itemKey3,1.00) == 4.00));
    }

    @Test
    public void decrementHashItem() {
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        Map<String, Object> map = MapUtil.newHashMap();
        String itemKey1 = "item1";
        String itemKey2 = "item2";
        String itemKey3 = "item3";
        Double itemValue1 = 1.00;
        Double itemValue2 = 2.00;
        Double itemValue3 = 3.00;
        map.put(itemKey1,itemValue1);
        map.put(itemKey2,itemValue2);
        map.put(itemKey3,itemValue3);

        Assert.assertTrue(RedisUtils.setHashMap(key,map));
        Assert.assertEquals(itemValue1,RedisUtils.getHashItem(key,itemKey1));
        Assert.assertEquals(itemValue2,RedisUtils.getHashItem(key,itemKey2));
        Assert.assertEquals(itemValue3,RedisUtils.getHashItem(key,itemKey3));

        Assert.assertTrue((RedisUtils.decrementHashItem(key,itemKey1,1.00) == 0.00));
        Assert.assertTrue((RedisUtils.decrementHashItem(key,itemKey2,1.00) == 1.00));
        Assert.assertTrue((RedisUtils.decrementHashItem(key,itemKey3,1.00) == 2.00));
    }

    @Test
    public void setSets(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String set1 = FakerUtil.nameCN();
        String set2 = FakerUtil.nameCN();
        String set3 = FakerUtil.nameCN();
        Long result = RedisUtils.setSets(key, set1, set2, set3, set1, set2, set3);
        assert LongConstants.THREE.equals(result);

        Set<Object> sets = RedisUtils.getSets(key);
        assert sets != null;
        assert sets.contains(set1);
        assert sets.contains(set2);
        assert sets.contains(set3);
    }

    @Test
    public void setSetsExpire(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String set1 = FakerUtil.nameCN();
        String set2 = FakerUtil.nameCN();
        String set3 = FakerUtil.nameCN();
        Long result = RedisUtils.setSets(key, LongConstants.TWO, set1, set2, set3, set1, set2, set3);
        assert LongConstants.THREE.equals(result);

        Set<Object> sets = RedisUtils.getSets(key);
        assert sets != null;
        assert sets.contains(set1);
        assert sets.contains(set2);
        assert sets.contains(set3);

        ThreadUtil.sleep(LongConstants.TWO);

        sets = RedisUtils.getSets(key);
        assert sets != null;
        assert !sets.contains(set1);
        assert !sets.contains(set2);
        assert !sets.contains(set3);
    }


    @Test
    public void getSets(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String set1 = FakerUtil.nameCN();
        String set2 = FakerUtil.nameCN();
        String set3 = FakerUtil.nameCN();
        RedisUtils.setSets(key, set1, set2, set3);

        Set<Object> sets = RedisUtils.getSets(key);
        assert sets != null;
        assert sets.contains(set1);
        assert sets.contains(set2);
        assert sets.contains(set3);
    }

    @Test
    public void hasValueInSet(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String set1 = FakerUtil.nameCN();
        String set2 = FakerUtil.nameCN();
        String set3 = FakerUtil.nameCN();
        RedisUtils.setSets(key, set1, set2, set3);

        assert RedisUtils.hasValueInSet(key,set1);
        assert RedisUtils.hasValueInSet(key,set2);
        assert RedisUtils.hasValueInSet(key,set3);
        assert !RedisUtils.hasValueInSet(key,1);
    }

    @Test
    public void getSetSize(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String set1 = FakerUtil.nameCN();
        String set2 = FakerUtil.nameCN();
        String set3 = FakerUtil.nameCN();
        RedisUtils.setSets(key, set1, set2, set3, set3);

        assert 3 ==RedisUtils.getSetSize(key);
    }

    @Test
    public void delValueInSet(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String set1 = FakerUtil.nameCN();
        String set2 = FakerUtil.nameCN();
        String set3 = FakerUtil.nameCN();
        RedisUtils.setSets(key, set1, set2, set3, set3);

        assert 3 == RedisUtils.getSetSize(key);
        assert 2 == RedisUtils.delValueInSet(key,set1,set2);
        assert 1 == RedisUtils.getSetSize(key);
    }

    @Test
    public void setList(){

        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        assert RedisUtils.setList(key,item1);
        assert RedisUtils.setList(key,item2);
        assert RedisUtils.setList(key,item3);
        assert RedisUtils.setList(key,item3);

        assert 4 == RedisUtils.getListSize(key);
    }

    @Test
    public void setListByExpire(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        assert RedisUtils.setList(key,item1);
        assert RedisUtils.setList(key,item2);
        assert RedisUtils.setList(key,item3);
        assert RedisUtils.setList(key,item3,LongConstants.ONE);

        assert 4 == RedisUtils.getListSize(key);
        ThreadUtil.sleep(LongConstants.ONE);
        assert 0 == RedisUtils.getListSize(key);
    }

    @Test
    public void setListByList(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key,list);
        assert 4 == RedisUtils.getListSize(key);
    }

    @Test
    public void setListByListExpire(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key,list,LongConstants.ONE);
        assert 4 == RedisUtils.getListSize(key);
        ThreadUtil.sleep(LongConstants.ONE);
        assert 0 == RedisUtils.getListSize(key);

    }

    @Test
    public void getList(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key, list);
        assert 1 == RedisUtils.getList(key,0,0).size();
        assert 2 == RedisUtils.getList(key,0,1).size();
        assert 3 == RedisUtils.getList(key,0,2).size();
        assert 4 == RedisUtils.getList(key,0,3).size();
        assert 1 == RedisUtils.getList(key,3,3).size();
        assert 2 == RedisUtils.getList(key,2,3).size();

        assert item1.equals(RedisUtils.getList(key,0,0).get(0));
        assert item2.equals(RedisUtils.getList(key,1,1).get(0));
        assert item3.equals(RedisUtils.getList(key,2,2).get(0));
        assert item3.equals(RedisUtils.getList(key,3,3).get(0));

    }

    @Test
    public void getListSize(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key, list);
        assert 4 == RedisUtils.getListSize(key);

    }
    @Test
    public void getListItemByIndex(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key, list);
        assert item1.equals(RedisUtils.getListItemByIndex(key,0));
        assert item2.equals(RedisUtils.getListItemByIndex(key,1));
        assert item3.equals(RedisUtils.getListItemByIndex(key,2));
        assert item3.equals(RedisUtils.getListItemByIndex(key,3));

        assert item1.equals(RedisUtils.getListItemByIndex(key,-4));
        assert item2.equals(RedisUtils.getListItemByIndex(key,-3));
        assert item3.equals(RedisUtils.getListItemByIndex(key,-2));
        assert item3.equals(RedisUtils.getListItemByIndex(key,-1));

    }

    @Test
    public void updateListItemValueByIndex(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key, list);
        assert item1.equals(RedisUtils.getListItemByIndex(key,0));
        assert item2.equals(RedisUtils.getListItemByIndex(key,1));
        assert item3.equals(RedisUtils.getListItemByIndex(key,2));
        assert item3.equals(RedisUtils.getListItemByIndex(key,3));

        assert RedisUtils.updateListItemValueByIndex(key,0,"");
        assert RedisUtils.updateListItemValueByIndex(key,1,"");
        assert RedisUtils.updateListItemValueByIndex(key,2,"");
        assert RedisUtils.updateListItemValueByIndex(key,3,"");

        assert "".equals(RedisUtils.getListItemByIndex(key,0));
        assert "".equals(RedisUtils.getListItemByIndex(key,1));
        assert "".equals(RedisUtils.getListItemByIndex(key,2));
        assert "".equals(RedisUtils.getListItemByIndex(key,3));

    }

    @Test
    public void delListCount(){
        String key = "redisUtilsTest-"+ FakerUtil.idNumberCN();
        String item1 = FakerUtil.nameCN();
        String item2 = FakerUtil.nameCN();
        String item3 = FakerUtil.nameCN();
        List<Object> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item3);
        assert RedisUtils.setList(key, list);

        assert 2 == RedisUtils.delListCount(key,2,item3);
        assert 2 == RedisUtils.getListSize(key);

    }
}
