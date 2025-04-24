package com.example.elasticlogdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${APP_NAME:default-app}")
    private String appName;

    @Value("${remote.service.url:spring-io.spring-io.svc.cluster.local}")
    private String remoteServiceUrl;

    private final RestTemplate restTemplate;

    // ✅ 생성자 주입 추가
    public IndexController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello")
    public String hello() {
        logger.info("📥 Received request to /");
        return "Hello from " + appName + "!";
    }

    @GetMapping("/")
    public String callSpringB() {
        if (!remoteServiceUrl.startsWith("http")) {
            remoteServiceUrl = "http://" + remoteServiceUrl;
        }
        String response = restTemplate.getForObject(remoteServiceUrl + "/hello", String.class);
        return "received: " + response;
    }
}
