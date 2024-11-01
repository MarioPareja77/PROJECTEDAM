package cat.enmarxa.incidentmanager;

import java.sql.SQLException;

/**
 * La classe ServeLogin és l'encarregada d'efectuar l'autenticació dels usuaris que es volen logar a l'aplicació i altres mètodes relacionats amb el login. La classe és trucada per Servidor i al seu voltant truca a UsuariDAO. Aquesta classe pertany al 'Controlador' dels del patró de disseny MVC.
 */
public class ServeiLogin {
    private UsuariDAO usuariDAO; // Objecte DAO per gestionar la base de dades dels usuaris

    // Constructor que inicialitza UsuariDAO, que és la classe que contacta amb la BD
    public ServeiLogin() throws SQLException {
        this.usuariDAO = new UsuariDAO(); // Inicialitzem l'objecte DAO
    }

    // Mètode per autenticar l'usuari
    public boolean autenticar(String email, String contrasenya) {
        try {
            // Comprovar si l'usuari i la contrasenya són correctes
            return usuariDAO.autenticar(email, contrasenya);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return false; // Retornar false en cas d'error
        }
    }

    // Mètode per obtenir els intents fallits d'un usuari
    public int obtenirIntentsFallits(String email) {
        try {
            // Obtenir el nombre d'intents fallits per a un usuari
            return usuariDAO.obtenirIntentsFallits(email); 
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
            return 0; // Retornar 0 en cas d'error
        }
    }

    // Mètode per augmentar el nombre d'intents fallits d'un usuari
    public void augmentarIntentsFallits(String email) {
        try {
            // Actualitzar el comptador d'intents fallits per a un usuari
            usuariDAO.augmentarIntentsFallits(email);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
        }
    }

    // Mètode per bloquejar un usuari després d'intents fallits consecutius
    public void bloquejarUsuari(String email) {
        try {
            // Bloquejar l'usuari després d'intents fallits
            usuariDAO.bloquejarUsuari(email);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
        }
    }

    // Mètode per restablir els intents fallits d'un usuari després d'un login exitós
    public void restablirIntentsFallits(String email) {
        try {
            // Reiniciar el comptador d'intents fallits a zero
            usuariDAO.restablirIntentsFallits(email);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
        }
    }

    // Mètode per tancar la connexió amb la base de dades
    public void close() {
        try {
            // Tancar la connexió a la base de dades
            usuariDAO.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionem l'excepció en cas d'error
        }
    }
}
