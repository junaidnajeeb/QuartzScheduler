package com.quartz.scheduler.model;

import org.quartz.JobKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class QuartzJobKey {

  public abstract String getName();

  public abstract String getGroup();

  @JsonCreator
  public static QuartzJobKey create(String group, String name) {
    return new AutoValue_QuartzJobKey(group, name);
  }

  public JobKey buildQuartzJobKey() {
    return new JobKey(getName(), getGroup());
  }

  public static QuartzJobKey fromQuartzJobKey(JobKey key) {
    return QuartzJobKey.create(key.getGroup(), key.getName());
  }

}
