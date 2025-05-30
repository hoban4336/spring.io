package com.example.elasticlogdemo;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Value("${APP_NAME:trace-app}")
    private String appName;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
            .additionalInterceptors(new OpenTelemetryRestTemplateInterceptor(appName))
            .build();
    }

    public static class OpenTelemetryRestTemplateInterceptor implements ClientHttpRequestInterceptor {
        private final Tracer tracer;

        public OpenTelemetryRestTemplateInterceptor(String appName) {
            this.tracer = GlobalOpenTelemetry.getTracer(appName);
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            Span span = tracer.spanBuilder("Calling " + request.getURI()).startSpan();
            try (Scope scope = span.makeCurrent()) {
                return execution.execute(request, body);
            } finally {
                span.end();
            }
        }        
    }
}