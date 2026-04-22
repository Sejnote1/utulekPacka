package dbspro2.utulek.service;

import dbspro2.utulek.model.*;
import dbspro2.utulek.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit {

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepo,
            UzivatelRepository userRepo,
            DruhRepository druhRepo,
            PlemenoRepository plemenoRepo,
            StatusZvireteRepository statusRepo,
            DuvodZamitnutiRepository duvodRepo,
            PasswordEncoder encoder,
            JdbcTemplate jdbcTemplate
    ) {
        return args -> {

            // ===== ROLE =====
            if (roleRepo.count() == 0) {
                roleRepo.save(new Role("Recepční"));
                roleRepo.save(new Role("Veterinář"));
                roleRepo.save(new Role("Administrátor"));
            }

            // ===== UZIVATELE =====
            if (userRepo.count() == 0) {
                Role recepce   = roleRepo.findByNazev("Recepční").orElseThrow();
                Role veterinar = roleRepo.findByNazev("Veterinář").orElseThrow();
                Role admin     = roleRepo.findByNazev("Administrátor").orElseThrow();

                userRepo.save(new Uzivatel("Jan",   "Novák",      "recepce@example.com", encoder.encode("heslo123"), recepce));
                userRepo.save(new Uzivatel("Petra", "Svobodová",  "vet@example.com",     encoder.encode("heslo123"), veterinar));
                userRepo.save(new Uzivatel("Admin", "Admin",      "admin@example.com",   encoder.encode("admin123"), admin));
            }

            // ===== STATUSY ZVÍŘETE =====
            if (statusRepo.count() == 0) {
                statusRepo.save(new StatusZvirete("Přijato"));
                statusRepo.save(new StatusZvirete("V léčení"));
                statusRepo.save(new StatusZvirete("K adopci"));
                statusRepo.save(new StatusZvirete("Rezervováno"));
                statusRepo.save(new StatusZvirete("Adoptováno"));
            }

            // ===== DRUHY a PLEMENA =====
            if (druhRepo.count() == 0) {
                Druh pes   = druhRepo.save(new Druh("Pes"));
                Druh kocka = druhRepo.save(new Druh("Kočka"));
                Druh kral  = druhRepo.save(new Druh("Králík"));

                // Plemena psů
                plemenoRepo.save(new Plemeno("Labrador",         pes));
                plemenoRepo.save(new Plemeno("Německý ovčák",    pes));
                plemenoRepo.save(new Plemeno("Zlatý retrívr",    pes));
                plemenoRepo.save(new Plemeno("Pudl",             pes));
                plemenoRepo.save(new Plemeno("Buldok",           pes));
                plemenoRepo.save(new Plemeno("Kříženec",         pes));

                // Plemena koček
                plemenoRepo.save(new Plemeno("Perská",           kocka));
                plemenoRepo.save(new Plemeno("Siamská",          kocka));
                plemenoRepo.save(new Plemeno("Britská krátkosrstá", kocka));
                plemenoRepo.save(new Plemeno("Kříženec",         kocka));

                // Plemena králíků
                plemenoRepo.save(new Plemeno("Bílý novozélandský", kral));
                plemenoRepo.save(new Plemeno("Zakrslý",          kral));
            }

            // ===== DŮVODY ZAMÍTNUTÍ =====
            if (duvodRepo.count() == 0) {
                duvodRepo.save(new DuvodZamitnuti("Nevhodné bytové podmínky"));
                duvodRepo.save(new DuvodZamitnuti("Přítomnost malých dětí (zvíře nevhodné)"));
                duvodRepo.save(new DuvodZamitnuti("Jiná zvířata v domácnosti (zvíře nevhodné)"));
                duvodRepo.save(new DuvodZamitnuti("Nedostatečné zkušenosti s daným druhem"));
                duvodRepo.save(new DuvodZamitnuti("Nezájem zájemce po bližším seznámení"));
                duvodRepo.save(new DuvodZamitnuti("Jiný důvod"));
            }

            // ===== NAČTENÍ A SPUŠTĚNÍ NATIVNÍCH SQL OBJEKTŮ (Triggery, Views) =====
            try {
                org.springframework.core.io.Resource resource = new org.springframework.core.io.ClassPathResource("custom_db_objects.sql");
                String sql = new String(resource.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                // Rozdělíme ručně přes speciální oddělovač pokud jdbc neumí bloky vcelku, ale Postgres driver obvykle umí spustit celý skript.
                jdbcTemplate.execute(sql);
                System.out.println("Všechny SQL Views, Functions, Procedures a Triggers byly nahrány.");
            } catch (Exception e) {
                System.err.println("Chyba při nahrávání SQL datových objektů: " + e.getMessage());
            }
        };
    }
}