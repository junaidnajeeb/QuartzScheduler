package com.quartz.scheduler.config;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

  @Autowired
  ApplicationContext applicationContext;
  
  @Autowired Environment env;

  @Bean
  public JobFactory jobFactory() {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() throws IOException {

    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setQuartzProperties(quartzProperties());
    schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
    schedulerFactory.setAutoStartup(true);
    schedulerFactory.setJobFactory(jobFactory());

    // Comment the following line to use the default Quartz job store.
    schedulerFactory.setDataSource(quartzDataSource());

    return schedulerFactory;
  }

  /*
   * Initializing quartz properties from quartz.properties file
   *
   */
  @Bean
  public Properties quartzProperties() throws IOException {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }


  @Bean
  @QuartzDataSource
  //@ConfigurationProperties(prefix = "spring.datasource")
  public DataSource quartzDataSource() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("spring.datasource.url"));
    dataSource.setUsername(env.getProperty("spring.datasource.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.password"));
    
    return dataSource;
  }

}
