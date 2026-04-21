# Použijeme menší obraz pouze s JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Non-root uživatel
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Zkopírujeme lokálně sestavený JAR
COPY target/Utulek-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]