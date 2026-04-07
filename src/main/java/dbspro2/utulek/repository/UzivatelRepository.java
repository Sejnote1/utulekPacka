package dbspro2.utulek.repository;

import dbspro2.utulek.model.Uzivatel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UzivatelRepository extends JpaRepository<Uzivatel, Integer> {

    Optional<Uzivatel> findByEmail(String email);
}
