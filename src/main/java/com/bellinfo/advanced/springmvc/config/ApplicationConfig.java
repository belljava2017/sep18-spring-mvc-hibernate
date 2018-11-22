package com.bellinfo.advanced.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.bellinfo.advanced.springmvc")
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationConfig {

     @Bean
     public UrlBasedViewResolver urlBasedViewResolver(){
         UrlBasedViewResolver resolver = new UrlBasedViewResolver();
         resolver.setPrefix("/WEB-INF/pages/");
         resolver.setSuffix(".jsp");
         resolver.setViewClass(JstlView.class);
         return resolver;
     }

     @Bean
     public DataSource dataSource(){
         DriverManagerDataSource dmds = new DriverManagerDataSource();
         dmds.setDriverClassName("org.postgresql.Driver");
         dmds.setUrl("jdbc:postgresql://127.0.0.1:5433/belljavasep");
         dmds.setUsername("postgres");
         dmds.setPassword("abcd12345");
         return dmds;
     }


     @Bean
     public LocalSessionFactoryBean sessionFactory(){

         Properties hibProp = new Properties();
         hibProp.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
         hibProp.put("hibernate.hbm2ddl.auto", "update");
         hibProp.put("hibernate.show_sql", "true");
         hibProp.put("hibernate.default_schema", "learning");

         LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
         sessionFactory.setDataSource(dataSource());
         sessionFactory.setHibernateProperties(hibProp);
         sessionFactory.setPackagesToScan("com.bellinfo.advanced.springmvc.model");
         return sessionFactory;
     }

     @Bean
     public HibernateTransactionManager hibernateTransactionManager(){
         HibernateTransactionManager hibTxMgr = new HibernateTransactionManager();
         hibTxMgr.setSessionFactory(sessionFactory().getObject());
         return hibTxMgr;
     }





      /*@Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver irvr = new InternalResourceViewResolver();
        irvr.setPrefix("/WEB-INF/pages/");
        irvr.setSuffix(".jsp");
        return irvr;
    }*/


}
