FROM openjdk:17-jdk
ADD main/target/main-0.0.1-SNAPSHOT.jar main-app.jar
EXPOSE 9080 9081
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:9081,server=y,suspend=n
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /main-app.jar" ]