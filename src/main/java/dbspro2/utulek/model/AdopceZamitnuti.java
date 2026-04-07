package dbspro2.utulek.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "adopce_zamitnuti",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_adopce", "id_duvod"})
        }
)
public class AdopceZamitnuti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zamitnuti")
    private Integer idZamitnuti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adopce", nullable = false)
    private Adopce adopce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_duvod", nullable = false)
    private DuvodZamitnuti duvod;

    public Integer getIdZamitnuti() {
        return idZamitnuti;
    }

    public void setIdZamitnuti(Integer idZamitnuti) {
        this.idZamitnuti = idZamitnuti;
    }

    public Adopce getAdopce() {
        return adopce;
    }

    public void setAdopce(Adopce adopce) {
        this.adopce = adopce;
    }

    public DuvodZamitnuti getDuvod() {
        return duvod;
    }

    public void setDuvod(DuvodZamitnuti duvod) {
        this.duvod = duvod;
    }
}
