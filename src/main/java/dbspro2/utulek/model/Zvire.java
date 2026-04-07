package dbspro2.utulek.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "zvire")
public class Zvire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zvire")
    private Integer idZvire;

    @Column(name = "jmeno", length = 50)
    private String jmeno;

    @Column(name = "datum_narozeni")
    private LocalDate datumNarozeni;

    @Column(name = "pohlavi", length = 1)
    private String pohlavi;

    @Column(name = "povaha", length = 50)
    private String povaha;

    @Column(name = "datum_prijeti", nullable = false)
    private LocalDate datumPrijeti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plemeno", nullable = false)
    private Plemeno plemeno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusZvirete status;

    @OneToMany(mappedBy = "zvire", fetch = FetchType.LAZY)
    private List<Adopce> adopce;

    @OneToMany(mappedBy = "zvire", fetch = FetchType.LAZY)
    private List<VeterinarniZaznam> zaznamy;

    public Integer getIdZvire() {
        return idZvire;
    }

    public void setIdZvire(Integer idZvire) {
        this.idZvire = idZvire;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public LocalDate getDatumNarozeni() {
        return datumNarozeni;
    }

    public void setDatumNarozeni(LocalDate datumNarozeni) {
        this.datumNarozeni = datumNarozeni;
    }

    public String getPohlavi() {
        return pohlavi;
    }

    public void setPohlavi(String pohlavi) {
        this.pohlavi = pohlavi;
    }

    public String getPovaha() {
        return povaha;
    }

    public void setPovaha(String povaha) {
        this.povaha = povaha;
    }

    public LocalDate getDatumPrijeti() {
        return datumPrijeti;
    }

    public void setDatumPrijeti(LocalDate datumPrijeti) {
        this.datumPrijeti = datumPrijeti;
    }

    public Plemeno getPlemeno() {
        return plemeno;
    }

    public void setPlemeno(Plemeno plemeno) {
        this.plemeno = plemeno;
    }

    public StatusZvirete getStatus() {
        return status;
    }

    public void setStatus(StatusZvirete status) {
        this.status = status;
    }

    public List<Adopce> getAdopce() {
        return adopce;
    }

    public void setAdopce(List<Adopce> adopce) {
        this.adopce = adopce;
    }

    public List<VeterinarniZaznam> getZaznamy() {
        return zaznamy;
    }

    public void setZaznamy(List<VeterinarniZaznam> zaznamy) {
        this.zaznamy = zaznamy;
    }
}
