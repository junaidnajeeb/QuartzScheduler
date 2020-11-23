package com.quartz.scheduler.model;

import org.quartz.Trigger;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type") 
@JsonSubTypes({
  @JsonSubTypes.Type(value = FixedTimeJobTrigger.class, name = "fixed"),
  @JsonSubTypes.Type(value = RelativeTimeJobTrigger.class, name = "relative"),
  @JsonSubTypes.Type(value = CronJobTrigger.class, name = "cron")
})
public abstract class JobTrigger {
  
  protected abstract Trigger buildTrigger();
}
