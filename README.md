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
docker build -t greeting-app .
```

### RUN DETACTED CONTAINER WITH ALL CONFIG PASSED 
```
docker run -d -p 8090:8080  -e SPRING_PROFILES_ACTIVE=dev -e AUTHOR_NAME=Junaid -e SPRING_DATASOURCE_PASSWORD=PASSWORD -e SPRING_DATASOURCE_USERNAME=USER_NAME -e SPRING_DATASOURCE_URL=jdbc:mysql://HOST:PORT/DB_NAME  greeting-app .
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