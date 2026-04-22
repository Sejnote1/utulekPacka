package dbspro2.utulek.repository;

import dbspro2.utulek.model.AdopceZamitnuti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dbspro2.utulek.model.AdopceZamitnutiId;

public interface AdopceZamitnutiRepository extends JpaRepository<AdopceZamitnuti, AdopceZamitnutiId> {
    List<AdopceZamitnuti> findByAdopce_IdAdopce(Integer idAdopce);
}
