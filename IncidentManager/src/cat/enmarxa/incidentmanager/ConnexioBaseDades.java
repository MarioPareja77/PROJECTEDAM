package cat.enmarxa.incidentmanager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexioBaseDades {
    // Constants per a la connexió a la base de dades
    private static final String URL = "jdbc:mysql://localhost:3306/gestio_incidencies";
    private static final String USUARI = "root"; // Canvia aquest usuari
    private static final String CONTRASENYA = "enmarxa"; // Canvia aquesta contrasenya

    // Mètode per obtenir la connexió a la base de dades
    public static Connection obtenirConnexio() throws SQLException {
        return DriverManager.getConnection(URL, USUARI, CONTRASENYA);
    }
}
