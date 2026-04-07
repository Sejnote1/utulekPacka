package dbspro2.utulek.repository;

import dbspro2.utulek.model.Druh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DruhRepository extends JpaRepository<Druh, Integer> {
    boolean existsByNazev(String nazev);
}
