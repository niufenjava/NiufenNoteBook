package io.niufen.common.base.mapper.provider;

import io.niufen.common.base.mapper.BaseProvider;
import io.niufen.common.constant.SqlConstants;
import io.niufen.common.metadata.TableInfo;
import io.niufen.common.util.ReflectionUtils;
import io.niufen.common.util.StringUtils;
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
public class SelectSqlProvider extends BaseProvider {

    public String selectById(ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName())
                .WHERE(table.getPrimaryKeyWhere())
                .toString();
    }

    public String selectBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName())
                .WHERE(table.getPrimaryKeyColumn()
                        + " IN (" + String.join(",", idList.stream().map(String::valueOf).toArray(String[]::new)) +")")
                .toString();
    }

    public String selectByMap(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getMapValue(field, params) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .LIMIT(1000)
                .toString();
    }
    public String selectByCriteria(Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .toString();
    }

    public String selectOneByMap(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getMapValue(field, params) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .LIMIT(1)
                .toString();
    }

    public String selectOneByCriteria(Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .LIMIT(1)
                .toString();
    }

    public String selectCountByMap(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()

                .SELECT(SqlConstants.COUNT)
                .FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getMapValue(field, params) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .LIMIT(1)
                .toString();
    }

    public String selectCountByCriteria(Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return new SQL()
                .SELECT(SqlConstants.COUNT)
                .FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .toString();
    }

    public String selectAll(String orderBy, ProviderContext context) {
        TableInfo table = tableInfo(context);

        SQL sql = new SQL()
                .SELECT(table.getSelectColumns())
                .FROM(table.getTableName());
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = table.getPrimaryKeyColumn() + " DESC";
        }
        return sql.ORDER_BY(orderBy).LIMIT(5000).toString();
    }
}
