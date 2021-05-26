package com.frame.batch.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

//@Configuration
//@PropertySources({
//        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
//})
public class EnvironmentConfig {

//    @Autowired
//    private Environment env;
//
//    @PostConstruct
//    private void postContruct() {
//        String mode = System.getProperty("spring.profiles.active");
//        if (mode == null || mode.equalsIgnoreCase("")) {
//            mode = "local";
//        }
//    }
//
//    @Bean
//    public ServerConfig serverConfig() {
//        String mode = System.getProperty("spring.profiles.active");
//        ServerConfig server = new ServerConfig();
//        if (mode == null || mode.equalsIgnoreCase("")) {
//            mode = "local";
//        }
//        server.SERVER_TYPE = mode;
//        return server;
//    }
//
//    @Data
//    public class ServerConfig {
//        private String SERVER_TYPE;
//    }
//
//    @Bean
//    public AppConfig appConfig() {
//        AppConfig appConfig = new AppConfig();
//        appConfig.setUserName(env.getProperty("DATABASE_USERNAME"));
//        appConfig.setPassword(env.getProperty("DATABASE_PASSWORD"));
//        appConfig.setUrl(env.getProperty("DATABASE_URL"));
//        appConfig.setDriverClassName(env.getProperty("DATABASE_DRIVER_CLASSNAME"));
//
//        return appConfig;
//    }
//
//    @Data
//    public class AppConfig {
//        private String userName;
//        private String password;
//        private String url;
//        private String driverClassName;
//    }

}
