package com.quartz.scheduler.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

public class HttpJob extends QuartzJob {

  private static final Logger logger = LoggerFactory.getLogger(HttpJob.class);

  public static final String URL_DATAMAP_KEY = "url";
  public static final String METHOD_DATAMAP_KEY = "method";
  public static final String BODY_DATAMAP_KEY = "body";
  public static final String HEADERS_JSON_DATAMAP_KEY = "headersJson";

  private URL url;
  private HttpMethod method = HttpMethod.POST;
  private String body;
  private Map<String, String> headers = Collections.emptyMap();
  private ObjectMapper jsonMapper = new ObjectMapper();


  @Autowired
  RestTemplate restTemplate;


  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }


  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }


  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }


  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  @Override
  protected Map<String, Object> buildDataMap() {
    Map<String, Object> jobDataMap = new HashMap<String, Object>();
    jobDataMap.put(URL_DATAMAP_KEY, url.toString());
    jobDataMap.put(METHOD_DATAMAP_KEY, method.toString());
    if (body != null) {
      jobDataMap.put(BODY_DATAMAP_KEY, body);
    }

    try {
      jobDataMap.put(HEADERS_JSON_DATAMAP_KEY, jsonMapper.writeValueAsString(headers));
    } catch (JsonProcessingException e) {
      throw new ServiceException(e.getMessage());
    }

    return jobDataMap;

  }
  
  @Override
  public void run() {
    HttpHeaders httpHeaders = new HttpHeaders();
    headers.forEach(httpHeaders::add);
    
    HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<String> response =
        restTemplate.exchange(url.toString(), method, request, String.class);
    
    int code = response.getStatusCode().value();
    String responseBody = response.getBody();
    logger.info("{}", new HttpAuditRecord(this, code, responseBody));

  }
  
  
  private static class HttpAuditRecord {

    private final HttpJob request;
    private final int responseCode;
    private final String responseBody;

    private HttpAuditRecord(
        HttpJob request,
        int responseCode,
        String responseBody) {
      this.request = request;
      this.responseCode = responseCode;
      this.responseBody = responseBody;
    }

    @Override
    public String toString() {
      return "HttpAuditRecord [request=" + request + ", responseCode=" + responseCode
          + ", responseBody=" + responseBody + "]";
    }
  }


  @Override
  protected void initJobFromDataMap(Map<String, Object> dataMap) {

    try {
      url = new URL((String) dataMap.get(URL_DATAMAP_KEY));
    } catch (MalformedURLException e) {
      throw new ServiceException(e.getMessage());
    }
    method = HttpMethod.valueOf((String) dataMap.get(METHOD_DATAMAP_KEY));
    body = (String) dataMap.get(BODY_DATAMAP_KEY);
    setHeadersJson((String) dataMap.get(HEADERS_JSON_DATAMAP_KEY));
  }


  @SuppressWarnings("unchecked")
  @JsonIgnore
  public void setHeadersJson(String json) {
    try {
      headers = jsonMapper.readValue(json, Map.class);
    } catch (IOException e) {
      throw new ServiceException(e.getMessage());
    }
  }

}
