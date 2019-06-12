#FROM maven:3.5-jdk-8
FROM openjdk:8-jre-alpine
COPY target/backend-0.0.1-SNAPSHOT.jar /opt/backend-0.0.1-SNAPSHOT.jar
ENV SPRING_PROFILES=prod
CMD exec java -jar /opt/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=$SPRING_PROFILES
