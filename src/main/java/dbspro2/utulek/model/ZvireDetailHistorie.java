package dbspro2.utulek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "v_detail_zvirat_historie")
public class ZvireDetailHistorie {

    @Id
    @Column(name = "id_zvire")
    private Integer idZvire;

    @Column(name = "jmeno")
    private String jmeno;

    @Column(name = "pohlavi")
    private String pohlavi;

    @Column(name = "aktualni_stav")
    private String aktualniStav;

    @Column(name = "pocet_veterinarnich_vysetreni")
    private Integer pocetVeterinarnichVysetreni;

    public Integer getIdZvire() {
        return idZvire;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPohlavi() {
        return pohlavi;
    }

    public String getAktualniStav() {
        return aktualniStav;
    }

    public Integer getPocetVeterinarnichVysetreni() {
        return pocetVeterinarnichVysetreni;
    }
}
