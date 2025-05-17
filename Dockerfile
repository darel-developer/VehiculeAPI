FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/vehicleapi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/vehicleapi-0.0.1-SNAPSHOT.jar"]
