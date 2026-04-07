package dbspro2.utulek.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "veterinarni_zaznam")
public class VeterinarniZaznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zaznam")
    private Integer idZaznam;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "popis", length = 50)
    private String popis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zvire", nullable = false)
    private Zvire zvire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_uzivatel", nullable = false)
    private Uzivatel uzivatel;

    public Integer getIdZaznam() {
        return idZaznam;
    }

    public void setIdZaznam(Integer idZaznam) {
        this.idZaznam = idZaznam;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Zvire getZvire() {
        return zvire;
    }

    public void setZvire(Zvire zvire) {
        this.zvire = zvire;
    }

    public Uzivatel getUzivatel() {
        return uzivatel;
    }

    public void setUzivatel(Uzivatel uzivatel) {
        this.uzivatel = uzivatel;
    }
}