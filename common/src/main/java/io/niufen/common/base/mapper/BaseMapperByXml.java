package io.niufen.common.base.mapper;

import io.niufen.common.constant.SqlConstants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * MyBatis 基础
 * @author niufen
 */
public interface BaseMapperByXml<T>{

    /**
     * 插入一条数据，ID由数据库自增，自动赋值到当前的 entity类
     *
     * @param entity 实体对象
     * @return int 插入条数
     */
    int insert(@Param(SqlConstants.KEY_ENTITY) T entity);

    /**
     * 根据 ID 删除一条数据
     *
     * @param id 主键
     * @return int 更新条数
     */
    int deleteById(Serializable id);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param params 删除条件
     * @return int 删除条数
     */
    int deleteByMap(@Param(SqlConstants.KEY_MAP_PARAMS) Map<String, Object> params);

    /**
     * 根据ID 进行批量删除
     * @param idList id列表
     * @return 删除数量
     */
    int deleteBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList);

    /**
     * 根据 ID 更新一条数据
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    int updateById(@Param("entity") T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity 实体对象
     * @param params 更新条件
     * @return int 更新条数
     */
    int update(@Param(SqlConstants.KEY_ENTITY) T entity, @Param(SqlConstants.KEY_MAP_PARAMS) Map<String, Object> params);

    /**
     * 根据 ID 获取一个实体
     *
     * @param id 主键
     * @return T 实体对象
     */
    T getById(Serializable id);


    /**
     * 根据 ID 获取一个实体
     *
     * @param params 查询参数
     * @return T 实体对象
     */
    T getOne(@Param(SqlConstants.KEY_MAP_PARAMS) Map<String, Object> params);

    /**
     * 查询（根据ID 批量查询）
     * @param idList id列表
     * @return List 查询对象列表
     */
    List<T> listBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList);

    /**
     * 根据 Map params 查询参数，进行列表查询
     *
     * @param params 查询参数
     * @return List 查询对象列表
     */
    List<T> listByParams(@Param(SqlConstants.KEY_MAP_PARAMS) Map<String, Object> params);


    /**
     * 根据 Map params 查询参数，进行分页count查询
     *
     * @param params 查询参数
     * @return count 查询数量
     */
    Long countByParams(@Param(SqlConstants.KEY_MAP_PARAMS) Map<String, Object> params);


}
