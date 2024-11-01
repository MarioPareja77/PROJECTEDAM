package cat.enmarxa.incidentmanager;

import java.util.Date; // Importa la classe Date per al camp dataCreacio

/**
 * La classe Actiu representa un objecte de tipus 'Missatge' en l'aplicació de gestió d'incidències i es trucada des de FinestraPrincipal. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
public class Missatge {

    private int id;             // Identificador únic del missatge
    private String emailDe;     // Correu electrònic del remitent
    private String emailPer;   // Correu electrònic del destinatari
    private Date dataCreacio;   // Data de creació del missatge
    private String contingut;   // Contingut del missatge

    // Constructor per defecte
    public Missatge() {
    }

    // Constructor amb tots els camps per inicialitzar un nou objecte Missatge
    public Missatge(int id, String emailDe, String emailPara, Date dataCreacio, String contingut) {
        this.id = id;                // Assigna l'identificador únic
        this.emailDe = emailDe;      // Assigna el correu electrònic del remitent
        this.emailPer = emailPara;  // Assigna el correu electrònic del destinatari
        this.dataCreacio = dataCreacio; // Assigna la data de creació
        this.contingut = contingut;  // Assigna el contingut del missatge
    }

    // Getters i setters per accedir i modificar els camps del missatge

    public int getId() {
        return id; // Retorna l'identificador del missatge
    }

    public void setId(int id) {
        this.id = id; // Estableix l'identificador del missatge
    }

    public String getEmailDe() {
        return emailDe; // Retorna el correu electrònic del remitent
    }

    public void setEmailDe(String emailDe) {
        this.emailDe = emailDe; // Estableix el correu electrònic del remitent
    }

    public String getEmailPer() {
        return emailPer; // Retorna el correu electrònic del destinatari
    }

    public void setEmailPer(String emailPara) {
        this.emailPer = emailPara; // Estableix el correu electrònic del destinatari
    }

    public Date getDataCreacio() {
        return dataCreacio; // Retorna la data de creació del missatge
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio; // Estableix la data de creació del missatge
    }

    public String getContingut() {
        return contingut; // Retorna el contingut del missatge
    }

    public void setContingut(String contingut) {
        this.contingut = contingut; // Estableix el contingut del missatge
    }

    // Mètode per a representar l'objecte Missatge com a cadena
    @Override
    public String toString() {
        return "Missatge{" +
                "id=" + id + // Inclou l'identificador a la representació
                ", emailDe='" + emailDe + '\'' + // Inclou el remitent a la representació
                ", emailPer='" + emailPer + '\'' + // Inclou el destinatari a la representació
                ", dataCreacio=" + dataCreacio + // Inclou la data de creació a la representació
                ", contingut='" + contingut + '\'' + // Inclou el contingut a la representació
                '}'; // Tanca la representació
    }
}
