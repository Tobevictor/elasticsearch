package com.learn.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.learn.mbg.mapper3",sqlSessionTemplateRef = "datasourceTemplate3")
public class ThirdDataSourceConfig {

    @Bean(name = "datasource3")
    @ConfigurationProperties(prefix="spring.datasource.database3")
    public DataSource thirdDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name="tm3")
    DataSourceTransactionManager tm3(@Qualifier ("datasource3") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }

    @Bean(name = "sqlsessionFactory03")
    public SqlSessionFactory sqlSessionFactory3(@Qualifier("datasource3") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
        System.out.println("sqlsession03");
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "datasourceTemplate3")
    public SqlSessionTemplate sqlSessionTemplate3(
            @Qualifier("sqlsessionFactory03") SqlSessionFactory sessionFactory) throws Exception {
        return new SqlSessionTemplate(sessionFactory);
    }
}
