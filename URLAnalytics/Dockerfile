# Use OpenJDK as the base image
# FROM openjdk:11-jre-slim
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built .jar file into the container
COPY target/URLAnalytics-0.0.1-SNAPSHOT.jar url-analytics.jar

# Expose the port the service will run on
EXPOSE 8082

# Run the service
ENTRYPOINT ["java", "-jar", "url-analytics.jar"]
