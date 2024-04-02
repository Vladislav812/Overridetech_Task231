#FROM amazoncorretto:11
#ARG JAR_FILE=./src/main/docker/task231-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} application.jar
#ENTRYPOINT ["java", "-jar", "application.jar"]

FROM maven:4.0.0-amazoncorretto-11 AS build
RUN mvn clean package -B --file pom.xml -DskipTests=true
RUN ls


FROM amazoncorretto:11
COPY ./target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]