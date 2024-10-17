package cat.enmarxa.incidentmanager;

public class Incidencia {
    private String descripcio;
    private String tipus;
    private String prioritat;

    public Incidencia(String descripcio, String tipus, String prioritat) {
        this.descripcio = descripcio;
        this.tipus = tipus;
        this.prioritat = prioritat;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getTipus() {
        return tipus;
    }

    public String getPrioritat() {
        return prioritat;
    }
}
