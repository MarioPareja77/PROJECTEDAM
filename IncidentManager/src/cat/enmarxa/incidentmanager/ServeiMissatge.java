package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe ServeiActiu conté les operacions (mètodes) que es podan portar a terme contra un missatge i es trucada per FinestraPrincipal o bé altres finestres que la necessitin. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
public class ServeiMissatge {
    private MissatgeDAO missatgeDAO; // Suposant que tens un DAO per gestionar els missatges
    
    // Constructor de la classe
    public ServeiMissatge() {
        try {
            this.missatgeDAO = new MissatgeDAO(); // Inicialitzem el DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Gestió d'excepcions en la inicialització
        }
    }


    // Mètode per crear un nou missatge
    public boolean crearMissatge(String emailDe, String emailPer, String missatge) {
        try {
            // Deleguem l'operació al DAO
            missatgeDAO.crearMissatge(emailDe, emailPer, missatge); // Creem el nou missatge
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
        }
        return false; // Retorna false si la creació del missatge ha donat cap error
    }

    // Mètode per obtenir el llistat de tots els missatges
    public List<Missatge> llistarMissatges() {
        try {
            // Deleguem l'operació al DAO
            return missatgeDAO.llistarMissatges(); // Obtenim tots els missatges de la base de dades
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
    }

    // Mètode per obtenir missatges d'un remitent (persona que envia) concret
    public List<Missatge> obtenirMissatgesEmailDe(String emailDe) {
        try {
            // Deleguem l'operació al DAO
            return missatgeDAO.obtenirMissatgesEmailDe(emailDe); // Obtenim tots els missatges d'un remitent concret
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
    }

    // Mètode per obtenir missatges per a un destinatari (persona que rep) concret
    public List<Missatge> obtenirMissatgesEmailPer(String emailPer) {
        try {
            // Deleguem l'operació al DAO
            return missatgeDAO.obtenirMissatgesEmailPer(emailPer); // Obtenim tots els missatges d'un destinatari concret
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
            return new ArrayList<>(); // Retornem una llista buida en cas d'error
        }
    }

    // Mètode per eliminar un missatge basant-se en el seu ID
    public boolean eliminarMissatge(int idMissatge) {
        try {
            // Deleguem l'operació al DAO per eliminar el missatge de la base de dades
            missatgeDAO.eliminarMissatge(idMissatge); // Eliminem el missatge pel seu ID
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció
        }
        return false; // Retorna false si l'eliminació ha donat cap error
    }
}
