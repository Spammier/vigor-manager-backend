# Stage 1: Build with Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy settings.xml from the build context to the image
COPY settings.xml /tmp/settings.xml

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -s /tmp/settings.xml dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Package the application, using the cache
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -s /tmp/settings.xml clean package -DskipTests

# Stage 2: Run the application using JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"] 