package cat.enmarxa.incidentmanager;

import java.util.Date; // Importa la classe Date per al nou camp data_alta

public class Actiu {

    private int id;       	 // Identificador únic de l'actiu
    private String nom;       // Nom de l'actiu
    private String tipus;     // Tipus d'actiu (per exemple, "ordinador", "servidor", etc.)
    private String area;      // Àrea o departament on es troba l'actiu
    private String marca;     // Marca de l'actiu
    private Date dataAlta;    // Data d'alta de l'actiu
    private String descripcio; // Descripció de l'actiu

    // Constructor per defecte
    public Actiu() {
    }

    // Constructor amb tots els camps
    public Actiu(int id, String nom, String tipus, String area, String marca, Date dataAlta, String descripcio) {
        this.id = id;
        this.nom = nom;
        this.tipus = tipus;
        this.area = area;
        this.marca = marca;
        this.dataAlta = dataAlta;
        this.descripcio = descripcio;
    }

    // Getters i setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public String toString() {
        return "Actiu{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", tipus='" + tipus + '\'' +
                ", area='" + area + '\'' +
                ", marca='" + marca + '\'' +
                ", dataAlta=" + dataAlta + // Incloem dataAlta a la representació de cadena
                ", descripcio='" + descripcio + '\'' + // Incloem descripcio a la representació de cadena
                '}';
    }
}
