# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o settings.xml com credenciais (minha maquina)
COPY settings.xml /root/.m2/settings.xml

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/facilit-kanban-*.jar app.jar
EXPOSE 8043
ENTRYPOINT ["java","-jar","/app/app.jar"]
