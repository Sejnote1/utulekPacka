package dbspro2.utulek.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "zajemce")
public class Zajemce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zajemce")
    private Integer idZajemce;

    @Column(name = "jmeno", length = 50, nullable = false)
    private String jmeno;

    @Column(name = "prijmeni", length = 50, nullable = false)
    private String prijmeni;

    @Column(name = "telefon", length = 50)
    private String telefon;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "mesto", length = 50)
    private String mesto;

    @Column(name = "ulice", length = 50)
    private String ulice;

    @Column(name = "cislo_popisne")
    private Integer cisloPopisne;

    @Column(name = "psc", length = 50)
    private String psc;

    @Column(name = "typ_bydleni", length = 50)
    private String typBydleni;

    @Column(name = "ma_jina_zvirata")
    private Boolean maJinaZvirata;

    @Column(name = "schvalen")
    private Boolean schvalen;

    @OneToMany(mappedBy = "zajemce", fetch = FetchType.LAZY)
    private List<Adopce> adopce;

    public Integer getIdZajemce() {
        return idZajemce;
    }

    public void setIdZajemce(Integer idZajemce) {
        this.idZajemce = idZajemce;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public Integer getCisloPopisne() {
        return cisloPopisne;
    }

    public void setCisloPopisne(Integer cisloPopisne) {
        this.cisloPopisne = cisloPopisne;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getTypBydleni() {
        return typBydleni;
    }

    public void setTypBydleni(String typBydleni) {
        this.typBydleni = typBydleni;
    }

    public Boolean getMaJinaZvirata() {
        return maJinaZvirata;
    }

    public void setMaJinaZvirata(Boolean maJinaZvirata) {
        this.maJinaZvirata = maJinaZvirata;
    }

    public Boolean getSchvalen() {
        return schvalen;
    }

    public void setSchvalen(Boolean schvalen) {
        this.schvalen = schvalen;
    }

    public List<Adopce> getAdopce() {
        return adopce;
    }

    public void setAdopce(List<Adopce> adopce) {
        this.adopce = adopce;
    }
}
