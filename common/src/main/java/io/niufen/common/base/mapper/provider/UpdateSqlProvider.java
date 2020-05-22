package io.niufen.common.base.mapper.provider;

import io.niufen.common.base.mapper.BaseProvider;
import io.niufen.common.constant.SqlConstants;
import io.niufen.common.metadata.TableInfo;
import io.niufen.common.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author haijun.zhang
 * @date 2020/5/11
 * @time 16:51
 */
@Slf4j
public class UpdateSqlProvider extends BaseProvider {

    public String updateById(ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .UPDATE(table.getTableName())
                .SET(Stream.of(table.getFields())
                        .filter(field -> !table.getPrimaryKeyColumn().equals(TableInfo.columnName(field)))
                        .map(TableInfo::assignParameter).toArray(String[]::new))
                .WHERE(table.getPrimaryKeyWhere())
                .toString();
    }
    public String updateSelectiveById(Object entity, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .UPDATE(table.getTableName())
                .SET(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, entity) != null && !table.getPrimaryKeyColumn().equals(TableInfo.columnName(field)))
                        .map(TableInfo::assignParameter).toArray(String[]::new))
                .WHERE(table.getPrimaryKeyWhere())
                .toString();
    }

    public String updateSelectiveByMap(@Param(SqlConstants.KEY_ENTITY) Object entity, @Param(SqlConstants.KEY_MAP_PARAMS) Map<String,Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .UPDATE(table.getTableName())
                .SET(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, entity) != null && !table.getPrimaryKeyColumn().equals(TableInfo.columnName(field)))
                        .map(TableInfo::assignParameterByEntity).toArray(String[]::new))
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getMapValue(field, params) != null)
                        .map(TableInfo::assignParameterByParams)
                        .toArray(String[]::new))
                .toString();
    }

    public String updateSelectiveByCriteria(@Param(SqlConstants.KEY_ENTITY) Object entity, @Param(SqlConstants.KEY_CRITERIA) Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .UPDATE(table.getTableName())
                .SET(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, entity) != null && !table.getPrimaryKeyColumn().equals(TableInfo.columnName(field)))
                        .map(TableInfo::assignParameterByEntity).toArray(String[]::new))
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                        .map(TableInfo::assignParameterByCriteria)
                        .toArray(String[]::new))
                .toString();
    }

    public String updateSelectiveBatchIds(@Param(SqlConstants.KEY_ENTITY) Object entity, @Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .UPDATE(table.getTableName())
                .SET(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, entity) != null && !table.getPrimaryKeyColumn().equals(TableInfo.columnName(field)))
                        .map(TableInfo::assignParameterByEntity).toArray(String[]::new))
                .WHERE(table.getPrimaryKeyColumn()
                        + " IN (" + String.join(",", idList.stream().map(String::valueOf).toArray(String[]::new)) +")")
                .toString();
    }
}
