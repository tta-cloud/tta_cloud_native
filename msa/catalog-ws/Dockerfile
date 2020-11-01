FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/catalog-ws-0.2.jar catalog-service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:2.0:/dev/./urandom", "-jar", "catalog-service.jar"]