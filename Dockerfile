FROM amazoncorretto:24-alpine-jdk
MAINTAINER Oceanica
COPY target/Oceanica-1.0-SNAPSHOT.jar   oceanica-app.jar
ENTRYPOINT ["java", "-jar", "/oceanica-app.jar"]