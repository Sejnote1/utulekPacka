package dbspro2.utulek.repository;

import dbspro2.utulek.model.AdopceZamitnuti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdopceZamitnutiRepository extends JpaRepository<AdopceZamitnuti, Integer> {
    List<AdopceZamitnuti> findByAdopce_IdAdopce(Integer idAdopce);
}
