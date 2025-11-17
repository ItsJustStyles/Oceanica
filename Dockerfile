# Etapa 1: Construir el JAR
FROM maven:3.9.6-eclipse-temurin-24 AS build
WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar el proyecto
RUN mvn -q -e -DskipTests package

# Etapa 2: Ejecutar el servidor
FROM eclipse-temurin:24-jre
WORKDIR /app

# Copiar el jar construido
COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto mediante $PORT
ENV PORT=8080

EXPOSE 8080

# Ejecutar la aplicación
CMD ["sh", "-c", "java -jar app.jar"]
