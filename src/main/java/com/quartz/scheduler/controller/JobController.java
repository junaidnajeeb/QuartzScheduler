package com.quartz.scheduler.controller;

import java.util.Map;
import java.util.Set;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.quartz.scheduler.exception.DuplicateJobKeyException;
import com.quartz.scheduler.model.QuartzJob;
import com.quartz.scheduler.model.QuartzJobKey;
import com.quartz.scheduler.service.SchedulerService;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping(path = "/scheduler")
public class JobController {

  private final SchedulerService schedulerService;

  @Autowired
  public JobController(SchedulerService schedulerService) {
    this.schedulerService = schedulerService;
  }


  @RequestMapping(value = "/createJob/{group}", method = RequestMethod.POST)
  public ResponseEntity<Object> addJob(@PathVariable String group, @RequestBody QuartzJob job)
      throws DuplicateJobKeyException {

    try {
      job.setGroup(group);
      schedulerService.scheduleJob(job);
      return ResponseEntity.ok(job);
    } catch (DuplicateJobKeyException duplicateException) {

      return new ResponseEntity<>(duplicateException, new HttpHeaders(),
          HttpStatus.PRECONDITION_FAILED);
    }
  }


  @RequestMapping(value = "/jobKeys", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Set<JobKey>>> getAllJobkeys() {
    return ResponseEntity.ok(schedulerService.getAllJobkeys());
  }


  @RequestMapping(value = "/jobKeys/group/{group}", method = RequestMethod.GET)
  public ResponseEntity<Set<JobKey>> getJobkeysByGroup(@PathVariable String group) {
    return ResponseEntity.ok(schedulerService.getJobKeysByGroup(group));
  }
  
  @RequestMapping(value = "/jobDetails/group/{group}/job/{name}", method = RequestMethod.GET)
  public ResponseEntity<Object> getJobDetails(@PathVariable String group, @PathVariable String name) {

    return new ResponseEntity<>("", new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED);

  }


  @RequestMapping(value = "/group/{group}/job/{name}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteJob(@PathVariable String group, @PathVariable String name) {

    if (!schedulerService.deleteJob(group, name)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }



}
