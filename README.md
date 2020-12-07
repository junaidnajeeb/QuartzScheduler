# QuartzScheduler

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Jersey](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jersey)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)



### BUILD JAVA
```
 mvn clean package
```

### BUILD DOCKER IMAGE
```
docker build -t quartz-app .
```

### RUN DETACTED CONTAINER WITH ALL CONFIG PASSED 
```
docker run -d -p 8090:8080  -e SPRING_PROFILES_ACTIVE=dev -e AUTHOR_NAME=Junaid -e SPRING_DATASOURCE_PASSWORD=PASSWORD -e SPRING_DATASOURCE_USERNAME=USER_NAME -e SPRING_DATASOURCE_URL=jdbc:mysql://HOST:PORT/DB_NAME -e TWILIO_ACCOUNT_TOKEN=TOKEN_FOR_SMS -e TWILIO_ACCOUNT_ID=IS_USED_FOR_SMS_JOB quartz-app .
```

### AVAILABLE PROFILES
dev
stg


### SWAGGER JSON
```
http://localhost:8080/v2/api-docs
```

### SWAGGER UI
```
http://localhost:8080/swagger-ui.html#
```


### APIs

#### Health Endpoint
```
GET /health HTTP/1.1
Host: localhost:8080
```

#### Get all groups with job name list Endpoint
```
GET /scheduler/jobGroupKeys HTTP/1.1
Host: localhost:8080
```


#### Console Job with Relative Trigger
```
POST /scheduler/createJob/group_1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "type" : "console",
    "name": "group_1:console",
    "message": "----> Coffee Please",
    "jobTrigger" : {
        "duration" : "5",
        "unit" : "SECONDS",
        "type" : "relative"
    }
}
```

#### Console Job with Fixed Trigger (Time in UTC)
```
POST /scheduler/createJob/group_1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "type" : "console",
    "name": "group_1:console",
    "message": "----> Coffee Please",
    "jobTrigger" : {
        "when" : "2020-11-22T07:45:00Z",
        "type" : "fixed"
    }
}
```

#### Console Job with Cron Expression
see http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html for how to create cron string
```
POST /scheduler/createJob/group_1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "type" : "console",
    "name": "group_2:console",
    "message": "----> Coffee Please every 5 mins",
    "jobTrigger" : {
        "cronExpression" : "0 0/5 * * * ?",
        "type" : "cron"
    }
}
```

#### Http Job POST with relative Expression
```
POST /scheduler/createJob/group_1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "type" : "http",
    "name": "group_2:http8",
    "method": "POST",
    "headers": {"Content-Type": "application/json"},
    "body": "{\"field\":\"value\"}",
    "url": "http://httpbin.org/post",
    "jobTrigger" : {
        "duration" : 10,
        "unit" : "Seconds",
        "type" : "relative"
    }
}
```

#### Http Job GET with relative Expression
```
POST /scheduler/createJob/group_1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "type" : "http",
    "name": "group_2:http8",
    "method": "GET",
    "headers": {"Content-Type": "application/json"},
    "url": "http://httpbin.org/get",
    "jobTrigger" : {
        "duration" : 10,
        "unit" : "Seconds",
        "type" : "relative"
    }
}
```

#### GET job details
```
GET /scheduler/jobDetails/group/group_sms/job/MessageToMe HTTP/1.1
Host: localhost:8080
{
    "type" : "http",
    "name": "group_2:http8",
    "method": "GET",
    "headers": {"Content-Type": "application/json"},
    "url": "http://httpbin.org/get",
    "jobTrigger" : {
        "duration" : 10,
        "unit" : "Seconds",
        "type" : "relative"
    }
}
```


```
POST /scheduler/createJob/group_sms HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "type" : "sms",
    "name": "MessageToMe",
    "message": "Hello Junaid",
    "phoneNumber": "+1222111000",
    "jobTrigger" : {
        "duration" : 10,
        "unit" : "Seconds",
        "type" : "relative"
    }
}
```