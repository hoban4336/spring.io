FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=builder /app/build/libs/*.jar /app/
ENTRYPOINT ["java","-jar","/app/*.jar"]