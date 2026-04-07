package dbspro2.utulek.repository;

import dbspro2.utulek.model.Adopce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdopceRepository extends JpaRepository<Adopce, Integer> {

    List<Adopce> findByStav(String stav);

    List<Adopce> findByZvire_IdZvire(Integer idZvire);

    boolean existsByZvire_IdZvireAndStav(Integer idZvire, String stav);
}
