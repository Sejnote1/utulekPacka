package dbspro2.utulek.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "nazev", length = 50, nullable = false)
    private String nazev;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Uzivatel> uzivatele;

    // ===== Výchozí konstruktor (nutný pro JPA) =====
    public Role() {
    }

    // ===== Konstruktor pro rychlé vytváření objektů =====
    public Role(String nazev) {
        this.nazev = nazev;
    }

    // Gettery a settery
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public List<Uzivatel> getUzivatele() {
        return uzivatele;
    }

    public void setUzivatele(List<Uzivatel> uzivatele) {
        this.uzivatele = uzivatele;
    }
}