# Dokumentace projektu Útulek Zdravá Packa 🐾

Tento projekt je webová aplikace pro správu zvířecího útulku, postavená na technologii **Spring Boot** v Javě. Následující text vysvětluje, jak to celé funguje, aniž bychom se utopili v technických detailech.

---

## 🏗️ Jak je kód rozdělen (Architektura)

Kód není v jedné velké hromadě, ale je rozdělen do několika "šuplíků" (balíčků/package). Každý má svou jasnou roli:

### 1. Model (`dbspro2.utulek.model`)
Tady jsou definovány **"věci"**, se kterými pracujeme. Jsou to v podstatě tabulky v databázi přepsané do Javy.
*   **Zvire**: Jméno, plemeno, pohlaví, kdy bylo přijato.
*   **Zajemce**: Člověk, který si chce vzít zvíře (jméno, telefon, adresa).
*   **Adopce**: Propojení mezi zvířetem a zájemcem. Má svůj stav (Čeká, Schváleno, Zamítnuto).
*   **Uzivatel**: Lidé, kteří s aplikací pracují (Recepční, Veterinář, Admin).

### 2. Repository (`dbspro2.utulek.repository`)
Tohle jsou **"sklady"**. Každý model má svůj sklad.
*   V Javě jsou to jen rozhraní (interface). Nemusíme psát složité kódy pro hledání v databázi, Spring to udělá za nás.
*   Příklad: Když chceme najít všechna zvířata, zavoláme `zvireRepository.findAll()`.

### 3. Service (`dbspro2.utulek.service`)
Tady se nachází **"mozek"** aplikace. Probíhá tu veškerá logika.
*   Například když se schvaluje adopce, Service zařídí, aby se stav zvířete změnil na "Adoptováno" a stav žádosti na "Schváleno".
*   Kontroluje se zde, jestli je vše v pořádku, než se data uloží do skladu (Repository).

### 4. Controller (`dbspro2.utulek.controller`)
To jsou **"pošťáci"**. Starají se o to, co vidí uživatel v prohlížeči.
*   Když kliknete na tlačítko "Detail zvířete", prohlížeč pošle požadavek Controlleru.
*   Controller se zeptá Service na data, ta mu je podá, a Controller je "vstříkne" do HTML šablony, kterou vám zobrazí.

---

## 🔐 Zabezpečení (Kdo co může dělat)

V aplikaci jsou tři hlavní role, které určují, co kdo vidí:

1.  **Recepční**:
    *   Může prohlížet zvířata a zájemce.
    *   Může zakládat nové žádosti o adopci.
2.  **Veterinář**:
    *   Může navíc přidávat zvířata a zapisovat jim zdravotní záznamy.
3.  **Administrátor**:
    *   Může všechno, včetně správy uživatelů aplikace.

Všechno tohle hlídá soubor `SecurityConfig.java`. Pokud nejste přihlášení, aplikace vás nepustí dál než na přihlašovací stránku.

---

## 🚀 Jak se data dostanou do aplikace na začátku?

V souboru `DataInit.java` je připravený skript, který se spustí při každém startu aplikace. Pokud je databáze prázdná, automaticky tam nahraje:
*   Základní role (Admin, Recepce, Vet).
*   Ukázkové uživatele (např. `admin@example.com` s heslem `admin123`).
*   Seznam plemen, druhů zvířat a stavů.

---

## 🧩 Detailní rozbor kódu (Pro ty, co neví, která bije)

Pokud koukáš do souborů `.java` a vidíš tam spoustu zavináčů (`@`) a divných slov, tady je vysvětlení těch nejdůležitějších částí.

### 1. Model: `Zvire.java`
Tento soubor říká databázi: "Hele, takhle vypadá zvíře".

*   **`@Entity`**: Říká Javě: "Tohle není jen obyčejná třída, tohle je tabulka v databázi."
*   **`private Integer idZvire;`**: Každé zvíře musí mít své unikátní číslo (občanku), aby se nepletlo s jiným.
*   **`@ManyToOne`**: Tohle je propojka. Říká, že **mnoho** zvířat může mít **jedno** stejné plemeno (např. v útulku je 5 labradorů).
*   **Getter a Setter**: To jsou jen takové „dvířka“. Java je slušná, takže data nejsou jen tak pohozená, ale musíš se na ně zeptat (`get`) nebo je nastavit (`set`).

### 2. Repository: `ZvireRepository.java`
Tenhle soubor je skoro prázdný, ale děje se v něm nejvíc magie.

*   **`extends JpaRepository<Zvire, Integer>`**: Tímhle dáváme Springu vědět: "Vytvoř mi automaticky všechny funkce pro ukládání, mazání a hledání zvířat."
*   **`findByJmenoContainingIgnoreCase(...)`**: Tohle je vtipný trik. Jen podle názvu téhle funkce Spring pochopí, že má v databázi hledat zvířata podle jména a ignorovat velká/malá písmena. Nemusíme psát žádný kód!

### 3. Service: `ZvireService.java`
Tady jsou ty skutečné „instrukce“, co se má dít.

*   **`@Service`**: Označuje, že tady sedí "úředník", který má na starosti logiku.
*   **`@Transactional`**: (Najdeš v `AdopceService`) To je „pojistka“. Pokud se během složité operace (např. schvalování adopce) něco pokazí uprostřed, Spring všechno vrátí do původního stavu. Buď se povede všechno, nebo nic. Žádný nepořádek.

### 4. Controller: `ZvireController.java`
Tady se Java potkává s webem.

*   **`@GetMapping`**: "Když uživatel chce *vidět* tuhle stránku, udělej tohle."
*   **`@PostMapping`**: "Když uživatel klikne na tlačítko a *odesílá* formulář, udělej tohle."
*   **`Model model`**: To je takový „přepravka“. Do ní Controller naloží data (např. seznam zvířat) a pošle je do HTML šablony, aby se tam zobrazila.
*   **`"redirect:/zvirata"`**: Tohle prostě řekne prohlížeči: "Hotovo, teď mě pošli zpátky na seznam zvířat."

---

## 🛠️ Slovníček „divných“ pojmů

*   **Dependency Injection (Vstřikování závislostí)**: Zní to děsivě, ale je to jednoduché. Místo aby si každá třída musela sama složitě vyrábět všechno, co potřebuje (např. Service si vyrábět Repository), prostě si o to řekne v "konstruktoru" a Spring jí to tam dodá už hotové. Je to jako mít v kuchyni kuchaře, kterému ingredience rovnou podává pomocník pod ruku.
*   **Beans (Zrnka)**: To jsou objekty (třídy), o které se Spring stará od začátku do konce. Jsou to součástky v našem stroji.
*   **Lombok**: Možná uvidíš v jiných projektech `@Getter` nebo `@Setter` nad celou třídou. To jen šetří psaní, místo těch 100 řádků s kódem se tam napíše jedno slovo a Java to pochopí. (Tady v projektu jsou vypsané ručně, aby to bylo jasnější).
