package dbspro2.utulek.repository;

import dbspro2.utulek.model.Plemeno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlemenoRepository extends JpaRepository<Plemeno, Integer> {
    List<Plemeno> findByDruh_IdDruh(Integer idDruh);
}
