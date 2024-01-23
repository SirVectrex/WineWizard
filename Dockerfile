# Start with a base image containing Java runtime (Java 17)
FROM openjdk:17-jdk-slim

# The application's jar file
ARG JAR_FILE=target/WineWizard-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} WineWizard.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/WineWizard.jar"]