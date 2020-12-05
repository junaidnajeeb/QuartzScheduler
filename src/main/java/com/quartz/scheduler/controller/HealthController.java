package com.quartz.scheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.quartz.scheduler.model.Health;

@RestController
@RequestMapping(path = "/health")
public class HealthController {

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Health> getHealth() {
    Health health = Health.create("good");
    return ResponseEntity.ok(health);
  }
}
