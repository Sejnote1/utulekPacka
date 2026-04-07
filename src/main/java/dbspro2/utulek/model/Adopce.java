package dbspro2.utulek.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
        name = "adopce"
)
public class Adopce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adopce")
    private Integer idAdopce;

    @Column(name = "datum_zadosti", nullable = false)
    private LocalDate datumZadosti;

    @Column(name = "datum_schvaleni")
    private LocalDate datumSchvaleni;

    @Column(name = "stav", length = 50, nullable = false)
    private String stav;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zvire", nullable = false)
    private Zvire zvire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zajemce", nullable = false)
    private Zajemce zajemce;

    @OneToMany(mappedBy = "adopce", fetch = FetchType.LAZY)
    private List<AdopceZamitnuti> zamitnuti;

    public Integer getIdAdopce() {
        return idAdopce;
    }

    public void setIdAdopce(Integer idAdopce) {
        this.idAdopce = idAdopce;
    }

    public LocalDate getDatumZadosti() {
        return datumZadosti;
    }

    public void setDatumZadosti(LocalDate datumZadosti) {
        this.datumZadosti = datumZadosti;
    }

    public LocalDate getDatumSchvaleni() {
        return datumSchvaleni;
    }

    public void setDatumSchvaleni(LocalDate datumSchvaleni) {
        this.datumSchvaleni = datumSchvaleni;
    }

    public String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }

    public Zvire getZvire() {
        return zvire;
    }

    public void setZvire(Zvire zvire) {
        this.zvire = zvire;
    }

    public Zajemce getZajemce() {
        return zajemce;
    }

    public void setZajemce(Zajemce zajemce) {
        this.zajemce = zajemce;
    }

    public List<AdopceZamitnuti> getZamitnuti() {
        return zamitnuti;
    }

    public void setZamitnuti(List<AdopceZamitnuti> zamitnuti) {
        this.zamitnuti = zamitnuti;
    }
}