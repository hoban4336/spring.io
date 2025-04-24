
@Configuration
public class RestTemplateConfig {

    @Value("${APP_NAME:trace-app}")
    private String appName;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
            .additionalInterceptors(new OpenTelemetryRestTemplateInterceptor())
            .build();
    }

    public static class OpenTelemetryRestTemplateInterceptor implements ClientHttpRequestInterceptor {
        private final Tracer tracer = GlobalOpenTelemetry.getTracer(appName);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            Span span = tracer.spanBuilder("Calling " + request.getURI().toString()).startSpan();
            try (Scope scope = span.makeCurrent()) {
                return execution.execute(request, body);
            } finally {
                span.end();
            }
        }
    }
}