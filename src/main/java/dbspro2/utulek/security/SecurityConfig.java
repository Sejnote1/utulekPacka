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

    // Konstruktorová injekce
    public SecurityConfig(UzivatelDetailsService uzivatelDetailsService) {
        this.uzivatelDetailsService = uzivatelDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            switch (role) {
                                case "Recepční" -> response.sendRedirect("/recepce");
                                case "Veterinář" -> response.sendRedirect("/veterinar");
                                case "Administrátor" -> response.sendRedirect("/admin");
                                default -> response.sendRedirect("/home");
                            }
                        })
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )
                .userDetailsService(uzivatelDetailsService);  // tady už je správně použito

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}