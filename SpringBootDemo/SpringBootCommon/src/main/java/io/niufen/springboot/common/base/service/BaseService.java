package io.niufen.springboot.common.base.service;

import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.CollUtil;
import io.niufen.common.util.MapUtil;
import io.niufen.common.util.ReflectUtil;
import io.niufen.springboot.common.base.mapper.BaseMapper;
import io.niufen.springboot.common.metadata.TableInfo;
import io.niufen.springboot.common.metadata.TableInfoHelper;
import io.niufen.springboot.common.page.PageResult;
import io.niufen.springboot.common.util.SqlHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 顶级 Service 接口，定义了通用的 Service 方法
 *
 * @author haijun.zhang@luckincoffee.com
 * @date 2019-06-02 22:55:33
 */
public interface BaseService<T,B> {

    /**
     * 默认批次提交数量
     */
    int DEFAULT_BATCH_SIZE = 100;

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     * @return true-插入成功；false-插入失败
     */
    default boolean save(T entity) {
        return SqlHelper.retBool(getBaseMapper().insertSelective(entity));
    }

    /**
     * 插入一条记录, 全字段插入，null 也插入
     *
     * @param entity 实体对象
     * @return TRUE-执行成功；FALSE-执行失败
     */
    default boolean saveWithNull(T entity) {
        if (ObjectTools.isNull(entity)) {
            return Boolean.FALSE;
        }
        return SqlHelper.retBool(getBaseMapper().insert(entity));
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @return TRUE-执行成功；FALSE-执行失败
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(List<T> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     * @return TRUE-执行成功；FALSE-执行失败
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(List<T> entityList, int batchSize) {
        if (CollUtil.isEmpty(entityList)) {
            return Boolean.FALSE;
        }
        int executeSize = 0;
        List<T> executeEntityList = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            executeEntityList.add(entityList.get(i));
            executeSize++;
            if(executeSize == batchSize || (i+1)==entityList.size()){
                executeSize = 0;
                executeInsertBatch(executeEntityList);
                executeEntityList = new ArrayList<>();
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 执行批量插入（批量）
     *
     * @param entityList 实体对象集合
     * @return TRUE-执行成功；FALSE-执行失败
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean executeInsertBatch(List<T> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            return Boolean.FALSE;
        }
        return SqlHelper.retBool(getBaseMapper().batchInsert(entityList));
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return TRUE-执行成功；FALSE-执行失败
     */
    default boolean removeById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteById(id));
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return TRUE-执行成功；FALSE-执行失败
     */
    default boolean removeLogicById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteLogicById(id));
    }

    /**
     * 根据 map 条件，删除记录
     *
     * @param criteria 实体对象
     * @return TRUE-执行成功；FALSE-执行失败
     */
    default boolean removeByCriteria(Object criteria) {
        return SqlHelper.retBool(getBaseMapper().deleteByCriteria(criteria));
    }

    /**
     * 根据 map 条件，删除记录
     *
     * @param criteria 实体对象
     * @return TRUE-执行成功；FALSE-执行失败
     */
    default boolean removeLogicByCriteria(Object criteria) {
        return SqlHelper.retBool(getBaseMapper().deleteLogicByCriteria(criteria));
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    default boolean removeByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        return SqlHelper.retBool(getBaseMapper().deleteByMap(columnMap));
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    default boolean removeLogicByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        return SqlHelper.retBool(getBaseMapper().deleteLogicByMap(columnMap));
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    default boolean removeByIds(Collection<? extends Serializable> idList) {
        if (ObjectTools.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteBatchIds(idList));
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    default boolean removeLogicByIds(Collection<? extends Serializable> idList) {
        if (ObjectTools.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteLogicBatchIds(idList));
    }

    /**
     * 根据 ID 选择修改，null 也更新
     *
     * @param entity 实体对象
     */
    default boolean updateById(T entity) {
        return SqlHelper.retBool(getBaseMapper().updateById(entity));
    }

    /**
     * 根据 ID 选择修改，如果为Null，则不更新
     *
     * @param entity 实体对象
     */
    default boolean updateSelectiveById(T entity) {
        return SqlHelper.retBool(getBaseMapper().updateSelectiveById(entity));
    }

    /**
     * 根据 ID 选择修改，如果为Null，则不更新
     *
     * @param entity 实体对象
     */
    default boolean updateSelectiveByMap(T entity, Map<String,Object> params) {
        if(CollUtil.isEmpty(params) || ObjectTools.isNull(entity)){
            return Boolean.FALSE;
        }
        return SqlHelper.retBool(getBaseMapper().updateSelectiveByMap(entity,params));
    }

    /**
     * 根据 ID 选择修改，如果为Null，则不更新
     *
     * @param entity 实体对象
     */
    default boolean updateSelectiveByCriteria(T entity, Object criteria) {
        if(ObjectTools.isNull(criteria) || ObjectTools.isNull(entity)){
            return Boolean.FALSE;
        }
        return SqlHelper.retBool(getBaseMapper().updateSelectiveByCriteria(entity,criteria));
    }

    /**
     * 根据ID 批量更新
     *
     * @param idList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean updateSelectiveBatchIds(T entity, Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(getBaseMapper().updateSelectiveBatchIds(entity, idList));
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */
    default boolean saveOrUpdate(T entity){
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.tableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getPrimaryKeyColumn();
            Object idVal = ReflectUtil.getFieldValue(entity, tableInfo.getPrimaryKeyColumn());
            return ObjectTools.isNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
        }
        return Boolean.FALSE;
    }


    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @return TRUE-成功；FALSE-失败
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        if(ObjectTools.isEmpty(entityList)){
            return Boolean.FALSE;
        }
        for (T t : entityList) {
            saveOrUpdate(t);
        }
        return Boolean.TRUE;
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    default T getById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    default B getBOById(Serializable id) {
        return entityToBO(getById(id));
    }

    /**
     * 根据 params，查询一条记录 <br/>
     *
     * @param
     */
    default T getOneByMap(Map<String, Object> params) {
        return getBaseMapper().selectOneByMap(params);
    }

    /**
     *
     * @param params 查询条件
     */
    default B getOneBOByMap(Map<String, Object> params) {
        return entityToBO(getOneByMap(params));
    }

    /**
     * 根据 params，查询一条记录 <br/>
     *
     * @param
     */
    default T getOneByCriteria(Object criteria) {
        return getBaseMapper().selectOneByCriteria(criteria);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     * @return entityList 实体对象列表
     */
    default List<T> listByIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().selectBatchIds(idList);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     * @return entityList 实体对象列表
     */
    default Map<Long,T> mapByIds(Collection<? extends Serializable> idList) {
        return entityListToMap(listByIds(idList));
    }

    default Map<Long,T> entityListToMap(List<T> entityList){
        Map<Long,T> maps = new HashMap<>();

        if(ObjectTools.isNotEmpty(entityList)){
            for (T t : entityList) {
                TableInfo tableInfo = TableInfoHelper.tableInfo(t.getClass());
                String primaryKeyColumn = tableInfo.getPrimaryKeyColumn();
                Long fieldValue = (Long) ReflectUtil.getFieldValue(t, primaryKeyColumn);
                maps.put(fieldValue,t);
            }
        }
        return maps;
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return entityList 实体对象列表
     */
    default List<T> listByMap(Map<String, Object> columnMap) {
        return getBaseMapper().selectByMap(columnMap);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return entityList 实体对象列表
     */
    default Map<Long,T> mapByMap(Map<String, Object> columnMap) {
        return entityListToMap(listByMap(columnMap));
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param criteria 表字段 map 对象
     * @return entityList 实体对象列表
     */
    default List<T> listByCriteria(Object criteria) {
        return getBaseMapper().selectByCriteria(criteria);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param criteria 表字段 map 对象
     * @return entityList 实体对象列表
     */
    default Map<Long,T> mapByCriteria(Object criteria) {
        return entityListToMap(listByCriteria(criteria));
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return entityList 实体对象列表
     */
    default Long countByMap(Map<String, Object> columnMap) {
        return getBaseMapper().selectCountByMap(columnMap);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param criteria 表字段 map 对象
     * @return entityList 实体对象列表
     */
    default Long countByCriteria(Object criteria) {
        return getBaseMapper().selectCountByCriteria(criteria);
    }

    /**
     * 翻页查询
     *
     * @param params 查询条件
     * @return 分页结果
     */
    default PageResult<T> pageByMap(Map<String, Object> params) {
        List<T> list = this.listByMap(params);
        Long count = this.countByMap(params);
        return new PageResult<T>(count,list);
    }

    /**
     * 翻页查询
     *
     * @param criteria 查询条件
     * @return 分页结果
     */
    default PageResult<B> pageByCriteria(Object criteria) {
        List<T> list = this.listByCriteria(criteria);
        Long count = this.countByCriteria(criteria);
        return new PageResult<B>(count,entityListToBOList(list));
    }

    /**
     * 查询所有
     */
    default List<T> list() {
        Map<String, Object> params = MapUtil.newMap();
        return listByMap(params);
    }

    default Long count() {
        Map<String, Object> params = MapUtil.newMap();
        return countByMap(params);
    }

    /**
     * 根据 sql，查询一条记录
     *
     * @param sql 根据sql查询一条记录
     */
    default Map<String, Object> getMap(String sql){
        return getBaseMapper().selectBySql(sql);
    }

    /**
     * entity 实体对象转 BO
     * @param entity
     * @return
     */
    B entityToBO(T entity);

    /**
     * entity 实体对象转 BO
     * @param entityList
     * @return boList
     */
    Collection<B> entityListToBOList(Collection<T> entityList);

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();
}
