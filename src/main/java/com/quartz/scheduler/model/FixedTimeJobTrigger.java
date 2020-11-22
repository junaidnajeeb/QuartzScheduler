package com.quartz.scheduler.model;

import java.time.Instant;
import java.util.Date;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class FixedTimeJobTrigger extends JobTrigger {

  private Instant when;

  public Instant getWhen() {
    return when;
  }

  public void setWhen(Instant when) {
    this.when = when;
  }

  @Override
  protected Trigger buildTrigger() {

    return TriggerBuilder.newTrigger().startAt(Date.from(getWhen())).build();
  }
  
}
