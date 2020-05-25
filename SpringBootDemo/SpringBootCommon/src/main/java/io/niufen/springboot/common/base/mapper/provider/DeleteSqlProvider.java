package io.niufen.springboot.common.base.mapper.provider;

import io.niufen.common.enums.YesOrNoEnum;
import io.niufen.common.util.ReflectionUtils;
import io.niufen.springboot.common.base.mapper.BaseProvider;
import io.niufen.springboot.common.constant.SqlConstants;
import io.niufen.springboot.common.metadata.TableInfo;
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
public class DeleteSqlProvider extends BaseProvider {

    public String deleteById(ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .DELETE_FROM(table.getTableName())
                .WHERE(table.getPrimaryKeyColumn() + " = #{id}")
                .toString();
    }

    public String deleteLogicById(ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .UPDATE(table.getTableName())
                .SET("del_flag = "+ YesOrNoEnum.YES.getIndex())
                .WHERE(table.getPrimaryKeyColumn() + " = #{id}")
                .toString();
    }

    public String deleteByCriteria(Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .DELETE_FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .toString();
    }

    public String deleteLogicByCriteria(Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .UPDATE(table.getTableName())
                .SET("del_flag = "+ YesOrNoEnum.YES.getIndex())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .toString();
    }

    public String deleteByMap(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .DELETE_FROM(table.getTableName())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getMapValue(field, params) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .toString();
    }

    public String deleteLogicByMap(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .UPDATE(table.getTableName())
                .SET("del_flag = "+ YesOrNoEnum.YES.getIndex())
                .WHERE(Stream.of(table.getFields())
                        .filter(field -> ReflectionUtils.getMapValue(field, params) != null)
                        .map(TableInfo::assignParameter)
                        .toArray(String[]::new))
                .toString();
    }

    public String deleteBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .DELETE_FROM(table.getTableName())
                .WHERE(table.getPrimaryKeyColumn()
                        + " IN (" + String.join(",", idList.stream().map(String::valueOf).toArray(String[]::new)) +")")
                .toString();
    }
    public String deleteLogicBatchIds(@Param(SqlConstants.KEY_ID_LIST) Collection<? extends Serializable> idList, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .UPDATE(table.getTableName())
                .SET("del_flag = "+ YesOrNoEnum.YES.getIndex())
                .WHERE(table.getPrimaryKeyColumn()
                        + " IN (" + String.join(",", idList.stream().map(String::valueOf).toArray(String[]::new)) +")")
                .toString();
    }
}
