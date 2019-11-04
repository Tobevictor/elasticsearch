package com.learn.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @date 2019/8/21 12:12
 * @author dshuyou
 */
@Configuration
@MapperScan(basePackages = "com.learn.mbg.mapper1",sqlSessionTemplateRef = "datasourceTemplate1")
public class FirstDataSourceConfig {

	@Bean(name = "datasource1")
	@ConfigurationProperties("spring.datasource.database1")
	@Primary
	public DataSource dataSource(){
		return new DruidDataSource();
	}

	/*@Bean(name = "datasource2")
	@ConfigurationProperties("spring.datasource.database2")
	public DataSource dataSource2(){
		return new DruidDataSource();
	}

	@Bean(name = "datasource3")
	@ConfigurationProperties(prefix="spring.datasource.database3")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}*/

	@Bean(name="tm1")
	@Primary
	DataSourceTransactionManager tm1(@Qualifier("datasource1") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
		return txm;
	}

	/*@Bean(name="tm2")
	@Autowired
	DataSourceTransactionManager tm2(@Qualifier ("datasource2") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
		return txm;
	}

	@Bean(name="tm3")
	@Autowired
	DataSourceTransactionManager tm3(@Qualifier ("datasource3") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
		return txm;
	}*/

	@Bean(name = "sqlsessionFactory01")
	@Primary
	public SqlSessionFactory sqlSessionFactory1(@Qualifier("datasource1") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
		System.out.println("sqlsession01");
		return sessionFactoryBean.getObject();
	}

	/*@Bean(name = "sqlsessionFactory2")
	@Autowired
	public SqlSessionFactory sqlSessionFactory2(@Qualifier("datasource2") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "sqlsessionFactory3")
	@Autowired
	public SqlSessionFactory sqlSessionFactory3(@Qualifier("datasource3") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
		return sessionFactoryBean.getObject();
	}
*/
	/**
	 * 创建sqlSessionTemplate
	 *
	 */
	@Bean(name = "datasourceTemplate1")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate1(
			@Qualifier("sqlsessionFactory01") SqlSessionFactory sessionFactory) throws Exception {
		return new SqlSessionTemplate(sessionFactory);
	}

	/*@Bean(name = "datasourceTemplate2")
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate2(
			@Qualifier("sqlsessionFactory2") SqlSessionFactory sessionFactory) throws Exception {
		return new SqlSessionTemplate(sessionFactory);
	}

	@Bean(name = "datasourceTemplate3")
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate3(
			@Qualifier("sqlsessionFactory3") SqlSessionFactory sessionFactory) throws Exception {
		return new SqlSessionTemplate(sessionFactory);
	}*/
}
