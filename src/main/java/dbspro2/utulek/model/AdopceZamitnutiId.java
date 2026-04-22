package dbspro2.utulek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AdopceZamitnutiId implements Serializable {

    @Column(name = "id_adopce")
    private Integer idAdopce;

    @Column(name = "id_duvod")
    private Integer idDuvod;

    public AdopceZamitnutiId() {}

    public AdopceZamitnutiId(Integer idAdopce, Integer idDuvod) {
        this.idAdopce = idAdopce;
        this.idDuvod = idDuvod;
    }

    public Integer getIdAdopce() {
        return idAdopce;
    }

    public void setIdAdopce(Integer idAdopce) {
        this.idAdopce = idAdopce;
    }

    public Integer getIdDuvod() {
        return idDuvod;
    }

    public void setIdDuvod(Integer idDuvod) {
        this.idDuvod = idDuvod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdopceZamitnutiId that = (AdopceZamitnutiId) o;
        return Objects.equals(idAdopce, that.idAdopce) &&
               Objects.equals(idDuvod, that.idDuvod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdopce, idDuvod);
    }
}
