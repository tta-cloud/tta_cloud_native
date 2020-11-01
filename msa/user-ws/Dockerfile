FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/users-ws-0.1.jar users-service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "users-service.jar"]