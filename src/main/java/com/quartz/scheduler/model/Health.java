package com.quartz.scheduler.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Health {

  public abstract String getStatus();

  public static Health create(String status) {
    return new AutoValue_Health(status);
  }

}
