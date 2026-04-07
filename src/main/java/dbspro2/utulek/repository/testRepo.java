package dbspro2.utulek.repository;
import dbspro2.utulek.model.testTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface testRepo extends JpaRepository<testTable, Long> {
}