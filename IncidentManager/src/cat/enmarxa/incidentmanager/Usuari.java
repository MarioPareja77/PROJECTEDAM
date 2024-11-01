package cat.enmarxa.incidentmanager;

/**
 * La classe Usuari representa un objecte de tipus 'Usuari' en l'aplicació de gestió d'incidències i es trucada des de FinestraPrincipal. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
public class Usuari {
    // Atributs de la classe Usuari
    private String email; // Correu electrònic de l'usuari
    private String contrasenya; // Contrasenya de l'usuari
    private String area; // Àrea de l'usuari dins de l'empresa
    private String cap; // Cap o responsable de l'usuari
    private String rol; // Rol de l'usuari (per exemple, administrador, usuari normal, etc.)
    private int intentsFallits; // Nombre d'intents fallits d'inici de sessió
    private String comentaris; // Comentaris de l'usuari

    // Constructor de la classe Usuari
    public Usuari (String email, String contrasenya, String area, String cap, String rol, int intentsFallits, String comentaris) {
        this.email = email;
        this.contrasenya = contrasenya;
        this.area = area;
        this.cap = cap;
        this.rol = rol;
        this.intentsFallits = intentsFallits; // Inicialitzar el nombre d'intents fallits
        this.comentaris = comentaris;
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

    public void setComentaris(String comentaris) {
        this.comentaris = comentaris; // Estableix comentaris de l'usuari
    }
    
    public String getComentaris() {
        return comentaris; // Retorna els comentaris de l'usuari
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
                "contrasenya='" + contrasenya + '\'' +
                ", area='" + area + '\'' +
                ", cap='" + cap + '\'' +
                ", rol='" + rol + '\'' +
                ", intentsFallits=" + intentsFallits +
                ", comentaris=" + comentaris +
                '}';
    }
}
