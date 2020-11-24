package com.quartz.scheduler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.service.spi.ServiceException;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quartz.scheduler.config.QuartzConfig;
import com.quartz.scheduler.exception.DuplicateJobKeyException;
import com.quartz.scheduler.model.BaseJob;

@Service
public class SchedulerService {

  private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

  @Autowired
  private QuartzConfig quartzConfig;

  private Scheduler quartzScheduler;

  @Autowired
  public void setQuartzScheduler() throws IOException {
    this.quartzScheduler = quartzConfig.schedulerFactoryBean().getScheduler();
  }



  public void scheduleJob(BaseJob job) throws DuplicateJobKeyException {


    try {
      JobDetail quartzJobDetail = job.buildQuartzJobDetail();
      Trigger quartzTrigger = job.buildTrigger();

      quartzScheduler.scheduleJob(quartzJobDetail, quartzTrigger);
      quartzScheduler.start();
      logger.info("Job scheduled with group={} name={}", job.getGroup(), job.getName());
    } catch (ObjectAlreadyExistsException e) {
      throw new DuplicateJobKeyException(job.getGroup(), job.getName(), e);
    } catch (SchedulerException e) {
      throw new ServiceException(e.getMessage());
    }

  }


  public Set<String> getJobKeys() {

    Set<String> jobSet = new HashSet<String>();
    try {
      for (String group : quartzScheduler.getJobGroupNames()) {
        // enumerate each job in group

        for (JobKey jobKey : quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals((group)))) {
          jobSet.add(jobKey.getGroup() + jobKey.getName());

        }
      }
    } catch (SchedulerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    return jobSet;
  }

}
