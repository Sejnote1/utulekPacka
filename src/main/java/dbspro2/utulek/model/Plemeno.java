package dbspro2.utulek.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plemeno")
public class Plemeno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plemeno")
    private Integer idPlemeno;

    @Column(name = "nazev", length = 50, nullable = false)
    private String nazev;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_druh", nullable = false)
    private Druh druh;

    @OneToMany(mappedBy = "plemeno", fetch = FetchType.LAZY)
    private List<Zvire> zvirata;

    public Integer getIdPlemeno() {
        return idPlemeno;
    }

    public void setIdPlemeno(Integer idPlemeno) {
        this.idPlemeno = idPlemeno;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Druh getDruh() {
        return druh;
    }

    public void setDruh(Druh druh) {
        this.druh = druh;
    }

    public List<Zvire> getZvirata() {
        return zvirata;
    }

    public void setZvirata(List<Zvire> zvirata) {
        this.zvirata = zvirata;
    }
}
