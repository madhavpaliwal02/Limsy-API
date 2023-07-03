# #FROM eclipse-temurin:17-jdk-alpine
# #VOLUME /tmp
# #ADD target/microservices_librarian-0.0.1-SNAPSHOT.jar microservices_librarian-0.0.1-SNAPSHOT.jar
# #ENTRYPOINT ["java","-jar","microservices_librarian-0.0.1-SNAPSHOT.jar"]


# # Use the official OpenJDK image as the base image
# FROM eclipse-temurin:17-jdk-alpine

# # Set the working directory in the container
# WORKDIR /app

# # Copy the JAR file into the container
# COPY target/microservices_librarian-0.0.1-SNAPSHOT.jar app.jar

# # Expose the port that your Spring Boot application listens on
# EXPOSE 8101
# # EXPOSE 5432

# # Set the command to run your Spring Boot application
# CMD ["java", "-jar", "app.jar"]

# Use the official OpenJDK image as the base image
FROM ubuntu:18.04

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar

RUN apt-get update && apt install openjdk-17-jdk openjdk-17-jre -y && apt install maven -y
# Expose the port that your Spring Boot application listens on
EXPOSE 8101

# Set the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]