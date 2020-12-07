package com.quartz.scheduler.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type") 
@JsonSubTypes({
  @JsonSubTypes.Type(value = CronJobTrigger.class, name = "cron"),
  @JsonSubTypes.Type(value = FixedTimeJobTrigger.class, name = "fixed"),
  @JsonSubTypes.Type(value = RelativeTimeJobTrigger.class, name = "relative")
})
public abstract class QuartzTrigger {

  protected abstract Trigger buildTrigger();

  public static QuartzTrigger fromTrigger(Trigger trigger) {

    if (trigger.getDescription().equals(CronJobTrigger.class.getSimpleName())) {
      CronJobTrigger cronJobTrigger = new CronJobTrigger();
      cronJobTrigger.setCronExpression(((CronTrigger) trigger).getCronExpression());
      cronJobTrigger.buildTrigger();
      return cronJobTrigger;

    } else if (trigger.getDescription().equals(FixedTimeJobTrigger.class.getSimpleName())) {
      FixedTimeJobTrigger fixedTimeJobTrigger = new FixedTimeJobTrigger();
      fixedTimeJobTrigger.setWhen(trigger.getStartTime().toInstant());
      fixedTimeJobTrigger.buildTrigger();
      return fixedTimeJobTrigger;

    } else if (trigger.getDescription().equals(RelativeTimeJobTrigger.class.getSimpleName())) {
      RelativeTimeJobTrigger relativeTimeJobTrigger = new RelativeTimeJobTrigger();
      relativeTimeJobTrigger.setDuration(trigger.getStartTime().getTime() - Instant.now().toEpochMilli());
      relativeTimeJobTrigger.setUnit(ChronoUnit.MILLIS.toString());
      return relativeTimeJobTrigger;

    }
    return null;

  }
}
