package dbspro2.utulek.model;

import jakarta.persistence.*;

@Entity
@Table(name = "adopce_zamitnuti")
public class AdopceZamitnuti {

    @EmbeddedId
    private AdopceZamitnutiId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAdopce")
    @JoinColumn(name = "id_adopce", nullable = false)
    private Adopce adopce;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDuvod")
    @JoinColumn(name = "id_duvod", nullable = false)
    private DuvodZamitnuti duvod;

    public AdopceZamitnuti() {}

    public AdopceZamitnuti(Adopce adopce, DuvodZamitnuti duvod) {
        this.adopce = adopce;
        this.duvod = duvod;
        this.id = new AdopceZamitnutiId(adopce.getIdAdopce(), duvod.getIdDuvod());
    }

    public AdopceZamitnutiId getId() {
        return id;
    }

    public void setId(AdopceZamitnutiId id) {
        this.id = id;
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
