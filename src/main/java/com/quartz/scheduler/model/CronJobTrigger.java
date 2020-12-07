package com.quartz.scheduler.model;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class CronJobTrigger extends QuartzTrigger {

  private String cronExpression;

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  @Override
  protected Trigger buildTrigger() {

    return TriggerBuilder.newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule(cronExpression)
        )
        .withDescription(getClass().getSimpleName())
        .build();
  }

}
