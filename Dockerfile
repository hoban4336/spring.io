FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test
RUN ls -al /app/build/libs

FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]