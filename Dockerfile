# 1단계: Build Spring App
FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test
RUN ls -al /app/build/libs

# 2단계: Final Image with OTEL Agent
FROM openjdk:17-jdk-slim

# OTEL Agent 다운로드 (이미지 빌드 중)
ENV OTEL_VERSION=1.32.0
RUN curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_VERSION}/opentelemetry-javaagent.jar \
    -o /opentelemetry-javaagent.jar

# OTEL ENV
ENV JAVA_TOOL_OPTIONS="-javaagent:/opentelemetry-javaagent.jar"
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://146.56.177.240:32413
ENV OTEL_SERVICE_NAME=spring-app
ENV OTEL_TRACES_EXPORTER=otlp
ENV OTEL_METRICS_EXPORTER=none
ENV OTEL_LOGS_EXPORTER=none

VOLUME /tmp
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]