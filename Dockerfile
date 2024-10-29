FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar desafio-logistica-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "desafio-logistica-0.0.1-SNAPSHOT.jar"]