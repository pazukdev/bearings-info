#pull base image
FROM openjdk:8-jre-alpine

#maintainer (me ofcourse)
MAINTAINER pazukdev@gmail.com

# Make port 8080 available to the world outside this container
# EXPOSE 8080

COPY target/backend-0.0.1-SNAPSHOT.jar /opt/backend-0.0.1-SNAPSHOT.jar

RUN mkdir /opt/dir

ENV SPRING_PROFILES=prod

RUN mvn clean install -DskipTests

CMD exec java -jar /opt/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=$SPRING_PROFILES
