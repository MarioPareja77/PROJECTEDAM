package cat.enmarxa.incidentmanager;

public class Actiu {
    private String id;
    private String nom;
    private String tipus;
    private String area;
    private String marca;

    public Actiu(String id, String nom, String tipus, String area, String marca) {
        this.id = id;
        this.nom = nom;
        this.tipus = tipus;
        this.area = area;
        this.marca = marca;
    }
    
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getTipus() {
        return tipus;
    }

    public String getArea() {
        return area;
    }          
        
    public String getMarca() {
        return marca;
    }
}