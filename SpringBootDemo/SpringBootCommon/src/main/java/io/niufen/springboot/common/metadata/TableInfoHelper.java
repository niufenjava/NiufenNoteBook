package io.niufen.springboot.common.metadata;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haijun.zhang
 * @date 2020/5/16
 * @time 13:43
 */
public class TableInfoHelper {

    /**
     * key -> mapper class   value -> tableInfo
     */
    private static Map<Class<?>, TableInfo> tableCache = new ConcurrentHashMap<>(128);

    /**
     * 获取表信息结构
     *
     * @param context  provider context
     * @return  表基本信息
     */
    public static TableInfo tableInfo(ProviderContext context) {
        // 如果不存在则创建
        return tableCache.computeIfAbsent(context.getMapperType(), TableInfo::of);
    }

    /**
     * 获取表信息结构
     *
     * @param entity  provider context
     * @return  表基本信息
     */
    public static TableInfo tableInfo(Class<?> entity) {
        // 如果不存在则创建
        return tableCache.computeIfAbsent(entity, TableInfo::off);
    }

}
