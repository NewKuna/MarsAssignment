# Use an official Maven image as the base image
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/demo.jar .
ARG CONFIG_ENV=sit
COPY config/$CONFIG_ENV/ /app/config/
CMD ["java", "-jar", "-Dspring.profiles.active=default", "-Dspring.config.location=/app/config/", "demo.jar"]
