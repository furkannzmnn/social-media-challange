FROM openjdk:11
FROM maven:alpine

WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} social-media-challange-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/social-media-challange-0.0.1-SNAPSHOT.jar"]
