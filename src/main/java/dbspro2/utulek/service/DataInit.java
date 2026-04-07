package dbspro2.utulek.service;

import dbspro2.utulek.model.Role;
import dbspro2.utulek.model.Uzivatel;
import dbspro2.utulek.repository.RoleRepository;
import dbspro2.utulek.repository.UzivatelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepo, UzivatelRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.count() == 0) {
                // vytvoření rolí
                Role recepce = roleRepo.save(new Role("Recepční"));
                Role veterinar = roleRepo.save(new Role("Veterinář"));
                Role admin = roleRepo.save(new Role("Administrátor"));

                // vytvoření uživatelů (bez null pro id)
                userRepo.save(new Uzivatel("Jan", "Novák", "recepce@example.com", encoder.encode("heslo123"), recepce));
                userRepo.save(new Uzivatel("Petra", "Svobodová", "vet@example.com", encoder.encode("heslo123"), veterinar));
                userRepo.save(new Uzivatel("Admin", "Admin", "admin@example.com", encoder.encode("admin123"), admin));
            }
        };
    }
}