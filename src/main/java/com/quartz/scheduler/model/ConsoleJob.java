package com.quartz.scheduler.model;

import java.util.HashMap;
import java.util.Map;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleJob extends QuartzJob {


  private static final Logger logger = LoggerFactory.getLogger(ConsoleJob.class);
  public static final String MESSAGE_DATAMAP_KEY = "message";

  private String message;
  
  
  public ConsoleJob() {
  }
  
  public ConsoleJob(String message) {
    this.message = message;
  }
  @Override
  public void run() {

//    JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    JobDetail jobDetail = jobExecutionContext.getJobDetail();

    logger.info("Executing ConsoleJob with id {}", getId());

    logger.info("--------------------------------------------------------------------");
    logger.info("ConsoleJob start: " + jobExecutionContext.getFireTime());
    logger.info("Example name is: " + jobDetail.getJobDataMap().getString("message"));
    logger.info("ConsoleJob end: " + jobExecutionContext.getJobRunTime() + ", key: " + jobDetail.getKey());
    logger.info("ConsoleJob next scheduled time: " + jobExecutionContext.getNextFireTime());
    logger.info("--------------------------------------------------------------------");

  }

  @Override
  protected Map<String, Object> buildDataMap() {
    Map<String, Object> jobDataMap = new HashMap<String, Object>();
    jobDataMap.put("message", getMessage());
    return jobDataMap;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  protected void initFromDataMap(Map<String, Object> dataMap) {
    // TODO Auto-generated method stub
    
  }

}
