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
    private Long celkemZadosti;

    @Column(name = "schvaleno_pocet")
    private Long schvalenoPocet;

    @Column(name = "zamitnuto_pocet")
    private Long zamitnutoPocet;

    public Integer getIdZajemce() {
        return idZajemce;
    }

    public Long getCelkemZadosti() {
        return celkemZadosti;
    }

    public Long getSchvalenoPocet() {
        return schvalenoPocet;
    }

    public Long getZamitnutoPocet() {
        return zamitnutoPocet;
    }
}
