FROM openjdk:8-jdk-alpine

LABEL maintainer="lukasz.jambor83@gmail.com"

ADD entity/target/entity-0.0.1-SNAPSHOT.jar demo.jar
ADD dto/target/dto-0.0.1-SNAPSHOT.jar demo.jar
ADD api/target/api-0.0.1-SNAPSHOT.jar demo.jar
ADD administration/target/administration-0.0.1-SNAPSHOT.jar demo.jar
ADD main/target/main-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.jar"]