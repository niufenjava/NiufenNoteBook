package io.niufen.springboot.common.metadata;

import io.niufen.common.core.constant.StringConstants;
import io.niufen.common.core.util.CollUtil;
import io.niufen.common.core.util.ReflectUtil;
import io.niufen.common.core.util.StrUtil;
import io.niufen.springboot.common.util.MapperUtils;
import io.niufen.springboot.common.annotation.NoColumn;
import io.niufen.springboot.common.annotation.PrimaryKey;
import io.niufen.springboot.common.annotation.TableName;
import io.niufen.springboot.common.constant.SqlConstants;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * table info
 *
 * @author meilin.huang
 * @date 2020-02-16 3:50 下午
 */
@Data
public class TableInfo {

    /**
     * 表前缀
     */
    private static final String TABLE_PREFIX = "t_";

    /**
     * 主键名
     */
    private static final String DEFAULT_PRIMARY_KEY = "id";

    /**
     * 表名
     */
    private String tableName;

    /**
     * 实体类型不含@NoColunm注解的field
     */
    private Field[] fields;

    /**
     * 主键列名
     */
    private String primaryKeyColumn;

    /**
     * 所有列名
     */
    private String[] columns;

    /**
     * 所有select sql的列名，有带下划线的将其转为aa_bb AS aaBb
     */
    private String[] selectColumns;

    private TableInfo() {
    }

    /**
     * 获取主键的where条件，如 id = #{id}
     *
     * @return 主键where条件
     */
    public String getPrimaryKeyWhere() {
        String pk = this.primaryKeyColumn;
        return pk + " = #{" + pk + "}";
    }

    /**
     * 获取TableInfo的简单工厂
     *
     * @param mapperType mapper类型
     * @return {@link TableInfo}
     */
    public static TableInfo of(Class<?> mapperType) {

        Class<?> entityClass = entityType(mapperType);
        // 获取不含有@NoColumn注解的fields
        Field[] fields = excludeNoColumnField(entityClass);
        TableInfo tableInfo = new TableInfo();
        tableInfo.fields = fields;
        tableInfo.tableName = tableName(entityClass);
        tableInfo.primaryKeyColumn = primaryKeyColumn(fields);
        tableInfo.columns = columns(fields);
        tableInfo.selectColumns = selectColumns(fields);
        return tableInfo;
    }

    /**
     * 获取TableInfo的简单工厂
     *
     * @param entityClass mapper类型
     * @return {@link TableInfo}
     */
    public static TableInfo off(Class<?> entityClass) {

        // 获取不含有@NoColumn注解的fields
        Field[] fields = excludeNoColumnField(entityClass);
        TableInfo tableInfo = new TableInfo();
        tableInfo.fields = fields;
        tableInfo.tableName = tableName(entityClass);
        tableInfo.primaryKeyColumn = primaryKeyColumn(fields);
        tableInfo.columns = columns(fields);
        tableInfo.selectColumns = selectColumns(fields);
        return tableInfo;
    }

    /**
     * 获取BaseMapper接口中的泛型类型
     *
     * @param mapperType mapper类型
     * @return 实体类型
     */
    public static Class<?> entityType(Class<?> mapperType) {
        for (Type genericInterface : mapperType.getGenericInterfaces()) {
            Type[] params = ((ParameterizedType) genericInterface).getActualTypeArguments();
            return (Class<?>) params[0];
        }
        return null;
    }


    /**
     * 获取表名
     *
     * @param entityType 实体类型
     * @return 表名
     */
    public static String tableName(Class<?> entityType) {
        TableName table = entityType.getAnnotation(TableName.class);
        return table == null ? TABLE_PREFIX + StrUtil.toUnderlineCase(MapperUtils.removeEntityStr(entityType.getSimpleName())) : table.value();
    }

    /**
     * 过滤含有@NoColumn注解或者是静态的field
     *
     * @param entityClass 实体类型
     * @return 不包含@NoColumn注解的fields
     */
    public static Field[] excludeNoColumnField(Class<?> entityClass) {
        Field[] allFields = ReflectUtil.getFields(entityClass);
        List<String> excludeColumns = getClassExcludeColumns(entityClass);
        return Stream.of(allFields)
                //过滤掉类上指定的@NoCloumn注解的字段和字段上@NoColumn注解或者是静态的field
                .filter(field -> !CollUtil.contains(excludeColumns, field.getName())
                        && (!field.isAnnotationPresent(NoColumn.class) && !Modifier.isStatic(field.getModifiers())))
                .toArray(Field[]::new);
    }

