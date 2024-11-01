package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe ServeiActiu conté les operacions (mètodes) que es podan portar a terme contra un actiu i es trucada per FinestraPrincipal o bé altres finestres que la necessitin. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
public class ServeiActiu {

    private ActiuDAO actiuDAO; // Objecte DAO per gestionar la base de dades

    // Constructor de la classe
    public ServeiActiu() {
        try {
            this.actiuDAO = new ActiuDAO(); // Inicialitzem el DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió d'excepcions en la inicialització
        }
    }

    // Mètode per crear un nou actiu
<<<<<<< HEAD
    public boolean crearActiu(String nom, String tipus, String area, String marca, String descripcio, Date dataAlta) {
=======
    public boolean crearActiu(String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) {
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        try {
            // Deleguem l'operació al DAO
<<<<<<< HEAD
            actiuDAO.crearActiu(nom, tipus, area, marca, descripcio, dataAlta); // Creem el nou actiu
            return true;
=======
            int id = -1; // Inicialitzem l'ID com a -1, potser es genera automàticament
            actiuDAO.crearActiu(id, nom, tipus, area, marca, dataAlta, descripcio); // Creem el nou actiu
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
<<<<<<< HEAD
        }
        return false; // Retorna false si la creació de l'actiu ha donat cap error
    }

    // Mètode per obtenir el llistat de tots els actius
    public List<Actiu> llistarActius() {
        try {
            // Deleguem l'operació al DAO
            return actiuDAO.llistarActius(); // Obtenim tots els actius de la base de dades
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
    }

    // Mètode per obtenir el llistat del nom de tots els actius
    public List<String> getLlistaActius() {
        // Deleguem l'operació al DAO
        return actiuDAO.getLlistaActius(); // Obtenim tots els noms dels actius de la base de dades
    }

    // Mètode per llistar actius per nom
    public Actiu obtenirActiuPerNom(String nom) {
        try {
            // Deleguem l'operació al DAO
            return actiuDAO.obtenirActiuPerNom(nom); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return null; // Retornem null en cas d'error
        }
    }

    // Mètode per obtenir el llistat de tots els actius d'un àrea determinada
    public List<Actiu> obtenirActiusArea(String area) {
        try {
            // Delegar l'operació al DAO per obtenir els actius de la base de dades
            return actiuDAO.obtenirActiusArea(area);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }
 
    // Mètode per obtenir el llistat de tots els actius d'una marca determinada
    public List<Actiu> obtenirActiusMarca(String marca) {
        try {
            // Delegar l'operació al DAO per obtenir els actius de la base de dades
            return actiuDAO.obtenirActiusMarca(marca); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }

    // Mètode per obtenir el llistat de tots els actius d'un tipus determinat
    public List<Actiu> obtenirActiusTipus(String tipus) {
        try {
            // Delegar l'operació al DAO per obtenir els actius de la base de dades
            return actiuDAO.obtenirActiusTipus(tipus); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }

    // Mètode per modificar un actiu
    public boolean modificarActiu(String nom, String tipus, String area, String marca, String descripcio) {
        try {
            // Deleguem l'operació al DAO per actualitzar l'actiu amb els nous valors
            actiuDAO.modificarActiu(nom, tipus, area, marca, descripcio); // Actualitzem l'actiu
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
=======
            // Es pot afegir un missatge d'error personalitzat si és necessari
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }
        return false; // Es pot modificar el retorn depenent de la implementació
    }

<<<<<<< HEAD
    // Mètode per eliminar un actiu basant-se en el seu nom
    public boolean eliminarActiu(String nom) {
=======
    // Mètode per obtenir el llistat de tots els actius
    public List<Actiu> obtenirTotsElsActius() {
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        try {
<<<<<<< HEAD
            // Deleguem l'operació al DAO per eliminar l'actiu de la base de dades
            actiuDAO.eliminarActiu(nom); // Eliminem l'actiu pel seu nom
            return true;
=======
            // Deleguem l'operació al DAO
            return actiuDAO.obtenirTotsElsActius(); // Obtenim tots els actius de la base de dades
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
<<<<<<< HEAD
=======
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }
<<<<<<< HEAD
        return false;
        
=======
    }

    // Mètode per obtenir el llistat de tots els actius (només els noms)
    public List<String> obtenirNomTotsElsActius() {
        // Deleguem l'operació al DAO
        return actiuDAO.obtenirNomTotsElsActius(); // Obtenim tots els noms dels actius de la base de dades
    }

    // Mètode per obtenir un actiu específic pel seu ID
    public Actiu obtenirActiuPerId(int idActiu) {
        try {
            // Deleguem l'operació al DAO
            return actiuDAO.obtenirActiuPerId(idActiu); // Obtenim l'actiu pel seu ID
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return null; // Retornem null en cas d'error
        }
    }

    // Mètode per actualitzar un actiu
    public void actualitzarActiu(int idActiu, String nom, String tipus, String area, String marca, Date dataAlta, String descripcio) {
        try {
            // Deleguem l'operació al DAO per actualitzar l'actiu amb els nous valors
            actiuDAO.actualitzarActiu(idActiu, nom, tipus, area, marca, dataAlta, descripcio); // Actualitzem l'actiu
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
        }
    }

    // Mètode per eliminar un actiu
    public void eliminarActiu(int idActiu) {
        try {
            // Deleguem l'operació al DAO per eliminar l'actiu de la base de dades
            actiuDAO.eliminarActiu(idActiu); // Eliminem l'actiu pel seu ID
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
        }
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }
}
