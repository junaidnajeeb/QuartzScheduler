package com.quartz.scheduler.model;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = ConsoleJob.class, name = "console")})
public abstract class BaseJob implements Job, Runnable {

  private static final String ID_DATAMAP_KEY = "id";
  private static final String NAME_DATAMAP_KEY = "name";

  protected UUID id;
  protected String group;
  protected String name;
  protected JobTrigger jobTrigger;
  protected JobExecutionContext jobExecutionContext;

  protected abstract Map<String, Object> buildDataMap();



  public BaseJob() {
    id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }


  public JobTrigger getJobTrigger() {
    return jobTrigger;
  }

  public void setJobTrigger(JobTrigger jobTrigger) {
    this.jobTrigger = jobTrigger;
  }

  public Trigger buildQuartzFixedTimeTrigger(Instant startTIme) {
    return TriggerBuilder.newTrigger().startAt(Date.from(startTIme)).build();
  }
  
  public Trigger buildTrigger() {

      return this.getJobTrigger().buildTrigger();
  }

  public JobDetail buildQuartzJobDetail() {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put(ID_DATAMAP_KEY, id.toString());
    dataMap.put(NAME_DATAMAP_KEY, name.toString());
    dataMap.putAll(buildDataMap());

    return org.quartz.JobBuilder.newJob(getClass()).withIdentity(name, group).usingJobData(dataMap).build();
  }


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    this.jobExecutionContext = context;
    run();
  }
}
