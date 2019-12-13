# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine

VOLUME /tmp

EXPOSE 8080

# Add the application's jar to the container
COPY /target/loan-planner-*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]