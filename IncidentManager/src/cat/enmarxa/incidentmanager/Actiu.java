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

    // Constructor amb tots els camps per inicialitzar un nou objecte Actiu
    public Actiu(int id, String nom, String tipus, String area, String marca, Date dataAlta, String descripcio) {
        this.id = id;              // Assigna l'identificador únic
        this.nom = nom;            // Assigna el nom de l'actiu
        this.tipus = tipus;        // Assigna el tipus d'actiu
        this.area = area;          // Assigna l'àrea on es troba l'actiu
        this.marca = marca;        // Assigna la marca de l'actiu
        this.dataAlta = dataAlta;  // Assigna la data d'alta
        this.descripcio = descripcio; // Assigna la descripció de l'actiu
    }

    // Getters i setters per accedir i modificar els camps de l'actiu

    public int getId() {
        return id; // Retorna l'identificador de l'actiu
    }

    public void setId(int id) {
        this.id = id; // Estableix l'identificador de l'actiu
    }

    public String getNom() {
        return nom; // Retorna el nom de l'actiu
    }

    public void setNom(String nom) {
        this.nom = nom; // Estableix el nom de l'actiu
    }

    public String getTipus() {
        return tipus; // Retorna el tipus d'actiu
    }

    public void setTipus(String tipus) {
        this.tipus = tipus; // Estableix el tipus d'actiu
    }

    public String getArea() {
        return area; // Retorna l'àrea on es troba l'actiu
    }

    public void setArea(String area) {
        this.area = area; // Estableix l'àrea de l'actiu
    }

    public String getMarca() {
        return marca; // Retorna la marca de l'actiu
    }

    public void setMarca(String marca) {
        this.marca = marca; // Estableix la marca de l'actiu
    }

    public Date getDataAlta() {
        return dataAlta; // Retorna la data d'alta de l'actiu
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta; // Estableix la data d'alta de l'actiu
    }

    public String getDescripcio() {
        return descripcio; // Retorna la descripció de l'actiu
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio; // Estableix la descripció de l'actiu
    }

    // Mètode per a representar l'objecte Actiu com a cadena
    @Override
    public String toString() {
        return "Actiu{" +
                "id='" + id + '\'' + // Inclou l'identificador a la representació
                ", nom='" + nom + '\'' + // Inclou el nom a la representació
                ", tipus='" + tipus + '\'' + // Inclou el tipus a la representació
                ", area='" + area + '\'' + // Inclou l'àrea a la representació
                ", marca='" + marca + '\'' + // Inclou la marca a la representació
                ", dataAlta=" + dataAlta + // Inclou dataAlta a la representació de cadena
                ", descripcio='" + descripcio + '\'' + // Inclou descripcio a la representació de cadena
                '}'; // Tanca la representació
    }
}
