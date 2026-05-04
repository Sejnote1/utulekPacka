-- ==============================================
-- 1. POHLEDY (VIEWS)
-- ==============================================

-- Pohled 1: Zvířata dostupná k adopci (zkrácený výpis pro rychlejší webový load)
CREATE OR REPLACE VIEW v_zvirata_k_adopci AS
SELECT z.id_zvire, z.jmeno, z.datum_prijeti, p.nazev AS plemeno_nazev, d.nazev AS druh_nazev
FROM zvire z
JOIN plemeno p ON z.id_plemeno = p.id_plemeno
JOIN druh d ON p.id_druh = d.id_druh
JOIN status_zvirete sz ON z.id_status = sz.id_status
WHERE sz.stav = 'K adopci';

-- Pohled 2: Zájemci Skóre (Zobrazí historii žádostí u zájemce a vypočte poměrové skóre schválení)
CREATE OR REPLACE VIEW v_zajemci_skore AS
SELECT z.id_zajemce, z.jmeno, z.prijmeni, z.email,
       COUNT(a.id_adopce) AS celkem_zadosti,
       SUM(CASE WHEN a.stav = 'Schválena' THEN 1 ELSE 0 END) AS schvaleno_pocet,
       SUM(CASE WHEN a.stav = 'Zamítnuta' THEN 1 ELSE 0 END) AS zamitnuto_pocet
FROM zajemce z
LEFT JOIN adopce a ON z.id_zajemce = a.id_zajemce
GROUP BY z.id_zajemce, z.jmeno, z.prijmeni, z.email;

-- Pohled 3: Detail Zvířat a Historie (kombinuje počet vet záznamů k logování)
CREATE OR REPLACE VIEW v_detail_zvirat_historie AS
SELECT z.id_zvire, z.jmeno, z.pohlavi, sz.stav AS aktualni_stav,
       COUNT(vz.id_zaznam) AS pocet_veterinarnich_vysetreni
FROM zvire z
LEFT JOIN status_zvirete sz ON z.id_status = sz.id_status
LEFT JOIN veterinarni_zaznam vz ON z.id_zvire = vz.id_zvire
GROUP BY z.id_zvire, z.jmeno, z.pohlavi, sz.stav;

-- ==============================================
-- 2. FUNKCE (FUNCTIONS)
-- ==============================================

-- Funkce 1: Výpočet věku zvířete v měsících, aby nebyl hardcoded (dynamický dotaz)
CREATE OR REPLACE FUNCTION fn_vek_mesice(datum_narozeni DATE) 
RETURNS INTEGER AS $$
DECLARE
    mesicu INTEGER;
BEGIN
    IF datum_narozeni IS NULL THEN
        RETURN -1;
    END IF;
    SELECT EXTRACT(year FROM age(current_date, datum_narozeni)) * 12 + EXTRACT(month FROM age(current_date, datum_narozeni)) INTO mesicu;
    RETURN mesicu;
END;
$$ LANGUAGE plpgsql;

-- Funkce 2: Počet zvířat určitého druhu (pro Dashboard)
CREATE OR REPLACE FUNCTION fn_pocet_zvirat_druhu(p_nazev_druhu VARCHAR) 
RETURNS INTEGER AS $$
DECLARE
    pocet INTEGER;
BEGIN
    SELECT COUNT(*) INTO pocet
    FROM zvire z
    JOIN plemeno p ON z.id_plemeno = p.id_plemeno
    JOIN druh d ON p.id_druh = d.id_druh
    JOIN status_zvirete sz ON z.id_status = sz.id_status
    WHERE d.nazev = p_nazev_druhu AND sz.stav != 'Adoptováno';
    RETURN pocet;
END;
$$ LANGUAGE plpgsql;

-- Funkce 3: Kontrola, zda je zvíře adoptovatelné boolean logika
CREATE OR REPLACE FUNCTION fn_zkontroluj_dostupnost(p_id_zvire INTEGER) 
RETURNS BOOLEAN AS $$
DECLARE
    is_dostupne BOOLEAN;
BEGIN
    SELECT CASE WHEN sz.stav IN ('K adopci', 'Přijato') THEN true ELSE false END INTO is_dostupne
    FROM zvire z JOIN status_zvirete sz ON z.id_status = sz.id_status
    WHERE z.id_zvire = p_id_zvire;
    RETURN is_dostupne;
END;
$$ LANGUAGE plpgsql;

-- ==============================================
-- 3. ULOŽENÉ PROCEDURY (STORED PROCEDURES)
-- ==============================================

-- Procedura 1: GDPR Anonymizace Zajemce v DB!
CREATE OR REPLACE PROCEDURE sp_anonymizuj_zajemce(p_id_zajemce INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE zajemce
    SET jmeno = 'Anonym',
        prijmeni = 'Anonymizováno',
        email = 'smazano@gdpr.cz',
        telefon = '000000000',
        ulice = NULL,
        mesto = NULL
    WHERE id_zajemce = p_id_zajemce;
END;
$$;

-- Procedura 2: Hromadná změna id statusu u zvířat (Macro)
CREATE OR REPLACE PROCEDURE sp_zmen_status_hromadne(p_id_stare INTEGER, p_id_nove INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE zvire
    SET id_status = p_id_nove
    WHERE id_status = p_id_stare;
END;
$$;

-- Procedura 3: Expiruj nepotvrzené adopce starší 30 dnů a zmeň na 'Zrušena'
CREATE OR REPLACE PROCEDURE sp_expiruj_stare_adopce()
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE adopce
    SET stav = 'Zamítnuta - Expirována'
    WHERE stav = 'Probíhá' AND datum_zadosti < (CURRENT_DATE - INTERVAL '30 days');
END;
$$;

-- ==============================================
-- 4. SPOUŠTĚČE (TRIGGERS)
-- ==============================================

-- Trigger 1: Ochrana před adoptováním zvířete "V léčení" přímo pod rukama backendu
CREATE OR REPLACE FUNCTION check_status_z_adopce() RETURNS TRIGGER AS $$
DECLARE
    current_status VARCHAR;
BEGIN
    SELECT sz.stav INTO current_status
    FROM zvire z JOIN status_zvirete sz ON z.id_status = sz.id_status
    WHERE z.id_zvire = NEW.id_zvire;

    IF current_status = 'V léčení' THEN
        RAISE EXCEPTION 'Nelze žádat o zvíře, které je aktuálně v léčení.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_adopce_before_insert ON adopce;
CREATE TRIGGER trg_adopce_before_insert
BEFORE INSERT ON adopce
FOR EACH ROW
EXECUTE FUNCTION check_status_z_adopce();

-- Trigger 2: Aktualizace timestampu při změně zvířete
-- Add column dynamically inside trigger creation flow safely
ALTER TABLE zvire ADD COLUMN IF NOT EXISTS datum_aktualizace TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

CREATE OR REPLACE FUNCTION set_datum_aktualizace() RETURNS TRIGGER AS $$
BEGIN
    NEW.datum_aktualizace = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_zvire_aktualizace ON zvire;
CREATE TRIGGER trg_zvire_aktualizace
BEFORE UPDATE ON zvire
FOR EACH ROW
EXECUTE FUNCTION set_datum_aktualizace();
