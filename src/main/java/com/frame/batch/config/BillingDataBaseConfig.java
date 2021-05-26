package com.frame.batch.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mapstruct.Qualifier;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
})
@MapperScan(value="com.frame.batch.dao.billing", sqlSessionFactoryRef = "billingSqlSessionFactory")
@RequiredArgsConstructor
public class BillingDataBaseConfig {

    private final ApplicationContext applicationContext;
    private final Environment env;

    @Primary
    @Bean(name = "billingDataSource")
    public HikariDataSource billingDataSource() {
        //TODO Master, Slave 분리... write, read 분리해서 쿼리에 적용
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(env.getProperty("BILLING_DATABASE_DRIVER_CLASSNAME"));
        ds.setJdbcUrl(env.getProperty("BILLING_DATABASE_URL"));
        ds.setUsername(env.getProperty("BILLING_DATABASE_USERNAME"));
        ds.setPassword(env.getProperty("BILLING_DATABASE_PASSWORD"));
        ds.setPoolName(env.getProperty("BILLING_DATABASE_POOL_NAME"));
        return ds;
    }

    @Primary
    @Bean(name="billingSqlSessionFactory")
    public SqlSessionFactory billingSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(billingDataSource());
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mapper/billing-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/billing/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "billingSqlSessionTemplate")
    public SqlSessionTemplate billingSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(billingSqlSessionFactory());
    }





}
