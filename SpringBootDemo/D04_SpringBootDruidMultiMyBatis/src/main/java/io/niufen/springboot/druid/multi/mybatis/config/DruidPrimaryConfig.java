package io.niufen.springboot.druid.multi.mybatis.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = DruidPrimaryConfig.PACKAGE, sqlSessionFactoryRef = "primarySqlSessionFactory")
public class DruidPrimaryConfig {

	// mysqldao扫描路径
	public static final String PACKAGE = "io.niufen.springboot.druid.multi.mybatis.mapper";
	// mybatis mapper扫描路径
	public static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

	@Primary
	@Bean(name = "primaryDatabase")
	@ConfigurationProperties("spring.datasource.druid.primary")
	public DataSource mysqlDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "primaryTransactionManager")
	@Primary
	public DataSourceTransactionManager primaryTransactionManager() {
		return new DataSourceTransactionManager(mysqlDataSource());
	}

	@Bean(name = "primarySqlSessionFactory")
	@Primary
	public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDatabase") DataSource dataSource)
			throws Exception {

		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		//如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。

		String resource = "classpath:mybatis-config.xml";
		sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(resource));
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DruidPrimaryConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
}
