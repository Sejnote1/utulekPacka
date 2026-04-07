package dbspro2.utulek.model;

/**
 * Původní testovací třída – odstraněna @Entity anotace,
 * aby nekolidovala s třídou Zvire (stejná tabulka).
 * Ponechána pro kompatibilitu kompilace.
 */
public class testTable {
    private Long id;
    private String jmeno;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getJmeno() { return jmeno; }
    public void setJmeno(String jmeno) { this.jmeno = jmeno; }
}