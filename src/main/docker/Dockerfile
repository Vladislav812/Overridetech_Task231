#FROM amazoncorretto:11
#ARG JAR_FILE=./src/main/docker/task231-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} application.jar
#ENTRYPOINT ["java", "-jar", "application.jar"]

FROM maven:3.9.6-amazoncorretto-11 AS build
RUN mkdir -p /maindir
WORKDIR /maindir
COPY /pom.xml /maindir
COPY /src /maindir/src
RUN mvn clean package -B --file ./pom.xml -DskipTests=true


FROM amazoncorretto:11
COPY --from=build /target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]