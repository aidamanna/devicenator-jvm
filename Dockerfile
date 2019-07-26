FROM maven:3.6.1-jdk-11-slim

WORKDIR /usr/src/devicenator

COPY pom.xml /usr/src/devicenator/
RUN mvn dependency:go-offline

COPY src/ /usr/src/devicenator/src/

EXPOSE 8080

ENTRYPOINT ["mvn", "spring-boot:run"]
