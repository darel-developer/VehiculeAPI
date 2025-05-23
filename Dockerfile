# Étape 1 : builder avec Maven
FROM maven:3.9-eclipse-temurin-17 AS build

# Copie le code source dans le conteneur
WORKDIR /app
COPY . .

# Compile le projet et génère le .jar
RUN ./mvnw clean package -DskipTests

# Étape 2 : image finale avec juste le JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copie le JAR depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
