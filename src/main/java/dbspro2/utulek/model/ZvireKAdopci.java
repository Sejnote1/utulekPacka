package dbspro2.utulek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "v_zvirata_k_adopci")
public class ZvireKAdopci {

    @Id
    @Column(name = "id_zvire")
    private Integer idZvire;

    @Column(name = "jmeno")
    private String jmeno;

    @Column(name = "datum_prijeti")
    private LocalDate datumPrijeti;

    @Column(name = "plemeno_nazev")
    private String plemenoNazev;

    @Column(name = "druh_nazev")
    private String druhNazev;

    public Integer getIdZvire() {
        return idZvire;
    }

    public String getJmeno() {
        return jmeno;
    }

    public LocalDate getDatumPrijeti() {
        return datumPrijeti;
    }

    public String getPlemenoNazev() {
        return plemenoNazev;
    }

    public String getDruhNazev() {
        return druhNazev;
    }
}
