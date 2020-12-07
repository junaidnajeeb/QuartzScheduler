package com.quartz.scheduler.model;

import java.util.HashMap;
import java.util.Map;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSJob extends QuartzJob {

  private static final Logger logger = LoggerFactory.getLogger(ConsoleJob.class);
  public static final String MESSAGE_DATAMAP_KEY = "message";
  public static final String PHONE_NUMBER_DATAMAP_KEY = "phoneNumber";
  
  private String message;
  private String phoneNumber;
  
  public SMSJob() {

  }

  public SMSJob(String message, String phoneNumber) {
    this.message = message;
    this.phoneNumber = phoneNumber;
  }
  
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  protected void initJobFromDataMap(Map<String, Object> dataMap) {
    message = (String)dataMap.get(MESSAGE_DATAMAP_KEY);
    phoneNumber = (String)dataMap.get(PHONE_NUMBER_DATAMAP_KEY);
  }

  @Override
  protected Map<String, Object> buildDataMap() {
    Map<String, Object> jobDataMap = new HashMap<String, Object>();
    jobDataMap.put("message", getMessage());
    jobDataMap.put("phoneNumber", getPhoneNumber());
    return jobDataMap;
  }
  
  @Override
  public void run() {

    JobDetail jobDetail = jobExecutionContext.getJobDetail();

    logger.info("Executing SMSJob -> integrate with SMS API service here with id {}", getId());

    logger.info("--------------------------------------------------------------------");
    logger.info("SMSJob start: " + jobExecutionContext.getFireTime());
    logger.info("message is: " + jobDetail.getJobDataMap().getString("message"));
    logger.info("phoneNumber is: " + jobDetail.getJobDataMap().getString("phoneNumber"));
    logger.info("SMSJob end: " + jobExecutionContext.getJobRunTime() + ", key: " + jobDetail.getKey());
    logger.info("SMSJob next scheduled time: " + jobExecutionContext.getNextFireTime());
    logger.info("--------------------------------------------------------------------");
  }
}
