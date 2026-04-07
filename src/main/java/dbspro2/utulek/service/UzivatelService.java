package dbspro2.utulek.service;

import dbspro2.utulek.model.Uzivatel;
import dbspro2.utulek.repository.UzivatelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UzivatelService {

    private final UzivatelRepository uzivatelRepository;
    private final PasswordEncoder passwordEncoder;

    public UzivatelService(UzivatelRepository uzivatelRepository, PasswordEncoder passwordEncoder) {
        this.uzivatelRepository = uzivatelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Uzivatel> getAll() {
        return uzivatelRepository.findAll();
    }

    public Optional<Uzivatel> getById(Integer id) {
        return uzivatelRepository.findById(id);
    }

    public Uzivatel save(Uzivatel uzivatel, boolean hashPassword) {
        if (hashPassword && uzivatel.getHesloHash() != null && !uzivatel.getHesloHash().isEmpty()) {
            uzivatel.setHesloHash(passwordEncoder.encode(uzivatel.getHesloHash()));
        }
        return uzivatelRepository.save(uzivatel);
    }

    public void delete(Integer id) {
        uzivatelRepository.deleteById(id);
    }
}
