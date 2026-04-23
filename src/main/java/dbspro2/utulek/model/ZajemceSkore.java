package dbspro2.utulek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "v_zajemci_skore")
public class ZajemceSkore {

    @Id
    @Column(name = "id_zajemce")
    private Integer idZajemce;

    @Column(name = "celkem_zadosti")
    private Integer celkemZadosti;

    @Column(name = "schvaleno_pocet")
    private Integer schvalenoPocet;

    @Column(name = "zamitnuto_pocet")
    private Integer zamitnutoPocet;

    public Integer getIdZajemce() {
        return idZajemce;
    }

    public Integer getCelkemZadosti() {
        return celkemZadosti;
    }

    public Integer getSchvalenoPocet() {
        return schvalenoPocet;
    }

    public Integer getZamitnutoPocet() {
        return zamitnutoPocet;
    }
}
