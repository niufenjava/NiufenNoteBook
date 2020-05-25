package io.niufen.springboot.common.base.mapper;

import io.niufen.springboot.common.base.mapper.provider.DeleteSqlProvider;
import io.niufen.springboot.common.base.mapper.provider.InsertSqlProvider;
import io.niufen.springboot.common.base.mapper.provider.SelectSqlProvider;
import io.niufen.springboot.common.base.mapper.provider.UpdateSqlProvider;
import io.niufen.springboot.common.constant.SqlConstants;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * 目前是基于 mybatis 注解，实现；
 * 后续需要通过注解、反射、代理方式实现
 * @author niufen
 * @date 2020-05-14
 */
@Mapper
public interface BaseMapper <T> extends IMapper<T>{


    /**
     * 插入一条数据，ID由数据库自增，自动赋值到当前的 entity类
     *
     * @param entity 实体对象
     */
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @InsertProvider(type = InsertSqlProvider.class,method = "insert")
    int insert(T entity);


    /**
     * 插入一条数据，ID由数据库自增，自动赋值到当前的 entity类
     *
     * @param entity 实体对象
     */
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @InsertProvider(type = InsertSqlProvider.class,method = "insertSelective")
    int insertSelective(T entity);

    /**
     * 批量插入实体
     *
     * @param entities  实体列表
     * @return          影响条数
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "batchInsert")
    int batchInsert(@Param("entities") List<T> entities);


    /**
     * 根据 传入的SQL语句进行 插入
     *
     * @param sqlStr sql语句
     * @return 插入条数
     */
    @Insert("${sqlStr}")
    int insertBySql(@Param(value = "sqlStr") String sqlStr);

    /**
     * 根据 ID 删除一条数据
     *
     * @param id 主键
     * @return int 更新条数
     */
    @DeleteProvider(type = DeleteSqlProvider.class,method = "deleteById")
    int deleteById(Serializable id);

    /**
     * 伪删除，即将 delFlag 字段更新为1
     *
     * @param id id
     * @return  影响条数
     */
    @UpdateProvider(type = DeleteSqlProvider.class, method = "deleteLogicById")
    int deleteLogicById(Serializable id);

    /**
     * 根据 map 条件，删除记录
     *
     * @param criteria 条件对象
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteByCriteria")
    int deleteByCriteria(Object criteria);

    /**
     * 根据 map 条件，删除记录
     *
     * @param criteria 条件对象
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteLogicByCriteria")
    int deleteLogicByCriteria(Object criteria);

    /**
     * 根据 map 条件，删除记录
     *
     * @param params map 查询条件
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteByMap")
    int deleteByMap(Map<String, Object> params);

    /**
     * 根据 map 条件，删除记录
     *
     * @param params map 查询条件
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteLogicByMap")
    int deleteLogicByMap(Map<String, Object> params);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteBatchIds")
    int deleteBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteLogicBatchIds")
    int deleteLogicBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList);


    /**
     * 根据 传入的SQL语句进行 删除
     *
     * @param sqlStr sql语句
     * @return 插入条数
     */
    @Delete("${sqlStr}")
    int deleteBySql(@Param(value = "sqlStr") String sqlStr);


    /**
     * 根据 ID 更新一条数据
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateById")
    int updateById(T entity);

    /**
     * 根据 ID 更新一条数据，如果字段为 NULL，不更新
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSelectiveById")
    int updateSelectiveById(T entity);

    /**
     * 根据 ID 更新一条数据，如果字段为 NULL，不更新
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSelectiveByMap")
    int updateSelectiveByMap(@Param(SqlConstants.KEY_ENTITY) T entity,@Param(SqlConstants.KEY_MAP_PARAMS) Map<String,Object> params);

    /**
     * 根据 ID 更新一条数据，如果字段为 NULL，不更新
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSelectiveByCriteria")
    int updateSelectiveByCriteria(@Param(SqlConstants.KEY_ENTITY) T entity,@Param(SqlConstants.KEY_CRITERIA) Object criteria);

    /**
     * 根据 ID 更新一条数据，如果字段为 NULL，不更新
     *
     * @param entity 实体对象，必须存在ID
     * @return int 更新条数
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSelectiveBatchIds")
    int updateSelectiveBatchIds(@Param(SqlConstants.KEY_ENTITY) T entity,@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList);

    /**
     * 根据 传入的SQL语句进行 删除
     *
     * @param sqlStr sql语句
     * @return 插入条数
     */
    @Update("${sqlStr}")
    int updateBySql(@Param(value = "sqlStr") String sqlStr);

    /**
     * 根据 ID 获取一个实体
     *
     * @param id 主键
     * @return T 实体对象
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectById")
    T selectById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     * @param idList id列表
     * @return List 查询对象列表
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectBatchIds")
    List<T> selectBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList);

    /**
     * 根据 Map params 查询参数，进行分页查询，返回List
     *
     * @param params 查询参数
     * @return List 查询对象列表
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectByMap")
    List<T> selectByMap(Map<String, Object> params);

    /**
     * 根据 map 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param criteria 实体对象封装操作类（可以为 null）
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectByCriteria")
    List<T> selectByCriteria(Object criteria);

    /**
     * 根据 Map 条件查询一条记录
     *
     * @param params 查询参数
     * @return T 实体对象
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectOneByMap")
    T selectOneByMap(Map<String, Object> params);
    /**
     * 根据 Map 条件查询一条记录
     *
     * @param criteria 查询参数
     * @return T 实体对象
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectOneByCriteria")
    T selectOneByCriteria(Object criteria);

    /**
     * 根据 Map params 查询参数，进行分页count查询
     *
     * @param params 查询参数
     * @return count 查询数量
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectCountByMap")
    Long selectCountByMap(Map<String, Object> params);

    /**
     * 根据 Map params 查询参数，进行分页count查询
     *
     * @param criteria 查询参数
     * @return count 查询数量
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectCountByCriteria")
    Long selectCountByCriteria(Object criteria);

    /**
     * 查询所有实体
     *
     * @param orderBy  排序
     * @return   实体list
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectAll")
    List<T> selectAll(String orderBy);


    /**
     * 根据 传入的SQL语句进行 删除
     *
     * @param sqlStr sql语句
     * @return 插入条数
     */
    @Update("${sqlStr}")
    Map<String,Object> selectBySql(@Param(value = "sqlStr") String sqlStr);
}
