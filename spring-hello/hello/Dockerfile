# Buildivaihe (Java Development Kit, kääntäjä)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Paketointivaihe (Java Runtime Environment, suoritusympäristö)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Buildausohjeet:
# docker build -t spring-hello .
# Kontin ajaminen:
# docker run -p 8080:8080 spring-hello
# Sovelluksen testaaminen selaimessa:
# http://localhost:8080/