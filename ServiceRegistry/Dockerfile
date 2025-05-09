# Use OpenJDK as the base image
# FROM openjdk:11-jre-slim
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built .jar file into the container
COPY target/ServiceRegistry-0.0.1-SNAPSHOT.jar service-registry.jar

# Expose the port Eureka will run on
EXPOSE 8761

# Run the Eureka service
ENTRYPOINT ["java", "-jar", "service-registry.jar"]
