package dbspro2.utulek.security;

import dbspro2.utulek.model.Uzivatel;
import dbspro2.utulek.repository.UzivatelRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UzivatelDetailsService implements UserDetailsService {

    private final UzivatelRepository uzivatelRepo;

    public UzivatelDetailsService(UzivatelRepository uzivatelRepo) {
        this.uzivatelRepo = uzivatelRepo;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Uzivatel u = uzivatelRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Uživatel nenalezen: " + email));
        return new UzivatelDetails(u);
    }
}