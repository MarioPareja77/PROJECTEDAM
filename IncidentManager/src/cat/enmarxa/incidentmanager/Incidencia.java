package cat.enmarxa.incidentmanager;

import java.util.Date;

public class Incidencia {

	private int id;           // Identificador de l'ncidència
	private String tipus;        	   // Tipus d'incidència
    private String prioritat;       // Prioritat de l'incidència
    private String descripcio;      // Descripció de l'incidència
    private String emailCreador;    // Correu electrònic del creador de l'incidència
    private Date dataCreacio;       // Data de creació de l'incidència

    // Constructor per defecte
    public Incidencia() {
    }

    // Constructor amb tots els camps
    public Incidencia(int id, String tipus, String prioritat, String descripcio, String emailCreador, Date dataCreacio) {
    	this.id = id;
    	this.tipus = tipus;
        this.prioritat = prioritat;
        this.descripcio = descripcio;
        this.emailCreador = emailCreador;
        this.dataCreacio = dataCreacio;
    }

    // Getters i setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getPrioritat() {
        return prioritat;
    }

    public void setPrioritat(String prioritat) {
        this.prioritat = prioritat;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getEmailCreador() {
        return emailCreador;
    }

    public void setEmailCreador(String emailCreador) {
        this.emailCreador = emailCreador;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "tipus='" + tipus + '\'' +
                ", prioritat='" + prioritat + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", emailCreador='" + emailCreador + '\'' +
                ", dataCreacio=" + dataCreacio +
                '}';
    }
}
