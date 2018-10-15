FROM openjdk:8-jdk
VOLUME /tmp
ADD target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]