package com.quartz.scheduler.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.quartz.scheduler.model.Health;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/health")
@Api(value = "Health Controller - Returns health of the system", produces = MediaType.APPLICATION_JSON_VALUE )
public class HealthController {

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Returns health of the system ", response = Health.class)
  public ResponseEntity<Health> getHealth() {
    Health health = Health.create("good");
    return ResponseEntity.ok(health);
  }
}
