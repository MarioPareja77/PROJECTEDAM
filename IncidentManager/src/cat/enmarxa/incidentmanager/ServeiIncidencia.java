package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ServeiIncidencia conté les operacions (mètodes) que es podan portar a terme contra una incidència i es trucada per FinestraPrincipal o bé altres finestres que la necessitin. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
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
    public boolean crearIncidencia(String tipus, String prioritat, String estat, String descripcio, String usuari, String actiu1, String actiu2) {
        try {
            // Delegar l'operació al DAO per crear la nova incidència
<<<<<<< HEAD
            incidenciaDAO.crearIncidencia(tipus, prioritat, estat, descripcio, usuari, actiu1, actiu2);
=======
            incidenciaDAO.crearIncidencia(tipus, prioritat, descripcio, usuari);
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            return true; // Retornem true si la creació és correcta
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
        }
        return false; // Retornem false si es produeix un error
    }

    // Mètode per obtenir el llistat de totes les incidències en un estat determinat
    public List<Incidencia> obtenirIncidenciesEstat(String estat) {
        try {
<<<<<<< HEAD
            // Delegar l'operació al DAO per obtenir les incidències de la base de dades
            return incidenciaDAO.obtenirIncidenciesEstat(estat); 
=======
            // Delegar l'operació al DAO per associar actius a la incidència
            incidenciaDAO.associarActius(idIncidencia, actius);
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (SQLException e) {
<<<<<<< HEAD
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }

    // Mètode per obtenir el llistat de totes les incidències en una prioritat determinada
    public List<Incidencia> obtenirIncidenciesPrioritat(String prioritat) {
        try {
            // Delegar l'operació al DAO per obtenir les incidències de la base de dades
            return incidenciaDAO.obtenirIncidenciesPrioritat(prioritat); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }

    // Mètode per obtenir el llistat de totes les incidències d'un tipus determinat
    public List<Incidencia> obtenirIncidenciesTipus(String tipus) {
        try {
            // Delegar l'operació al DAO per obtenir les incidències de la base de dades
            return incidenciaDAO.obtenirIncidenciesTipus(tipus); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
=======
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }
    }

    // Mètode per obtenir el llistat d'incidències d'un usuari específic
    public List<Incidencia> obtenirIncidenciesUsuari(String email) {
        try {
            // Delegar l'operació al DAO per obtenir les incidències d'un usuari concret
<<<<<<< HEAD
            return incidenciaDAO.obtenirIncidenciesUsuari(email);
=======
            return incidenciaDAO.obtenirIncidenciesPerUsuari(idUsuari);
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
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
    
    // Mètode per obtenir totes les incidències creades per tots els usuaris
    public List<String> getLlistaIncidenciesID() {
        // Delegar l'operació al DAO per obtenir totes les incidències
		return incidenciaDAO.getLlistaIncidenciesID();
    }
    
    // Mètode per modificar una incidència existent
    public boolean modificarIncidencia(int id, String tipus, String prioritat, String descripcio, String actiu1, String actiu2, String estat, String tecnic_assignat) {
    	
        try {
            // Delegar l'operació al DAO per crear la nova incidència
            incidenciaDAO.modificarIncidencia(id, tipus, prioritat, descripcio, actiu1, actiu2, estat, tecnic_assignat);
            return true; // Retornem true si la modificació és correcta
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió de l'excepció en cas d'error
        }
        return false; // Retornem false si es produeix un error
    }
    

<<<<<<< HEAD
    
    // Mètode per eliminar una incidència existent basant-se en el seu id
    public boolean eliminarIncidencia(int id) {
        try {
            // Delegar l'operació al DAO per eliminar l'incidència de la base de dades
            incidenciaDAO.eliminarIncidencia(id);
            return true; // Retornem true si la modificació és correcta
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant l'eliminació
        }
        return false; // Retornem false si es produeix un error
=======
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
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }
}
