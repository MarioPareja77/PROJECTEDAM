package cat.enmarxa.incidentmanager;

public class Usuari {
    // Atributs de la classe Usuari
    private String email; // Correu electrònic de l'usuari
    private String contrasenya; // Contrasenya de l'usuari
    private String area; // Àrea de l'usuari dins de l'empresa
    private String cap; // Cap o responsable de l'usuari
    private String rol; // Rol de l'usuari (per exemple, administrador, usuari normal, etc.)
    private int intentsFallits; // Nombre d'intents fallits d'inici de sessió

    // Constructor de la classe Usuari
    public Usuari (String email, String contrasenya, String area, String cap, String rol, int intentsFallits) {
        this.email = email;
        this.contrasenya = contrasenya;
        this.area = area;
        this.cap = cap;
        this.rol = rol;
        this.intentsFallits = intentsFallits; // Inicialitzar el nombre d'intents fallits
    }

    // Getters i setters per accedir i modificar els atributs de la classe

    public String getEmail() {
        return email; // Retorna el correu electrònic de l'usuari
    }

    public void setEmail(String email) {
        this.email = email; // Estableix el correu electrònic de l'usuari
    }

    public String getContrasenya() {
        return contrasenya; // Retorna la contrasenya de l'usuari
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya; // Estableix la contrasenya de l'usuari
    }

    public String getArea() {
        return area; // Retorna l'àrea de l'usuari
    }

    public void setArea(String area) {
        this.area = area; // Estableix l'àrea de l'usuari
    }

    public String getCap() {
        return cap; // Retorna el cap o responsable de l'usuari
    }

    public void setCap(String cap) {
        this.cap = cap; // Estableix el cap o responsable de l'usuari
    }

    public String getRol() {
        return rol; // Retorna el rol de l'usuari
    }

    public void setRol(String rol) {
        this.rol = rol; // Estableix el rol de l'usuari
    }

    public int getIntentsFallits() {
        return intentsFallits; // Retorna el nombre d'intents fallits d'inici de sessió
    }

    public void setIntentsFallits(int intentsFallits) {
        this.intentsFallits = intentsFallits; // Estableix el nombre d'intents fallits
    }

    // Sobreescriptura del mètode toString per representar l'objecte Usuari com una cadena
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
