# Útulek Zdravá Packa

Informační systém pro správu zvířat a adopcí v útulku. Architektura je postavena jako monolitická webová aplikace na jazyku **Java (Spring Boot)**, renderování šablon přes **Thymeleaf** a s databází **PostgreSQL**.

## 🛠 Použité technologie
- **Backend:** Java 17, Spring Boot 3.2.5 (Web, Data JPA, Security, Validation)
- **Frontend:** HTML šablony generované na serveru přes Thymeleaf
- **Databáze:** PostgreSQL s automatickou správou databázových tabulek skrze Hibernate (JPA)
- **Správa závislostí a build:** Maven
- **Infrastruktura:** Docker & Docker Compose pro rychlé nasazení databáze
- **Nástroje:** Lombok (pro redukci boilerplate kódu u entit a DTO)

## 📂 Architektura a struktura projektu
Aplikace využívá standardní vícevrstvou (N-tier) architekturu typickou pro Spring:

- `controller/` – **Prezentační vrstva:** Přijímá HTTP požadavky od uživatele, volá service vrstvu a vrací příslušné HTML (Thymeleaf) stránky (např. `ZvireController`, `AdopceController`).
- `service/` – **Logická vrstva:** Obsahuje jádro byznys logiky, provádění transakčních úkonů a zprostředkovává komunikaci mezi databází a aplikací.
- `repository/` – **Přístupová vrstva:** Rozhraní napojená na Spring Data JPA poskytující vestavěné metody k rychlým manipulacím s databází (CRUD).
- `model/` – **Datové entity:** Java třídy (např. `Zvire`, `Adopce`), namapované přes anotace přímo na relační tabulky v databázi. 
- `security/` – **Zabezpečení:** Konfigurace Spring Security spravující hesla, přihlášení uživatelů a zabezpečení koncových bodů.

## 🐾 Hlavní datový model
Agenda je rozdělena na tři zásadní části, které spolu přímo komunikují.

1. **Zvířata a jejich stav:**
   - **`Zvire`**: Hlavní pilíř struktury (eviduje jméno, věk, povahu atd.).
   - Modul zvířat je dovybaven doprovodnými číselníky `Plemeno`, `Druh` a `StatusZvirete` (tj. k adopci/léčení/rezervováno).
   - Vazba 1:N je uplatněna ve `VeterinarniZaznam`, který uchovává informace o lékařských prohlídkách zvířete.

2. **Adopční oddělení:**
   - **`Zajemce`**: Profil konkrétní fyzické osoby usilující o adopci.
   - **`Adopce`**: Křižovatka mezi `Zvire` a `Zajemce`. Reprezentuje celý průběh adopčního řízení.
   - Odmítnuté adopce jsou řízeny přes `AdopceZamitnuti` s popisem přes `DuvodZamitnuti`.

3. **Uživatelská a administrátorská část:**
   - Slouží k chodu útulku (zaměstnanci a administrátoři – entity `Uzivatel` a `Role`).

## 🚀 Jak aplikaci spustit

### 1. Požadavky
- **Java 17+**
- **Docker** (pro lokální natáhnutí databáze bez instalování Postgresu na OS)

### 2. Spuštění databáze
Pro lokální vývoj je připraven Docker konfigurák definující databázový stroj s vytvořením schématu, viz `docker-compose.yml`.
```bash
docker-compose up -d
```
Lokální PG databáze po tomto příkazu ihned poběží na portu `5432`.

### 3. Spuštění přes Maven wrapper
Není nutné instalovat Maven na úrovni operačního systému, spusťte aplikaci následujícími skripty přímo v rootu projektu:

**Na Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**Na Mac/Linux:**
```bash
./mvnw spring-boot:run
```
Při prvním náběhu si aplikace přes JPA vygeneruje/aktualizuje z entit databázové tabulky automaticky díky `spring.jpa.hibernate.ddl-auto=update`.  
Frontend systému je poté k dispozici na adrese `http://localhost:8080`.
