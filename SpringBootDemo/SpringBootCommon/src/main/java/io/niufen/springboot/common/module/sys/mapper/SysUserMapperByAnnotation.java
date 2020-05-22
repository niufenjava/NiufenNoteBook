package io.niufen.springboot.common.module.sys.mapper;

import io.niufen.common.constant.SqlConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 13:22
 */
@Component
@Mapper
public interface SysUserMapperByAnnotation {

    /**
     * 表名
     */
    String TABLE_NAME = "t_sys_user";

    String RESULT_ID = "sysUserEntity";

    /**
     * Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")  该注解用于返回主键
     * @param sysUserEntity entity
     * @return int 插入数量
     */
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @Insert("insert into t_sys_user(username,password,sex,phone,status,del_flag,create_user_id,create_user_name,create_time) values(#{username},#{password},#{sex},#{phone},#{status},#{delFlag},#{createUserId},#{createUserName},#{createTime})")
    int insert(SysUserEntity sysUserEntity);

    /**
     * 根据条件删除
     * @param id 主键
     * @return 删除数量
     */
    @Delete("delete from t_sys_user where id=#{id}")
    int deleteById(Long id);

    /**
     * 根据ID更新数据
     * @param sysUserEntity 实体对象
     * @return 更新数量
     */
    @Insert("update t_sys_user set username = #{username},password = #{password},sex = #{sex},phone = #{phone},status = #{status},del_flag = #{delFlag},update_user_id = #{updateUserId},update_user_name = #{updateUserName},update_time = #{updateTime} where id = #{id}")
    int updateById(SysUserEntity sysUserEntity);

    /**
     * 根据ID获取实体对象
     * @param id ID
     * @return 返回实体对象
     */
    @Select("select * from t_sys_user where id=#{id}")
    @Results(id = RESULT_ID,value= {
            @Result(property = "id", column = "id", javaType = Long.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class),
            @Result(property = "sex", column = "sex", javaType = Integer.class),
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "delFlag", column = "del_flag", javaType = Integer.class),
            @Result(property = "createUserId", column = "create_user_id", javaType = Long.class),
            @Result(property = "createUserName", column = "create_user_name", javaType = String.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "updateUserId", column = "update_user_id", javaType = Long.class),
            @Result(property = "updateUserName", column = "update_user_name", javaType = String.class),
            @Result(property = "updateTime", column = "update_time", javaType = Date.class)
    })
    SysUserEntity getById(Long id);

    /**
     * 通过 SelectProvider 的 进行id查询
     * @param id 主键
     * @return entity
     */
    @SelectProvider(type = SysUserSelectProvider.class, method = "selectById")
    @ResultMap(value=RESULT_ID)
    SysUserEntity selectById(Long id);

    /**
     * 根据 唯一索引 username 获取实体对象
     * @param username username
     * @return 返回实体对象
     */
    @Select("select * from t_sys_user where username=#{username} limit 1")
    @ResultMap(value=RESULT_ID)
    SysUserEntity getByUsername(String username);

    /**
     * 查询所有列表
     * @return list
     */
    @Select("select * from t_sys_user limit 1000")
    @ResultMap(value=RESULT_ID)
    List<SysUserEntity> listAll();

    /**
     * 查询总用户数量
     */
    @Select("select count(*) from t_sys_user")
    Long count();

    @SelectProvider(type = SysUserSelectProvider.class, method = "listByMap")
    List<SysUserEntity> listByMap(Map<String, Object> map);


    /**
     * SysUserMapper 的查询Provider
     */
    class SysUserSelectProvider {

        /**
         * 根据ID查询SQL
         * @param id id
         * @return sqlStr
         */
        public String selectById(Long id) {
            return SqlConstants.SELECT_ALL_FROM + TABLE_NAME + SqlConstants.WHERE_ID;
        }

        /**
         * 根据map进行查询
         * @param map 查询map
         * @return 返回结果
         */
        public String listByMap(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT(SqlConstants.ASTERISK);
                    FROM(TABLE_NAME);
                    if(ObjectTools.isNotNull(map.get("status"))){
                        WHERE("status="+ map.get("status"));
                    }
                    if(ObjectTools.isNotNull(map.get("delFlag"))){
                        WHERE("del_flag="+ map.get("delFlag"));
                    }
                    ORDER_BY((String)map.get("orderCols"));
                    LIMIT((String)map.get("limit"));
                }
            }.toString();
        }
    }
}
