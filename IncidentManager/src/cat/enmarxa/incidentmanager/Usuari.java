package cat.enmarxa.incidentmanager;

public class Usuari {
    private String id;
    private String contrasenya;
    private String area;
    private String cap;
    private String rol;

    public Usuari (String id, String contrasenya, String area, String cap, String rol) {
        this.id = id;
        this.contrasenya = contrasenya;
        this.area = area;
        this.cap = cap;
        this.rol = rol;
    }
    
    public String getId() {
        return id;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public String getCap() {
        return cap;
    }
    
    public String getArea() {
        return area;
    }

    public String getrol() {
        return rol;
    }          
        
}