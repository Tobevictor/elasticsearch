package com.learn.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
@Configuration
public class SecondDataSourceConfig {

    @Bean(name = "datasource2")
    @ConfigurationProperties("spring.datasource.database2")
    public DataSource dataSource2(){
        return new DruidDataSource();
    }


    @Bean(name="tm2")
    @Autowired
    DataSourceTransactionManager tm2(@Qualifier ("datasource2") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }

    @Bean(name = "sqlsessionFactory2")
    @Autowired
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("datasource2") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "datasourceTemplate2")
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate2(
            @Qualifier("sqlsessionFactory2") SqlSessionFactory sessionFactory) throws Exception {
        return new SqlSessionTemplate(sessionFactory);
    }

}
