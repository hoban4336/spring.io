package com.example.elasticlogdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class IndexController {
    
    @Value("${APP_NAME:default-app}")
    private String appName;

    @Value("${remote.service.url:spring-io.spring-io.svc.cluster.local}")
    private String remoteServiceUrl;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/hello")
    public String hello() {
        logger.info("📥 Received request to /");
        return "Hello from " + appName + "!";
    }

    private final RestTemplate restTemplate;
    @GetMapping("/")
    public String callSpringB() {
        if (!remoteServiceUrl.startsWith("http")) {
            remoteServiceUrl = "http://" + remoteServiceUrl;
        }
        String response = restTemplate.getForObject(remoteServiceUrl + "/hello", String.class);
        return "received: " + response;
    }    
}
