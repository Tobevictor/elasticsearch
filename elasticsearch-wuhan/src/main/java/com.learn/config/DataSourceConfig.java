package com.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Date 2019/8/21 12:12
 * @Created by dshuyou
 */
@Configuration
public class DataSourceConfig {

	@Bean(name = "datasource1")
	@ConfigurationProperties("spring.datasource.database1")
	@Primary
	public DataSource dataSource(){
		return new DruidDataSource();
	}

	@Bean(name = "datasource2")
	@ConfigurationProperties("spring.datasource.database2")
	public DataSource dataSource2(){
		return new DruidDataSource();
	}

	@Bean(name="tm1")
	@Autowired
	@Primary
	DataSourceTransactionManager tm1(@Qualifier("datasource1") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
		return txm;
	}

	@Bean(name="tm2")
	@Autowired
	DataSourceTransactionManager tm2(@Qualifier ("datasource2") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
		return txm;
	}
}
