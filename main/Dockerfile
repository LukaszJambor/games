FROM openjdk:8-jdk-alpine
ADD target/main-0.0.1-SNAPSHOT.jar main-app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /main-app.jar" ]