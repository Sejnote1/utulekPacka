package dbspro2.utulek.repository;

import dbspro2.utulek.model.Zvire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZvireRepository extends JpaRepository<Zvire, Integer> {

    List<Zvire> findByJmenoContainingIgnoreCase(String jmeno);

    List<Zvire> findByStatus_IdStatus(Integer idStatus);

    List<Zvire> findByPlemeno_IdPlemeno(Integer idPlemeno);

    @org.springframework.data.jpa.repository.Query(value = "SELECT fn_zkontroluj_dostupnost(:idZvire)", nativeQuery = true)
    Boolean zkontrolujDostupnostNativne(Integer idZvire);
}
