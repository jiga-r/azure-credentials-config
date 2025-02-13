# Stage 1: Build the application
FROM gradle:8.12-jdk-21-and-23 AS builder

# Set the working directory
WORKDIR /app

# Copy the source code
COPY . .

# Build the application
RUN chmod +x gradlew && gradle clean build --no-daemon

# Stage 2: Create the final image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]