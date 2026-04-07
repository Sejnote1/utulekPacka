package dbspro2.utulek.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "status_zvirete")
public class StatusZvirete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "stav", length = 50, nullable = false)
    private String stav;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<Zvire> zvirata;

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }

    public List<Zvire> getZvirata() {
        return zvirata;
    }

    public void setZvirata(List<Zvire> zvirata) {
        this.zvirata = zvirata;
    }
}
