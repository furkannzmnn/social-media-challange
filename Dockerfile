FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} social-media-challange-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/social-media-challange-0.0.1-SNAPSHOT.jar"]
