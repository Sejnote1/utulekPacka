package dbspro2.utulek.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "duvod_zamitnuti")
public class DuvodZamitnuti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_duvod")
    private Integer idDuvod;

    @Column(name = "popis", columnDefinition = "TEXT", nullable = false)
    private String popis;

    @OneToMany(mappedBy = "duvod", fetch = FetchType.LAZY)
    private List<AdopceZamitnuti> zamitnuti;

    public Integer getIdDuvod() {
        return idDuvod;
    }

    public void setIdDuvod(Integer idDuvod) {
        this.idDuvod = idDuvod;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public List<AdopceZamitnuti> getZamitnuti() {
        return zamitnuti;
    }

    public void setZamitnuti(List<AdopceZamitnuti> zamitnuti) {
        this.zamitnuti = zamitnuti;
    }
}
