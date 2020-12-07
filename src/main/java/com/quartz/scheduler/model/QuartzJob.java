package com.quartz.scheduler.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Trigger;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = ConsoleJob.class, name = "console"),
    @JsonSubTypes.Type(value = HttpJob.class, name = "http"),
    @JsonSubTypes.Type(value = SMSJob.class, name = "sms")
  }
)
public abstract class QuartzJob implements Job, Runnable {

  private static final String ID_DATAMAP_KEY = "id";
  private static final String NAME_DATAMAP_KEY = "name";

  protected UUID id;
  protected String group;
  protected String name;
  protected QuartzTrigger jobTrigger;
  protected JobExecutionContext jobExecutionContext;

  protected abstract Map<String, Object> buildDataMap();


  public QuartzJob() {
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


  public QuartzTrigger getJobTrigger() {
    return jobTrigger;
  }

  public void setJobTrigger(QuartzTrigger jobTrigger) {
    this.jobTrigger = jobTrigger;
  }

  public Trigger buildTrigger() {
    return this.getJobTrigger().buildTrigger();
  }

  public JobDetail buildJobDetail() {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put(ID_DATAMAP_KEY, id.toString());
    dataMap.put(NAME_DATAMAP_KEY, name.toString());
    dataMap.putAll(buildDataMap());

    return org.quartz.JobBuilder.newJob(getClass()).withIdentity(name, group).usingJobData(dataMap)
        .build();
  }


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    this.jobExecutionContext = context;
    run();
  }

  public static QuartzJob fromJobDetail(JobDetail jobDetail) {

    try {

      QuartzJob job = (QuartzJob) jobDetail.getJobClass().getDeclaredConstructor().newInstance();
      JobKey jobKey = jobDetail.getKey();
      job.setId(UUID.fromString((String) jobDetail.getJobDataMap().remove(ID_DATAMAP_KEY)));
      job.setName(jobKey.getName());
      job.setGroup(jobKey.getGroup());

      job.initJobFromDataMap(jobDetail.getJobDataMap());

      return job;
    } catch (InstantiationException | IllegalArgumentException | NoSuchMethodException  e) {
      //throw Throwables.propagate(e);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  protected abstract void initJobFromDataMap(Map<String, Object> dataMap);
}
