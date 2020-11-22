package com.adobe.xad.quartz.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.util.StringUtils;

public class RelativeTimeJobTrigger extends JobTrigger {

  private String unit;
  private Long duration;

  public String getUnit() {
    return unit;
  }


  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  @Override
  protected Trigger buildTrigger() {
    
    
    return TriggerBuilder.newTrigger()
        .startAt(
            Date.from(Instant.now()
            .plus(duration, 
                ChronoUnit.valueOf(StringUtils.capitalize(getUnit()))
                )
            )
        )
        .build();
  }

}
