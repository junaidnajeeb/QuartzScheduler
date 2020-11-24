package com.quartz.scheduler.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.quartz.scheduler.exception.DuplicateJobKeyException;
import com.quartz.scheduler.model.BaseJob;
import com.quartz.scheduler.service.SchedulerService;

@RestController
@RequestMapping(path = "/scheduler")
public class JobController {
  
  private final SchedulerService schedulerService;

  @Autowired
  public JobController(SchedulerService schedulerService) {
    this.schedulerService = schedulerService;
  }


  @RequestMapping(value = "/createJob/{group}", method = RequestMethod.POST)
  public ResponseEntity<BaseJob> addJob(@PathVariable String group, @RequestBody BaseJob job) throws DuplicateJobKeyException{
    job.setGroup(group);
    
    schedulerService.scheduleJob(job);

    return ResponseEntity.ok(job);
  }
  
  
  @RequestMapping(value = "/jobs", method = RequestMethod.GET)
  public Set<String> getJobKeys() {
    return schedulerService.getJobKeys();
  }
  

  

}
