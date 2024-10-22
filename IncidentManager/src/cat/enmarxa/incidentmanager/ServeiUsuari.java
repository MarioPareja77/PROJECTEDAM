package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServeiUsuari {

    private UsuariDAO usuariDAO; // Objecte DAO per gestionar la base de dades

    // Constructor que inicialitza el DAO
    public ServeiUsuari() {
        try {
            this.usuariDAO = new UsuariDAO(); // Inicialitzem l'objecte DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant la inicialització
        }
    }

    // Mètode per crear un nou usuari
    public void crearUsuari(String email, String contrasenya, String area, String cap, String rol) {
        try {
            // Delegar l'operació al DAO per crear un nou usuari
            usuariDAO.crearUsuari(email, contrasenya, area, cap, rol);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant la creació de l'usuari
        }
    }

    // Mètode per obtenir el llistat de tots els usuaris
    public List<Usuari> obtenirTotsElsUsuaris() {
        try {
            // Delegar l'operació al DAO per obtenir tots els usuaris de la base de dades
            return usuariDAO.obtenirTotsElsUsuaris();
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }

    // Mètode per obtenir un usuari específic mitjançant el seu correu electrònic
    public Usuari obtenirUsuariPerEmail(String email) {
        try {
            // Delegar l'operació al DAO per obtenir l'usuari per email
            return usuariDAO.obtenirUsuariPerEmail(email);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return null; // Retornar null en cas d'error
        }
    }

    // Mètode per actualitzar un usuari existent
    public void actualitzarUsuari(String email, String contrasenya, String area, String cap, String rol) {
        try {
            // Delegar l'operació al DAO per actualitzar l'usuari
            usuariDAO.actualitzarUsuari(email, contrasenya, area, cap, rol);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant l'actualització
        }
    }

    // Mètode per obtenir el rol d'un usuari específic mitjançant el seu correu electrònic
    public String obtenirRolUsuari(String email) {
        try {
            // Delegar l'operació al DAO per obtenir el rol de l'usuari
            return usuariDAO.obtenirRolUsuari(email);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return null; // Retornar null en cas d'error
        }
    }

    // Mètode per eliminar un usuari existent
    public void eliminarUsuari(String email) {
        try {
            // Delegar l'operació al DAO per eliminar l'usuari de la base de dades
            usuariDAO.eliminarUsuari(email);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant l'eliminació
        }
    }
}
