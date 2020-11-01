FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/orders-ws-0.2.jar orders-service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "orders-service.jar"]