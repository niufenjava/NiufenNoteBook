package io.niufen.common.base.dao;

import java.util.List;
import java.util.Map;

/**
 * 顶级Dao接口，定义了通用的数据库 CRUD 方法
 * 2019-11-08 22:55:33
 * @author niufen
 */
public interface BaseDao<T> {

    /**
     * 插入一条数据，ID由数据库自增，自动赋值到当前的 entity类
     *
     * @param entity 实体对象
     */
    void insert(T entity);


    /**
     * 根据 ID 删除一条数据
     *
     * @param id 主键
     * @return int 更新条数
     */
    int deleteById(Long id);


    /**
     * 根据 ID 更新一条数据
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    int updateById(T entity);


    /**
     * 根据 ID 获取一个实体
     *
     * @param id 主键
     * @return T 实体对象
     */
    T getById(Long id);


    /**
     * 根据 ID 获取一个实体
     *
     * @param params 查询参数
     * @return T 实体对象
     */
    T getOne(Map<String, Object> params);


    /**
     * 根据 Map params 查询参数，进行列表查询
     *
     * @param params 查询参数
     * @return List 查询对象列表
     */
    List<T> listByParams(Map<String, Object> params);


    /**
     * 根据 Map params 查询参数，进行分页查询，返回List
     *
     * @param params 查询参数
     * @return List 查询对象列表
     */
    List<T> pageByParams(Map<String, Object> params);


    /**
     * 根据 Map params 查询参数，进行分页count查询
     *
     * @param params 查询参数
     * @return count 查询数量
     */
    Long countByParams(Map<String, Object> params);


}
