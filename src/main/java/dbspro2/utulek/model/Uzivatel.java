package dbspro2.utulek.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "uzivatel")
public class Uzivatel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzivatel")
    private Integer idUzivatel;

    @Column(name = "jmeno", length = 50, nullable = false)
    private String jmeno;

    @Column(name = "prijmeni", length = 50, nullable = false)
    private String prijmeni;

    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "heslo_hash", length = 255, nullable = false)
    private String hesloHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "uzivatel", fetch = FetchType.LAZY)
    private List<VeterinarniZaznam> zaznamy;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    // ===== Výchozí konstruktor =====
    public Uzivatel() {
    }

    // ===== Konstruktor pro pohodlné vytváření testovacích dat =====
    public Uzivatel(String jmeno, String prijmeni, String email, String hesloHash, Role role) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.email = email;
        this.hesloHash = hesloHash;
        this.role = role;
    }

    // Gettery a settery
    public Integer getIdUzivatel() {
        return idUzivatel;
    }

    public void setIdUzivatel(Integer idUzivatel) {
        this.idUzivatel = idUzivatel;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHesloHash() {
        return hesloHash;
    }

    public void setHesloHash(String hesloHash) {
        this.hesloHash = hesloHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<VeterinarniZaznam> getZaznamy() {
        return zaznamy;
    }

    public void setZaznamy(List<VeterinarniZaznam> zaznamy) {
        this.zaznamy = zaznamy;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}