FROM openjdk:11-jdk-slim

COPY ./target/devicenator-*.jar ./target/devicenator.jar

ENTRYPOINT ["java", "-jar", "./target/devicenator.jar"]
