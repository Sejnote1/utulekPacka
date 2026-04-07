package dbspro2.utulek.repository;

import dbspro2.utulek.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNazev(String nazev);
}
