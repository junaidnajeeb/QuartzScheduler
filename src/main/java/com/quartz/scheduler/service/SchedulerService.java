package com.quartz.scheduler.service;

import java.io.IOException;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quartz.scheduler.config.QuartzConfig;
import com.quartz.scheduler.model.BaseJob;

@Service
public class SchedulerService {

  private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

  @Autowired
  private QuartzConfig quartzConfig;

  
  
  public void scheduleJob(BaseJob job) {
    
    try {
      Scheduler quartzScheduler = quartzConfig.schedulerFactoryBean().getScheduler();

      JobDetail quartzJobDetail = job.buildQuartzJobDetail();
      Trigger quartzTrigger = job.buildTrigger();

      quartzScheduler.scheduleJob(quartzJobDetail, quartzTrigger);
      quartzScheduler.start();

    } catch (IOException | SchedulerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }


}
