/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.niufen.springboot.druid.testcase;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lihengming [89921218@qq.com]
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidDataSourceTestCase {

    @Resource
    private DruidDataSource dataSource;

    @Test
    public void testDataSourceExists() throws Exception {
        assertThat(dataSource).isNotNull();
    }

    @Test
    public void testDataSourcePropertiesOverridden() throws Exception {
        assertThat(dataSource.getUrl()).isEqualTo("jdbc:mysql://127.0.0.1:3306/springboot_demo?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false");
        assertThat(dataSource.getInitialSize()).isEqualTo(0);
        assertThat(dataSource.getMaxActive()).isEqualTo(8);
        assertThat(dataSource.getMinIdle()).isEqualTo(0);
        assertThat(dataSource.getMaxWait()).isEqualTo(-1L);
        assertThat(dataSource.isPoolPreparedStatements()).isTrue();
        //assertThat(dataSource.getMaxOpenPreparedStatements()).isEqualTo(5); //Duplicated with following
        assertThat(dataSource.getMaxPoolPreparedStatementPerConnectionSize()).isEqualTo(5);
        assertThat(dataSource.getValidationQuery()).isEqualTo("select 1");
        assertThat(dataSource.getValidationQueryTimeout()).isEqualTo(1);
        assertThat(dataSource.isTestWhileIdle()).isTrue();
        assertThat(dataSource.isTestOnBorrow()).isTrue();
        assertThat(dataSource.isTestOnReturn()).isTrue();
        assertThat(dataSource.getTimeBetweenEvictionRunsMillis()).isEqualTo(10000);
        assertThat(dataSource.getMinEvictableIdleTimeMillis()).isEqualTo(30001);
        assertThat(dataSource.isAsyncCloseConnectionEnable()).isEqualTo(true);
    }
}
