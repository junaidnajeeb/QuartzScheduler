package com.quartz.scheduler.model;

import java.util.HashMap;
import java.util.Map;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleJob extends BaseJob {


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

    JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

    String consoleMessaage = dataMap.getString("message");

    logger.info("Executing job for id {}", getId());

    logger.info("Message Found={}", consoleMessaage);
    logger.info("Message Found={}", message);

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

}
