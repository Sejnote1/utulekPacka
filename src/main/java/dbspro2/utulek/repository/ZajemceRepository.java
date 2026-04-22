package dbspro2.utulek.repository;

import dbspro2.utulek.model.Zajemce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZajemceRepository extends JpaRepository<Zajemce, Integer> {
    List<Zajemce> findBySchvalen(Boolean schvalen);
    List<Zajemce> findByPrijmeniContainingIgnoreCaseOrJmenoContainingIgnoreCase(String prijmeni, String jmeno);

    @org.springframework.data.jpa.repository.query.Procedure(procedureName = "sp_anonymizuj_zajemce")
    void anonymizujZajemceNativne(Integer p_id_zajemce);
}
