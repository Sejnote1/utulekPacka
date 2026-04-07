# ==========================================
# FÁZE 1: Sestavení (Builder)
# ==========================================
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /build

# KROK A: Optimalizace mezipaměti (Cache)
# Zkopírujeme nejprve pouze pom.xml. Docker si tuto vrstvu uloží do paměti.
COPY pom.xml .
# Stáhneme všechny závislosti. Pokud se pom.xml nezmění, Docker tento krok 
# při dalším sestavování přeskočí a obrovsky tím zrychlí build.
RUN mvn dependency:go-offline

# KROK B: Sestavení aplikace
# Až nyní zkopírujeme samotné zdrojové kódy a sestavíme JAR.
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# FÁZE 2: Spuštění (Runtime)
# ==========================================
# Použijeme menší obraz pouze s JRE (Java Runtime Environment), nepotřebujeme celý Maven.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# KROK C: Bezpečnost - Non-root uživatel
# Z hlediska bezpečnosti by aplikace v kontejneru neměla běžet jako administrátor (root).
# Vytvoříme běžného uživatele "spring" a přepneme se na něj.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# KROK D: Zkopírování výsledku
# Vezmeme hotový JAR soubor z Fáze 1 (builder) a zkopírujeme ho do tohoto čistého obrazu.
COPY --from=builder /build/target/*.jar app.jar

# Informace o portu
EXPOSE 8080

# Spuštění aplikace
ENTRYPOINT ["java", "-jar", "app.jar"]