# 1단계: Build Spring App
FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# OTEL Agent 다운로드
ENV OTEL_VERSION=1.32.0
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_VERSION}/opentelemetry-javaagent.jar \
         -O /opentelemetry-javaagent.jar && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 2단계: Final Image with OTEL Agent
FROM openjdk:17-jdk-slim
VOLUME /tmp

COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

# OTEL ENV
ENV JAVA_TOOL_OPTIONS="-javaagent:/opentelemetry-javaagent.jar"
ENV OTEL_JAVAAGENT_DEBUG=true
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://tempo-distributor.observability.svc.cluster.local:4318
ENV OTEL_SERVICE_NAME=spring-app
ENV OTEL_TRACES_EXPORTER=otlp
ENV OTEL_METRICS_EXPORTER=otlp
ENV OTEL_LOGS_EXPORTER=none
ENV OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf

ENTRYPOINT ["java", "-jar", "/app.jar"]