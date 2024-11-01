package cat.enmarxa.incidentmanager;

import java.util.Date;

/**
 * La classe Incidencia representa un objecte de tipus 'Incidencia' en l'aplicació de gestió d'incidències i es trucada des de FinestraPrincipal. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
public class Incidencia {

    private int id;               // Identificador de l'incidència
    private String tipus;         // Tipus d'incidència
    private String prioritat;     // Prioritat de l'incidència
    private String descripcio;    // Descripció de l'incidència
<<<<<<< HEAD
    private String estat;		// Estat de l'incidència
    private String actiu1;    // Actiu1 associat a l'incidència
    private String actiu2;    // Actiu2 associat a l'incidència
    private String emailCreador;  // Correu electrònic del creador de l'incidència
    private String tecnicAssignat;  //Tècnic assignat a l'incidència
=======
    private String emailCreador;  // Correu electrònic del creador de l'incidència
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    private Date dataCreacio;     // Data de creació de l'incidència

    // Constructor per defecte
    public Incidencia() {
    }

    // Constructor amb tots els camps
<<<<<<< HEAD
    public Incidencia(int id, String tipus, String prioritat, String estat, String descripcio, String emailCreador, Date dataCreacio, String actiu1, String actiu2, String tecnicAssignat) {
        this.id = id;                // Inicialitzar l'ID de l'incidència
        this.tipus = tipus;          // Inicialitzar el tipus d'incidència
        this.prioritat = prioritat;  // Inicialitzar la prioritat de l'incidència
        this.descripcio = descripcio; // Inicialitzar la descripció de l'incidència
        this.emailCreador = emailCreador; // Inicialitzar el correu del creador
        this.estat = estat;	// Inicialitzar estat de l'incidència
        this.actiu1 = actiu1; // Inicialitzar l'actiu1 relacionat amb la incidencia
        this.actiu2 = actiu2; // Inicialitzar l'actiu1 relacionat amb la incidencia
        this.dataCreacio = dataCreacio; // Inicialitzar la data de creació
        this.tecnicAssignat = tecnicAssignat; // Inicialitzar el tècnic assignat;
=======
    public Incidencia(int id, String tipus, String prioritat, String descripcio, String emailCreador, Date dataCreacio) {
        this.id = id;                // Inicialitzar l'ID de l'incidència
        this.tipus = tipus;          // Inicialitzar el tipus d'incidència
        this.prioritat = prioritat;  // Inicialitzar la prioritat de l'incidència
        this.descripcio = descripcio; // Inicialitzar la descripció de l'incidència
        this.emailCreador = emailCreador; // Inicialitzar el correu del creador
        this.dataCreacio = dataCreacio; // Inicialitzar la data de creació
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }

    // Getters i setters per a cada atribut
<<<<<<< HEAD
    
=======

>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
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
<<<<<<< HEAD
    }
    
    public String getEstat() {
        return estat; // Retornar l'estat de l'incidència
    }

    public void setEstat(String estat) {
        this.estat = estat; // Assignar un nou estat a l'incidència
=======
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
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
<<<<<<< HEAD
    }
    
    public String getActiu1() {
        return actiu1; // Retornar l'actiu1 de l'incidència
    }

    public void setActiu1(String actiu1) {
        this.actiu1 = actiu1; // Assignar un nou actiu1 a l'incidència
    }
    
    public String getActiu2() {
        return actiu2; // Retornar l'actiu1 de l'incidència
    }

    public void setActiu2(String actiu2) {
        this.actiu2 = actiu2; // Assignar un nou actiu1 a l'incidència
    }
    
    public String getTecnicAssignat() {
        return tecnicAssignat; // Retornar el tècnic assignat a l'incidència
    }

    public void setTecnicAssignat(String tecnicAssignat) {
        this.tecnicAssignat = tecnicAssignat; // Assignar un tècnic assignat a l'incidència
=======
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }

    @Override
    public String toString() {
        // Retornar una representació en forma de cadena de l'objecte Incidencia
        return "Incidencia{" +
        		"id='" + id + '\'' +
                "tipus='" + tipus + '\'' +
                "prioritat='" + prioritat + '\'' +
                "estat='" + estat + '\'' +
                "actiu1=" + actiu1 + '\'' +
                "actiu2=" + actiu2 + '\'' +
                "descripcio='" + descripcio + '\'' +
                "dataCreacio='" + dataCreacio + + '\'' +
                "emailCreador='" + emailCreador + '\'' +
                "tecnicAssignat='" + tecnicAssignat + + '\'' +
                '}';
    }
}
