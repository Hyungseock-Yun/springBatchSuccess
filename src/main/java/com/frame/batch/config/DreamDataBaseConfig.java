package com.frame.batch.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
})
@MapperScan(value="com.frame.batch.dao.dream", sqlSessionFactoryRef = "dreamSqlSessionFactory")
@RequiredArgsConstructor
public class DreamDataBaseConfig {

    private final ApplicationContext applicationContext;
    private final Environment env;


    @Bean(name = "dreamDataSource")
    public HikariDataSource dreamDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(env.getProperty("DREAM_DATABASE_DRIVER_CLASSNAME"));
        ds.setJdbcUrl(env.getProperty("DREAM_DATABASE_URL"));
        ds.setUsername(env.getProperty("DREAM_DATABASE_USERNAME"));
        ds.setPassword(env.getProperty("DREAM_DATABASE_PASSWORD"));
        ds.setPoolName(env.getProperty("DREAM_DATABASE_POOL_NAME"));
        return ds;
    }

    @Bean(name="dreamSqlSessionFactory")
    public SqlSessionFactory dreamSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dreamDataSource());
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mapper/dream-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/dream/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "dreamSqlSessionTemplate")
    public SqlSessionTemplate dreamSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(dreamSqlSessionFactory());
    }


}
