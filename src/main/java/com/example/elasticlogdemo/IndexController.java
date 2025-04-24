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

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/hello")
    public String hello() {
        logger.info("📥 Received request to /");
        return "Hello from " + appName + "!";
    }
}
