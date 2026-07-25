# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM maven:3-eclipse-temurin-26 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render injeta a variavel PORT em runtime; application.yml ja le PORT com fallback.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
