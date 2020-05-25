package io.niufen.springboot.common.base.mapper.provider;

import io.niufen.common.util.PlaceholderResolver;
import io.niufen.common.util.ReflectionUtils;
import io.niufen.springboot.common.base.mapper.BaseProvider;
import io.niufen.springboot.common.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author haijun.zhang
 * @date 2020/5/11
 * @time 16:51
 */
@Slf4j
public class InsertSqlProvider extends BaseProvider {

    /**
     * insert
     * @param context context
     * @return  sql
     */
    public String insert(ProviderContext context) {
        TableInfo table = tableInfo(context);
        return new SQL()
                .INSERT_INTO(table.getTableName())
                .INTO_COLUMNS(table.getColumns())
                .INTO_VALUES(Stream.of(table.getFields()).map(TableInfo::bindParameter).toArray(String[]::new))
                .toString();
    }


    /**
     * sql
     * @param entity  entity
     * @param context context
     * @return  sql
     */
    public String insertSelective(Object entity, ProviderContext context) {
        TableInfo table = tableInfo(context);
        Field[] notNullFields = Stream.of(table.getFields())
                .filter(field -> ReflectionUtils.getFieldValue(field, entity) != null && !table.getPrimaryKeyColumn().equals(TableInfo.columnName(field)))
                .toArray(Field[]::new);

        return new SQL()
                .INSERT_INTO(table.getTableName())
                .INTO_COLUMNS(TableInfo.columns(notNullFields))
                .INTO_VALUES(Stream.of(notNullFields).map(TableInfo::bindParameter).toArray(String[]::new))
                .toString();

    }


    /**
     * batchInsert
     * @param context context
     * @return  sql
     */
    public String batchInsert(Map<String, Object> param, ProviderContext context) {
        TableInfo table = tableInfo(context);
        @SuppressWarnings("unchecked")
        int size = ((List<Object>)param.get("entities")).size();
        String value = "(" + String.join(",", Stream.of(table.getFields())
                .map(field -> "#{entities[${index}]." + field.getName() + "}").toArray(String[]::new)) + ")";
        String[] values = new String[size];
        Map<String, Object> fillIndex = new HashMap<>(2);
        for (int i = 0; i < size; i++) {
            fillIndex.put("index", i);
            values[i] = PlaceholderResolver.getDefaultResolver().resolveByMap(value, fillIndex);
        }

        SQL sql = new SQL()
                .INSERT_INTO(table.getTableName())
                .INTO_COLUMNS(table.getColumns());
        return sql.toString() + " VALUES " + String.join(",", values);
    }
}
