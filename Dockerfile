FROM eclipse-temurin:24-jdk

WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Damos permiso al wrapper de Maven (mvnw)
RUN chmod +x mvnw

# Compilamos sin tests
RUN ./mvnw -e -X -DskipTests clean package

# Ejecutamos la aplicación
CMD ["java", "--enable-preview", "-jar", "target/Oceanica-1.0-SNAPSHOT.jar"]
