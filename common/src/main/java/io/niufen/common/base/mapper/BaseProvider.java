package io.niufen.common.base.mapper;

import io.niufen.common.metadata.TableInfo;
import io.niufen.common.metadata.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * @author haijun.zhang
 * @date 2020/5/11
 * @time 16:51
 */
@Slf4j
public class BaseProvider {
    /**
     * 获取表信息结构
     *
     * @param context  provider context
     * @return  表基本信息
     */
    protected TableInfo tableInfo(ProviderContext context) {
        // 如果不存在则创建
        return TableInfoHelper.tableInfo(context);
    }
}
