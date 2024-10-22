package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServeiIncidencia {

    private IncidenciaDAO incidenciaDAO; // Objecte DAO per gestionar la base de dades

    // Mètode constructor
    public ServeiIncidencia() {
        try {
            // Inicialitzem l'objecte DAO i creem la taula d'incidències si no existeix
            this.incidenciaDAO = new IncidenciaDAO();
            incidenciaDAO.crearTaulaIncidencies(); // Crear la taula d'incidències si no està creada
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en la inicialització
        }
    }

    // Mètode per crear una nova incidència
    public boolean crearIncidencia(String tipus, String prioritat, String descripcio, String usuari) {
        try {
            // Delegar l'operació al DAO per crear la nova incidència
            incidenciaDAO.crearIncidencia(tipus, prioritat, descripcio, usuari);
            return true; // Retornem true si la creació és correcta
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
        }
        return false; // Retornem false si es produeix un error
    }

    // Mètode per associar actius a una incidència
    private void associarActius(int idIncidencia, List<Integer> actius) {
        try {
            // Delegar l'operació al DAO per associar actius a la incidència
            incidenciaDAO.associarActius(idIncidencia, actius);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
        }
    }

    // Mètode per obtenir el llistat d'incidències d'un usuari específic
    public List<Incidencia> obtenirIncidenciesPerUsuari(String idUsuari) {
        try {
            // Delegar l'operació al DAO per obtenir les incidències d'un usuari concret
            return incidenciaDAO.obtenirIncidenciesPerUsuari(idUsuari);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
    }

    // Mètode per obtenir totes les incidències creades per tots els usuaris
    public List<Incidencia> obtenirTotesLesIncidencies() {
        try {
            // Delegar l'operació al DAO per obtenir totes les incidències
            return incidenciaDAO.obtenirTotesLesIncidencies();
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
    }

    // Mètode per llistar totes les incidències creades de tots els usuaris
    public List<Incidencia> listarIncidencies() {
        // Aquest mètode no llença SQLException, així que no és necessari try-catch
        try {
			return incidenciaDAO.listarIncidencies();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
