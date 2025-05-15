# Use OpenJDK as the base image
# FROM openjdk:11-jre-slim
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built .jar file into the container
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose the port the API Gateway will run on
EXPOSE 8084

# Run the API Gateway
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
