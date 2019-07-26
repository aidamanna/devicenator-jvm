FROM maven:3.6.1-jdk-11-slim as maven

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn package

FROM openjdk:11-jdk-slim

COPY --from=maven ./target/devicenator-*.jar ./target/devicenator.jar

ENTRYPOINT ["java", "-jar", "./target/devicenator.jar"]
