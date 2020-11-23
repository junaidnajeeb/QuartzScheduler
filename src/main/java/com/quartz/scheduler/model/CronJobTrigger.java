package com.quartz.scheduler.model;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class CronJobTrigger extends JobTrigger {
  
  private String cronExpression;

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  @Override
  protected Trigger buildTrigger() {
    
    //TODO:: add TimeZone.... maybe at base class level
    return TriggerBuilder.newTrigger()
    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
    .build();
  }

}
