package dbspro2.utulek.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final UzivatelDetailsService uzivatelDetailsService;

    public SecurityConfig(UzivatelDetailsService uzivatelDetailsService) {
        this.uzivatelDetailsService = uzivatelDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/verejne-adopce", "/uzivatele/*/avatar").permitAll()
                .requestMatchers("/admin/**", "/uzivatele/**").hasAuthority("Administrátor")
                // Přidání/úprava zvířat: pouze Veterinář a Administrátor (Recepční NEMŮŽE)
                .requestMatchers("/zvirata/novy", "/zvirata/ulozit", "/zvirata/*/upravit").hasAnyAuthority("Veterinář", "Administrátor")
                // Veterinární záznamy: pouze Veterinář a Administrátor
                .requestMatchers("/zaznamy/**").hasAnyAuthority("Veterinář", "Administrátor")
                // Adopce a zájemci: Recepční, Veterinář, Administrátor (všichni přihlášení)
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/chyba/pristup")
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    switch (role) {
                        case "Recepční"     -> response.sendRedirect("/zvirata");
                        case "Veterinář"    -> response.sendRedirect("/zvirata");
                        case "Administrátor"-> response.sendRedirect("/admin");
                        default             -> response.sendRedirect("/zvirata");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .userDetailsService(uzivatelDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}