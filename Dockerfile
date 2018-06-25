FROM maven:3.5.3-jdk-10-slim as builder
LABEL maintainer="haphut@gmail.com"

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve-plugins dependency:resolve package
COPY . .
RUN mvn clean package shade:shade

FROM openjdk:10.0.1-10-jre-slim-sid
WORKDIR /app
COPY --from=builder \
  /app/target/pulsar-mqtt-source.jar \
  pulsar-mqtt-source.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/app/pulsar-mqtt-source.jar"]
