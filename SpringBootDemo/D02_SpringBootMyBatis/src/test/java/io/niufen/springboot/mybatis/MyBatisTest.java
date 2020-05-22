package io.niufen.springboot.mybatis;

import io.niufen.springboot.common.module.sys.mapper.SysUserMapperByXml;
import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis 入门测试类
 */
public class MyBatisTest {

    /**
     * MyBatis 创建SqlSession工厂
     * @throws IOException 读取配置文件异常
     */
    @Test
    void buildSqlSessionFactoryByXml() throws IOException {
        String resource = "mybatis-config-test.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.selectOne("io.niufen.springboot.mybatis.mapper.SysUserMapperByXml.getById", 100L);
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            SysUserMapperByXml mapper = session.getMapper(SysUserMapperByXml.class);
            SysUserEntity sysUserEntity = mapper.getById(101L);
        }
    }

}
