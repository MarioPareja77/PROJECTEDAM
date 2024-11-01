package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ServeiUsuari conté les operacions (mètodes) que es podan portar a terme contra un usuari i es trucada per FinestraPrincipal o bé altres finestres que la necessitin. Aquesta classe pertany a la capa 'Controlador' dins del patró MVC.
 */
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
<<<<<<< HEAD
    public boolean crearUsuari(String email, String contrasenya, String intentsFallits, String area, String cap, String rol, String comentaris) {
=======
    public void crearUsuari(String email, String contrasenya, String area, String cap, String rol) {
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        try {
            // Delegar l'operació al DAO per crear un nou usuari
<<<<<<< HEAD
            usuariDAO.crearUsuari(email, contrasenya, intentsFallits, area, cap, rol, comentaris);
            return true;
=======
            usuariDAO.crearUsuari(email, contrasenya, area, cap, rol);
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant la creació de l'usuari
        }
        return false; // Retornem false si es produeix un error
    }

<<<<<<< HEAD
    // Mètode per modificar la contrasenya d'un usuari
    public boolean modificarContrasenya(String email, String novaContrasenya) {
        try {
        	// Delegar l'operació al DAO per fer el canvi de contrasenya de l'usuari actual 
        	usuariDAO.modificarContrasenya(email, novaContrasenya);
        	return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
        }
        return false; // Retornem false si s'ha produït un error
    }

=======
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    // Mètode per obtenir el llistat de tots els usuaris
    public List<Usuari> obtenirTotsElsUsuaris() {
        try {
            // Delegar l'operació al DAO per obtenir tots els usuaris de la base de dades
<<<<<<< HEAD
            return usuariDAO.obtenirTotsElsUsuaris(); 
=======
            return usuariDAO.obtenirTotsElsUsuaris();
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }
<<<<<<< HEAD
    
    // Mètode per obtenir el llistat de tots els usuaris (unicament l'email)
    public List<String> getLlistatEmailUsuaris() {
        // Delegar l'operació al DAO per obtenir el mail de tots els caps de la base de dades
		return usuariDAO.getLlistatEmailUsuaris();
    }
    
    // Mètode per obtenir el llistat de tots els usuaris que són caps (unicament l'email)
    public List<String> getLlistatEmailCaps() {
        // Delegar l'operació al DAO per obtenir el mail de tots els usuaris de la base de dades
        return usuariDAO.getLlistatEmailCaps(); 
       
    }
    
    
    // Mètode per obtenir el llistat de tots els usuaris amb un rol determinat
    public List<Usuari> obtenirUsuarisRol(String rol) {
        try {
            // Delegar l'operació al DAO per obtenir tots els usuaris de la base de dades
            return usuariDAO.obtenirUsuarisRol(rol); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return new ArrayList<>(); // Retornar una llista buida en cas d'error
        }
    }
    
    // Mètode per obtenir el llistat de tots els usuaris amb un àrea determinada
    public List<Usuari> obtenirUsuarisArea(String area) {
        try {
            // Delegar l'operació al DAO per obtenir tots els usuaris de la base de dades
            return usuariDAO.obtenirUsuarisArea(area); 
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
=======

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
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }
    }

<<<<<<< HEAD
    // Mètode per modificar un usuari existent
    public boolean modificarUsuari(String email, String contrasenya, String area, String cap, String rol, String comentaris, int intentsFallits) {
=======
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
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        try {
<<<<<<< HEAD
            // Delegar l'operació al DAO per actualitzar l'usuari
            usuariDAO.modificarUsuari(email, contrasenya, area, cap, rol, comentaris, intentsFallits);
            return true;
=======
            // Delegar l'operació al DAO per eliminar l'usuari de la base de dades
            usuariDAO.eliminarUsuari(email);
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (SQLException e) {
<<<<<<< HEAD
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant l'actualització
=======
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant l'eliminació
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }
        return false;
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

    // Mètode per eliminar un usuari existent basant-se en el seu email
    public boolean eliminarUsuari(String email) {
        try {
            // Delegar l'operació al DAO per eliminar l'usuari de la base de dades
            usuariDAO.eliminarUsuari(email); 
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error durant l'eliminació
        }
        return false; // Retornem false si s'ha produït un error
    }
}
