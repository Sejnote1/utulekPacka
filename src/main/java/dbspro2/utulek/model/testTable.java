package dbspro2.utulek.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Zvire")
public class testTable {

    @Id
    @Column(name = "id_zvire")
    private Long id;

    @Column(name = "jmeno")
    private String jmeno;

    @Column(name = "datum_narozeni")
    private java.time.LocalDate datumNarozeni;

    @Column(name = "pohlavi")
    private String pohlavi;

    @Column(name = "povaha")
    private String povaha;

    @Column(name = "datum_prijeti")
    private java.time.LocalDate datumPrijeti;

    // Gettery a settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJmeno() { return jmeno; }
    public void setJmeno(String jmeno) { this.jmeno = jmeno; }

    public java.time.LocalDate getDatumNarozeni() { return datumNarozeni; }
    public void setDatumNarozeni(java.time.LocalDate datumNarozeni) { this.datumNarozeni = datumNarozeni; }

    public String getPohlavi() { return pohlavi; }
    public void setPohlavi(String pohlavi) { this.pohlavi = pohlavi; }

    public String getPovaha() { return povaha; }
    public void setPovaha(String povaha) { this.povaha = povaha; }

    public java.time.LocalDate getDatumPrijeti() { return datumPrijeti; }
    public void setDatumPrijeti(java.time.LocalDate datumPrijeti) { this.datumPrijeti = datumPrijeti; }
}