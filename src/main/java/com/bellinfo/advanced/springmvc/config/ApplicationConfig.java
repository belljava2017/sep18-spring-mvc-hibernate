package com.bellinfo.advanced.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource(value="classpath:application.properties")
@ComponentScan(basePackages = "com.bellinfo.advanced.springmvc")
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationConfig {

      private static final String HIB_DIALECT = "hibernate.dialect";

     @Resource
     Environment environment;

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
         dmds.setDriverClassName(environment.getProperty("pg.driver"));
         dmds.setUrl(environment.getProperty("pg.url"));
         dmds.setUsername(environment.getProperty("pg.username"));
         dmds.setPassword(environment.getProperty("pg.password"));
         return dmds;
     }


     @Bean
     public LocalSessionFactoryBean sessionFactory(){

         Properties hibProp = new Properties();
         hibProp.put(HIB_DIALECT, environment.getProperty("hib.dialect"));
         hibProp.put("hibernate.hbm2ddl.auto", environment.getProperty("hib.ddl.strategy"));
         hibProp.put("hibernate.show_sql", environment.getProperty("hib.show.queries"));
         hibProp.put("hibernate.default_schema", environment.getProperty("pg.schema"));

         LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
         sessionFactory.setDataSource(dataSource());
         sessionFactory.setHibernateProperties(hibProp);
         sessionFactory.setPackagesToScan(environment.getProperty("hib.entity.scan"));
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