    /**
     * 获取实体类上标注的不需要映射的字段名
     *
     * @param entityClass 实体类
     * @return 不需要映射的字段名
     */
    public static List<String> getClassExcludeColumns(Class<?> entityClass) {
        List<String> excludeColumns = null;
        NoColumn classNoColumns = entityClass.getAnnotation(NoColumn.class);
        if (classNoColumns != null) {
            excludeColumns = Arrays.asList(classNoColumns.fields());
        }
        return excludeColumns;
    }

    /**
     * 获取查询对应的字段 (不包含pojo中含有@NoColumn主键的属性)
     *
     * @param fields p
     * @return 所有需要查询的查询字段
     */
    public static String[] selectColumns(Field[] fields) {
        return Stream.of(fields).map(TableInfo::selectColumnName).toArray(String[]::new);
    }

    /**
     * 获取所有pojo所有属性对应的数据库字段 (不包含pojo中含有@NoColumn主键的属性)
     *
     * @param fields entityClass所有fields
     * @return 所有的column名称
     */
    public static String[] columns(Field[] fields) {
        return Stream.of(fields).map(TableInfo::columnName).toArray(String[]::new);
    }

    /**
     * 如果fields中含有@Primary的字段，则返回该字段名为主键，否则默认'id'为主键名
     *
     * @param fields entityClass所有fields
     * @return 主键column(驼峰转为下划线)
     */
    public static String primaryKeyColumn(Field[] fields) {
        return Stream.of(fields).filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                .findFirst()    //返回第一个primaryKey的field
                .map(TableInfo::columnName)
                .orElse(DEFAULT_PRIMARY_KEY);
    }

    /**
     * 获取单个属性对应的数据库字段（带有下划线字段将其转换为"字段 AS pojo属性名"形式）
     *
     * @param field 字段
     * @return 带有下划线字段将其转换为"字段 AS pojo属性名"形式
     */
    public static String selectColumnName(Field field) {
        String camel = StrUtil.toUnderlineCase(field.getName());
        return camel.contains("_") ? camel + " AS " + field.getName() : camel;
    }

    /**
     * 获取单个属性对应的数据库字段
     *
     * @param field entityClass中的field
     * @return 字段对应的column
     */
    public static String columnName(Field field) {
        return StrUtil.toUnderlineCase(field.getName());
    }

    /**
     * 绑定参数
     *
     * @param field 字段
     * @return 参数格式
     */
    public static String bindParameter(Field field) {
        return "#{" + field.getName() + "}";
    }
    /**
     * 绑定参数
     *
     * @param field 字段
     * @return 参数格式
     */
    public static String bindParameterByEntity(Field field) {
        return "#{" + SqlConstants.KEY_ENTITY+ StringConstants.Mark.DOT +field.getName() + "}";
    }
    /**
     * 绑定参数
     *
     * @param field 字段
     * @return 参数格式
     */
    public static String bindParameterByParams(Field field) {
        return "#{" + SqlConstants.KEY_MAP_PARAMS+ StringConstants.Mark.DOT +field.getName() + "}";
    }
    /**
     * 绑定参数
     *
     * @param field 字段
     * @return 参数格式
     */
    public static String bindParameterByCriteria(Field field) {
        return "#{" + SqlConstants.KEY_CRITERIA+ StringConstants.Mark.DOT +field.getName() + "}";
    }

    /**
     * 获取该字段的参数赋值语句，如 user_name = #{userName}
     *
     * @param field 字段
     * @return 参数赋值语句
     */
    public static String assignParameter(Field field) {
        return columnName(field) + " = " + bindParameter(field);
    }
    /**
     * 获取该字段的参数赋值语句，如 user_name = #{userName}
     *
     * @param field 字段
     * @return 参数赋值语句
     */
    public static String assignParameterByEntity(Field field) {
        return columnName(field) + " = " + bindParameterByEntity(field);
    }
    /**
     * 获取该字段的参数赋值语句，如 user_name = #{userName}
     *
     * @param field 字段
     * @return 参数赋值语句
     */
    public static String assignParameterByParams(Field field) {
        return columnName(field) + " = " + bindParameterByParams(field);
    }

    /**
     * 获取该字段的参数赋值语句，如 user_name = #{userName}
     *
     * @param field 字段
     * @return 参数赋值语句
     */
    public static String assignParameterByCriteria(Field field) {
        return columnName(field) + " = " + bindParameterByCriteria(field);
    }
}
