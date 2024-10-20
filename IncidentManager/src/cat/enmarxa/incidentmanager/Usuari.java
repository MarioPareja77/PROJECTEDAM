package cat.enmarxa.incidentmanager;

public class Usuari {
    private String email;
    private String contrasenya;
    private String area;
    private String cap;
    private String rol;
    private int intentsFallits;

    public Usuari (String email, String contrasenya, String area, String cap, String rol, int intentsFallits) {
        this.email = email;
        this.contrasenya = contrasenya;
        this.area = area;
        this.cap = cap;
        this.rol = rol;
    }
    
  // Getters i setters
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContrasenya() {
        return contrasenya;
    }
    
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    
   
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area= area;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIntentsFallits() {
        return intentsFallits;
    }

    public void setIntentsFallits(int intentsFallits) {
        this.intentsFallits = intentsFallits;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "email='" + email + '\'' +
                ", area='" + area + '\'' +
                ", cap='" + cap + '\'' +
                ", rol='" + rol + '\'' +
                ", intentsFallits=" + intentsFallits +
                '}';
    }
}
