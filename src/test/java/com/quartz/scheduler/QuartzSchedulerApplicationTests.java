package com.quartz.scheduler;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = QuartzSchedulerApplication.class, webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@WebAppConfiguration
class QuartzSchedulerApplicationTests {


}
