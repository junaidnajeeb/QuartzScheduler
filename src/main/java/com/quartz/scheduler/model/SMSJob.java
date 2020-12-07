package com.quartz.scheduler.model;

import java.util.HashMap;
import java.util.Map;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class SMSJob extends QuartzJob {

  private static final Logger logger = LoggerFactory.getLogger(ConsoleJob.class);
  public static final String MESSAGE_DATAMAP_KEY = "message";
  public static final String PHONE_NUMBER_DATAMAP_KEY = "phoneNumber";
  
  private String message;
  private String phoneNumber;
  
  @Autowired
  private Environment env;

  
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

    logger.info("Executing SMSJob -> integrate with SMS API service here with id {}", getId());
    String ACCOUNT_SID = env.getProperty("twilio.account.id");
    String AUTH_TOKEN = env.getProperty("twilio.account.token");
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    MessageCreator messageCreater = Message
      .creator(new PhoneNumber(getPhoneNumber()), // to
             new PhoneNumber(getPhoneNumber()), // from
             getMessage());

     // send
     Message message = messageCreater.create();
     //System.out.println(message.getSid());

  }
}
