package dbspro2.utulek.model;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "druh")
public class Druh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_druh")
    private Integer idDruh;

    @Column(name = "nazev", length = 50, nullable = false)
    private String nazev;

    @OneToMany(mappedBy = "druh", fetch = FetchType.LAZY)
    private List<Plemeno> plemena;

    public Integer getIdDruh() {
        return idDruh;
    }

    public void setIdDruh(Integer idDruh) {
        this.idDruh = idDruh;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public List<Plemeno> getPlemena() {
        return plemena;
    }

    public void setPlemena(List<Plemeno> plemena) {
        this.plemena = plemena;
    }
}
