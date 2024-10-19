package cat.enmarxa.incidentmanager;

import java.sql.SQLException;

public class ServeiLogin {
    private UsuariDAO usuariDAO;

    // Constructor que inicialitza UsuariDAO, que és la classe que contacta amb la BD
    public ServeiLogin() throws SQLException {
        this.usuariDAO = new UsuariDAO(); 
    }

    // Mètode per autenticar l'usuari
    public boolean autenticar(String email, String contrasenya) {
        try {
            // Comprovar si l'usuari i la contrasenya són correctes
            return usuariDAO.autenticar(email, contrasenya);
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retornar false en cas d'error
        }
    }

    // Mètode per obtenir els intents fallits d'un usuari
    public int obtenirIntentsFallits(String email) {
        try {
            return usuariDAO.obtenirIntentsFallits(email); // Retornar el nombre d'intents fallits
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Retornar 0 en cas d'error
        }
    }

    // Mètode per actualitzar el nombre d'intents fallits d'un usuari
    public void augmentarIntentsFallits(String email) {
        try {
            usuariDAO.augmentarIntentsFallits(email); // Actualitzar intents fallits
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mètode per bloquejar un usuari
    public void bloquejarUsuari(String email) {
        try {
            usuariDAO.bloquejarUsuari(email); // Bloquejar l'usuari
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mètode per restablir els intents fallits d'un usuari després d'un login exitós
    public void restablirIntentsFallits(String email) {
        try {
            usuariDAO.restablirIntentsFallits(email); // Restablir intents fallits
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mètode per tancar la connexió a la BD
    public void close() {
        try {
            usuariDAO.close(); // Tancar la connexió
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
