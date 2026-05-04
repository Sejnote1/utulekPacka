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

    @org.springframework.data.jpa.repository.Query(value = "SELECT fn_vek_mesice(datum_narozeni) FROM zvire WHERE id_zvire = :idZvire", nativeQuery = true)
    Integer getVekMesiceNativne(Integer idZvire);

    @org.springframework.data.jpa.repository.Query(value = "SELECT fn_pocet_zvirat_druhu(:druh)", nativeQuery = true)
    Integer getPocetZviratDruhuNativne(String druh);

    @org.springframework.data.jpa.repository.query.Procedure(procedureName = "sp_zmen_status_hromadne")
    void zmenStatusHromadneNativne(@org.springframework.data.repository.query.Param("p_id_stare") Integer p_id_stare, @org.springframework.data.repository.query.Param("p_id_nove") Integer p_id_nove);
}
