/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.niufen.springboot.common.util;

import io.niufen.common.util.CollectionUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * SQL 辅助类
 *
 * @author hubin
 * @since 2016-11-06
 */
public final class SqlHelper {

    /**
     * 主要用于 service 和 ar
     */
    public static SqlSessionFactory FACTORY;

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

    /**
     * 返回SelectCount执行结果
     *
     * @param result ignore
     * @return int
     */
    public static int retCount(Integer result) {
        return (null == result) ? 0 : result;
    }

    /**
     * 从list中取第一条数据返回对应List中泛型的单个结果
     *
     * @param list ignore
     * @param <E>  ignore
     * @return ignore
     */
    public static <E> E getObject(Log log, List<E> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            int size = list.size();
            if (size > 1) {
                log.warn(String.format("Warn: execute Method There are  %s results.", size));
            }
            return list.get(0);
        }
        return null;
    }
}
