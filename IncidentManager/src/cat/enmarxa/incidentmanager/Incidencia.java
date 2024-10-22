package cat.enmarxa.incidentmanager;

import java.util.Date;

public class Incidencia {

    private int id;               // Identificador de l'incidència
    private String tipus;         // Tipus d'incidència
    private String prioritat;     // Prioritat de l'incidència
    private String descripcio;    // Descripció de l'incidència
    private String emailCreador;  // Correu electrònic del creador de l'incidència
    private Date dataCreacio;     // Data de creació de l'incidència

    // Constructor per defecte
    public Incidencia() {
    }

    // Constructor amb tots els camps
    public Incidencia(int id, String tipus, String prioritat, String descripcio, String emailCreador, Date dataCreacio) {
        this.id = id;                // Inicialitzar l'ID de l'incidència
        this.tipus = tipus;          // Inicialitzar el tipus d'incidència
        this.prioritat = prioritat;  // Inicialitzar la prioritat de l'incidència
        this.descripcio = descripcio; // Inicialitzar la descripció de l'incidència
        this.emailCreador = emailCreador; // Inicialitzar el correu del creador
        this.dataCreacio = dataCreacio; // Inicialitzar la data de creació
    }

    // Getters i setters per a cada atribut

    public int getId() {
        return id; // Retornar l'ID de l'incidència
    }

    public void setId(int id) {
        this.id = id; // Assignar un nou ID a l'incidència
    }

    public String getTipus() {
        return tipus; // Retornar el tipus d'incidència
    }

    public void setTipus(String tipus) {
        this.tipus = tipus; // Assignar un nou tipus d'incidència
    }

    public String getPrioritat() {
        return prioritat; // Retornar la prioritat de l'incidència
    }

    public void setPrioritat(String prioritat) {
        this.prioritat = prioritat; // Assignar una nova prioritat a l'incidència
    }

    public String getDescripcio() {
        return descripcio; // Retornar la descripció de l'incidència
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio; // Assignar una nova descripció a l'incidència
    }

    public String getEmailCreador() {
        return emailCreador; // Retornar el correu del creador de l'incidència
    }

    public void setEmailCreador(String emailCreador) {
        this.emailCreador = emailCreador; // Assignar un nou correu al creador
    }

    public Date getDataCreacio() {
        return dataCreacio; // Retornar la data de creació de l'incidència
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio; // Assignar una nova data de creació a l'incidència
    }

    @Override
    public String toString() {
        // Retornar una representació en forma de cadena de l'objecte Incidencia
        return "Incidencia{" +
                "tipus='" + tipus + '\'' +
                ", prioritat='" + prioritat + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", emailCreador='" + emailCreador + '\'' +
                ", dataCreacio=" + dataCreacio +
                '}';
    }
}
