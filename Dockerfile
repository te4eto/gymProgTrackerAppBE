# Multi-stage build for Gradle
FROM gradle:8.8-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]