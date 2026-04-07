package dbspro2.utulek.repository;

import dbspro2.utulek.model.StatusZvirete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusZvireteRepository extends JpaRepository<StatusZvirete, Integer> {
    Optional<StatusZvirete> findByStav(String stav);
}
