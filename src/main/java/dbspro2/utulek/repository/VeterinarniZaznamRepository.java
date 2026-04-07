package dbspro2.utulek.repository;

import dbspro2.utulek.model.VeterinarniZaznam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarniZaznamRepository extends JpaRepository<VeterinarniZaznam, Integer> {
    List<VeterinarniZaznam> findByZvire_IdZvire(Integer idZvire);
    List<VeterinarniZaznam> findByUzivatel_IdUzivatel(Integer idUzivatel);
}
