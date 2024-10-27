package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UsuariDAO {
    private Connection connexio; // Objecte per gestionar la connexió a la base de dades
    int intentsFallits; // Variable per comptar els intents fallits de login

    // Constructor que estableix la connexió a la base de dades
    public UsuariDAO() throws SQLException {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establir la connexió amb la base de dades
            String url = "jdbc:mysql://localhost:3306/gestio_incidencies";
            String user = "Admin";
            String password = "enmarxa";
            this.connexio = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Controlador JDBC no trobat.");
        }
    }

    // Mètode per comprovar si l'usuari i la contrasenya són correctes
    public boolean autenticar(String email, String contrasenya) throws SQLException {
        String query = "SELECT contrasenya FROM Usuaris WHERE email = ?";
        String contrasenyaHasheadaDB = null;
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Obtenir la contrasenya hasheada de la base de dades
                contrasenyaHasheadaDB = rs.getString("contrasenya");
            } else {
                System.out.println("Usuari no trobat.");
                return false; // Retornar fals si l'usuari no es troba
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retornar fals si hi ha un error en la consulta
        }
        // Comprovar si la contrasenya introduïda coincideix amb la hash
        return BCrypt.checkpw(contrasenya, contrasenyaHasheadaDB);
    }

    // Mètode per obtenir el nombre d'intents fallits de l'usuari
    public int obtenirIntentsFallits(String email) throws SQLException {
        String query = "SELECT intents_fallits FROM Usuaris WHERE email = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("intents_fallits"); // Retorna el nombre d'intents fallits
            }
        }
        return 0; // Retorna 0 si no es troba l'usuari
    }

    // Mètode per augmentar els intents fallits de login de l'usuari
    public void augmentarIntentsFallits(String email) throws SQLException {
        int intentsFallits = obtenirIntentsFallits(email);
        intentsFallits = intentsFallits + 1; // Augmentar en 1 el comptador
        String query = "UPDATE Usuaris SET intents_fallits = ? WHERE email = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setInt(1, intentsFallits);
            stmt.setString(2, email);
            stmt.executeUpdate(); // Actualitza el comptador a la base de dades
        }
    }

    // Mètode per restablir els intents fallits de l'usuari a 0
    public void restablirIntentsFallits(String email) throws SQLException {
        intentsFallits = 0;
        String query = "UPDATE Usuaris SET intents_fallits = ? WHERE email = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setInt(1, intentsFallits);
            stmt.setString(2, email);
            stmt.executeUpdate(); // Actualitza els intents fallits a 0
        }
    }

    // Mètode per crear un nou usuari
    public void crearUsuari(String email, String contrasenya, String area, String cap, String rol) throws SQLException {
        // Hashear la contrasenya i afegir el salt
        String contrasenyaHasheada = BCrypt.hashpw(contrasenya, BCrypt.gensalt());
        String consulta = "INSERT INTO usuaris (email, contrasenya, area, cap, rol) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            sentencia.setString(2, contrasenyaHasheada);
            sentencia.setString(3, area);
            sentencia.setString(4, cap);
            sentencia.setString(5, rol);
            sentencia.executeUpdate(); // Executar la inserció de l'usuari
        }
    }

    // Mètode per obtenir tots els usuaris de la base de dades
    public List<Usuari> obtenirTotsElsUsuaris() throws SQLException {
        List<Usuari> usuaris = new ArrayList<>();
        String consulta = "SELECT email, contrasenya, area, cap, rol, intents_fallits FROM usuaris";
        try (Statement sentencia = connexio.createStatement(); ResultSet resultats = sentencia.executeQuery(consulta)) {
            while (resultats.next()) {
                // Crear objectes Usuari i afegir-los a la llista
                String email = resultats.getString("email");
                String contrasenya = resultats.getString("contrasenya");
                String area = resultats.getString("area");
                String cap = resultats.getString("cap");
                String rol = resultats.getString("rol");
                int intentsFallits = resultats.getInt("intents_fallits");
                usuaris.add(new Usuari(email, contrasenya, area, cap, rol, intentsFallits));
            }
        }
        return usuaris; // Retorna la llista d'usuaris
    }

    // Mètode per obtenir el rol d'un usuari donat el seu email
    public String obtenirRolUsuari(String email) throws SQLException {
        String consulta = "SELECT rol FROM usuaris WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            try (ResultSet resultats = sentencia.executeQuery()) {
                if (resultats.next()) {
                    return resultats.getString("rol"); // Retorna el rol de l'usuari
                }
            }
        }
        return null; // Retorna null si no es troba el rol
    }

    // Mètode per obtenir un usuari a partir del seu email
    public Usuari obtenirUsuariPerEmail(String email) throws SQLException {
        String consulta = "SELECT contrasenya, area, cap, rol, intents_fallits FROM usuaris WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            try (ResultSet resultats = sentencia.executeQuery()) {
                if (resultats.next()) {
                    // Crear un objecte Usuari amb les dades obtingudes
                    String contrasenya = resultats.getString("contrasenya");
                    String area = resultats.getString("area");
                    String cap = resultats.getString("cap");
                    String rol = resultats.getString("rol");
                    int intentsFallits = resultats.getInt("intents_fallits");
                    return new Usuari(email, contrasenya, area, cap, rol, intentsFallits);
                }
            }
        }
        return null; // Retorna null si no es troba l'usuari
    }

    // Mètode per actualitzar un usuari
    public void actualitzarUsuari(String email, String contrasenya, String area, String cap, String rol) throws SQLException {
        // Hashear la nova contrasenya
        String contrasenyaHasheada = BCrypt.hashpw(contrasenya, BCrypt.gensalt());
        String consulta = "UPDATE usuaris SET contrasenya = ?, area = ?, cap = ?, rol = ? WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, contrasenyaHasheada);
            sentencia.setString(2, area);
            sentencia.setString(3, cap);
            sentencia.setString(4, rol);
            sentencia.setString(5, email);
            sentencia.executeUpdate(); // Executar l'actualització
        }
    }

    // Mètode per eliminar un usuari de la base de dades
    public void eliminarUsuari(String email) throws SQLException {
        String consulta = "DELETE FROM usuaris WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            sentencia.executeUpdate(); // Executar l'eliminació de l'usuari
        }
    }

    // Mètode per bloquejar un usuari (establir intentsFallits a 5)
    public void bloquejarUsuari(String email) throws SQLException {
        int intentsFallits = 5;
        String query = "UPDATE Usuaris SET intents_fallits = ? WHERE email = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setInt(1, intentsFallits); // Estableix el nombre d'intents fallits a 5
            stmt.setString(2, email);
            stmt.executeUpdate(); // Executar l'actualització per bloquejar l'usuari
        }
    }

    // Mètode per tancar la connexió a la base de dades
    public void tancarConnexio() {
        try {
            if (connexio != null && !connexio.isClosed()) {
                connexio.close(); // Tanca la connexió
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneig d'excepcions al tancar la connexió
        }
    }
}
