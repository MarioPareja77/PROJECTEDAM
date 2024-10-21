package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServeiIncidencia {

    private IncidenciaDAO incidenciaDAO; // Objeto DAO para gestionar la base de datos

    // Mètode Constructor
    public ServeiIncidencia() {
        try {
			this.incidenciaDAO = new IncidenciaDAO();
			incidenciaDAO.crearTaulaIncidencies();
		} catch (SQLException e) {
			e.printStackTrace();
		} // Inicialitzem el DAO
    }

    // Mètode per crear una nova incidència
    public boolean crearIncidencia(String tipus, String prioritat, String descripcio, String usuari) {
        try {
            // Delegar la operación al DAO
            incidenciaDAO.crearIncidencia(tipus, prioritat, descripcio, usuari); // Obtener el ID de la incidencia creada
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            // Aquí puedes agregar un mensaje de error personalizado si es necesario.
        }
        return false ;
    }

    // Mètode per associar actius a una incidència
    private void associarActius(int idIncidencia, List<Integer> actius) {
        try {
            // Delegar la operación al DAO
            incidenciaDAO.associarActius(idIncidencia, actius);
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            // Aquí puedes agregar un mensaje de error personalizado si es necesario.
        }
    }

    // Mètode per obtenir el llistat d'incidències d'un usuari específic
    public List<Incidencia> obtenirIncidenciesPerUsuari(String idUsuari) {
        try {
            // Delegar la operación al DAO
            return incidenciaDAO.obtenirIncidenciesPerUsuari(idUsuari);
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }

    // Mètode per obtenir totes les incidències creades per tots els usuaris
    public List<Incidencia> obtenirTotesLesIncidencies() {
        try {
            // Delegar la operación al DAO
            return incidenciaDAO.obtenirTotesLesIncidencies();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }

    // Mètode per llistar totes les incidències creades de totes els usuaris
    public List<Incidencia> listarIncidencies() {
        // En este caso, no se lanza SQLException, así que no es necesario try-catch
        return incidenciaDAO.listarIncidencies();
    }
}

