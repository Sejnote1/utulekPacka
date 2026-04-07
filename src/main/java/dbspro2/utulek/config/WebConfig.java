package dbspro2.utulek.config;

import dbspro2.utulek.model.*;
import dbspro2.utulek.repository.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registruje konvertory pro převod String ID → JPA entita.
 * Thymeleaf formuláře posílají ID jako String, Spring MVC
 * pomocí těchto konvertorů automaticky načte entitu z databáze.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PlemenoRepository plemenoRepo;
    private final StatusZvireteRepository statusRepo;
    private final ZvireRepository zvireRepo;
    private final ZajemceRepository zajemceRepo;
    private final RoleRepository roleRepo;

    public WebConfig(PlemenoRepository plemenoRepo,
                     StatusZvireteRepository statusRepo,
                     ZvireRepository zvireRepo,
                     ZajemceRepository zajemceRepo,
                     RoleRepository roleRepo) {
        this.plemenoRepo = plemenoRepo;
        this.statusRepo  = statusRepo;
        this.zvireRepo   = zvireRepo;
        this.zajemceRepo = zajemceRepo;
        this.roleRepo    = roleRepo;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        // String → Plemeno
        registry.addConverter(String.class, Plemeno.class, id -> {
            if (id == null || id.isBlank()) return null;
            return plemenoRepo.findById(Integer.parseInt(id)).orElse(null);
        });

        // String → StatusZvirete
        registry.addConverter(String.class, StatusZvirete.class, id -> {
            if (id == null || id.isBlank()) return null;
            return statusRepo.findById(Integer.parseInt(id)).orElse(null);
        });

        // String → Zvire
        registry.addConverter(String.class, Zvire.class, id -> {
            if (id == null || id.isBlank()) return null;
            return zvireRepo.findById(Integer.parseInt(id)).orElse(null);
        });

        // String → Zajemce
        registry.addConverter(String.class, Zajemce.class, id -> {
            if (id == null || id.isBlank()) return null;
            return zajemceRepo.findById(Integer.parseInt(id)).orElse(null);
        });

        // String → Role
        registry.addConverter(String.class, Role.class, id -> {
            if (id == null || id.isBlank()) return null;
            return roleRepo.findById(Integer.parseInt(id)).orElse(null);
        });
    }
}
