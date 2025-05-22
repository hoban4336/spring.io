package com.example.elasticlogdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // @Value("${APP_NAME:trace-app}")
    // private String appName;

    // @Bean
    // public RestTemplate restTemplate() {
    //     return new RestTemplateBuilder()
    //         .additionalInterceptors(new OpenTelemetryRestTemplateInterceptor(appName))
    //         .build();
    // }

    // public static class OpenTelemetryRestTemplateInterceptor implements ClientHttpRequestInterceptor {
    //     private final Tracer tracer;

    //     public OpenTelemetryRestTemplateInterceptor(String appName) {
    //         this.tracer = GlobalOpenTelemetry.getTracer(appName);
    //     }

    //     @Override
    //     public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    //         Span span = tracer.spanBuilder("Calling " + request.getURI()).startSpan();
    //         try (Scope scope = span.makeCurrent()) {
    //             return execution.execute(request, body);
    //         } finally {
    //             span.end();
    //         }
    //     }        
    // }
}