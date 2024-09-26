package com.example.web.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.web.dao"})
public class MybatisConfig {

    private final Environment environment;

    public MybatisConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/dao/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.web.model");

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        if (sqlSessionFactory != null) {
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
            sqlSessionFactory.getConfiguration().setLazyLoadingEnabled(true);
            sqlSessionFactory.getConfiguration().setCacheEnabled(false);
            sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        }

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSource dataSource() throws Exception {
        BasicDataSource dataSource = null;
        try {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
            dataSource.setUrl(environment.getProperty("spring.datasource.url"));
            dataSource.setUsername(environment.getProperty("spring.datasource.username"));
            dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        } catch (Exception e) {
            if (dataSource != null) {
                dataSource.close();
            }
            throw e;
        }
        return dataSource;
    }

}
