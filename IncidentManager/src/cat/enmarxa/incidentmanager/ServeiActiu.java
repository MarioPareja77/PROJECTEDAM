package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public boolean crearActiu(String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) {
        try {
            // Deleguem l'operació al DAO
            int id = -1; // Inicialitzem l'ID com a -1, potser es genera automàticament
            actiuDAO.crearActiu(id, nom, tipus, area, marca, dataAlta, descripcio); // Creem el nou actiu
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            // Es pot afegir un missatge d'error personalitzat si és necessari
        }
        return false; // Es pot modificar el retorn depenent de la implementació
    }

    // Mètode per obtenir el llistat de tots els actius
    public List<Actiu> obtenirTotsElsActius() {
        try {
            // Deleguem l'operació al DAO
            return actiuDAO.obtenirTotsElsActius(); // Obtenim tots els actius de la base de dades
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
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
    }
}
