package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuariDAO {
    private Connection connexio;
    int intentsFallits;

    // Constructor que estableix la connexió a la base de dades
    public UsuariDAO() throws SQLException {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establir la connexió
            String url = "jdbc:mysql://localhost:3306/gestio_incidencies";
            String user = "root"; 
            String password = "enmarxa"; 
            this.connexio = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Controlador JDBC no trobat.");
        }
    }

    // Comprovar si l'usuari i contrasenya introduïts són correctes
    public boolean autenticar(String email, String contrasenya) throws SQLException {
        String query = "SELECT * FROM Usuaris WHERE email = ? AND contrasenya = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, contrasenya);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true si es troba un registre
        }
    }

    // Obtenir el nombre d'intents fallits de l'usuari
    public int obtenirIntentsFallits(String email) throws SQLException {
        String query = "SELECT intents_fallits FROM Usuaris WHERE email = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	intentsFallits = rs.getInt("intents_fallits");
                return rs.getInt("intents_fallits");
            }
        }
        return 0; // Retorna 0 si no es troba l'usuari
    }

    // Augmentar el nombre d'intents fallits de l'usuari
    public void augmentarIntentsFallits(String email) throws SQLException {
    	obtenirIntentsFallits (email);
    	intentsFallits = intentsFallits + 1;
        String query = "UPDATE Usuaris SET intents_fallits = ? WHERE email = ?";
            try (PreparedStatement stmt = connexio.prepareStatement(query)) {
                stmt.setInt(1, intentsFallits);
                stmt.setString(2, email);
                stmt.executeUpdate();  
        }
    }
    
    // Método per crear un nou usuari
    public void crearUsuari(String email, String contrasenya, String area, String cap, String rol) throws SQLException {
        String consulta = "INSERT INTO usuaris (email, contrasenya, area, cap, rol) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            sentencia.setString(2, contrasenya);
            sentencia.setString(3, area);
            sentencia.setString(4, cap);
            sentencia.setString(5, rol);
            sentencia.executeUpdate(); // Executa la inserció
        }
    }

    // Método per obtenir tots els usuaris
    public List<Usuari> obtenirTotsElsUsuaris() throws SQLException {
        List<Usuari> usuaris = new ArrayList<>();
        String consulta = "SELECT email, contrasenya, area, cap, rol, intents_fallits FROM usuaris";
        try (Statement sentencia = connexio.createStatement(); ResultSet resultats = sentencia.executeQuery(consulta)) {
            while (resultats.next()) {
                String email = resultats.getString("email");
                String contrasenya = resultats.getString("contrasenya");
                String area = resultats.getString("area");
                String cap = resultats.getString("cap");
                String rol = resultats.getString("rol");
                int intentsFallits = resultats.getInt("intents_fallits");
                usuaris.add(new Usuari(email, contrasenya, area, cap, rol, intentsFallits)); // Agrega l'usuari a la llista
            }
        }
        return usuaris; // Retorna la llista d'usuaris
    }
    
    // Método per obtenir el rol d'un usuari
    public String obtenirRolUsuari(String email) throws SQLException {
        String consulta = "SELECT rol FROM usuaris where email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            try (ResultSet resultats = sentencia.executeQuery()) {
                if (resultats.next()) {
                    String rol = resultats.getString("rol");
                    return rol;
                }
            }
        }
        return null;
    }

    // Método per obtenir un usuari per el seu email
    public Usuari obtenirUsuariPerEmail(String email) throws SQLException {
        String consulta = "SELECT contrasenya, area, cap, rol, intents_fallits FROM usuaris WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            try (ResultSet resultats = sentencia.executeQuery()) {
                if (resultats.next()) {
                    String contrasenya = resultats.getString("contrasenya");
                    String area = resultats.getString("area");
                    String cap = resultats.getString("cap");
                    String rol = resultats.getString("rol");
                    int intentsFallits = resultats.getInt("intents_fallits");
                    return new Usuari(email, contrasenya, area, cap, rol, intentsFallits); // Retorna l'usuari trobat
                }
            }
        }
        return null; // Retorna null si no es troba l'usuari
    }

    // Método per actualitzar un usuari
    public void actualitzarUsuari(String email, String contrasenya, String area, String cap, String rol) throws SQLException {
        String consulta = "UPDATE usuaris SET contrasenya = ?, area = ?, cap = ?, rol = ? WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, contrasenya);
            sentencia.setString(2, area);
            sentencia.setString(3, cap);
            sentencia.setString(4, rol);
            sentencia.setString(5, email);
            sentencia.executeUpdate(); // Executa l'actualització
        }
    }

    // Método per eliminar un usuari
    public void eliminarUsuari(String email) throws SQLException {
        String consulta = "DELETE FROM usuaris WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            sentencia.executeUpdate(); // Executa l'eliminació
        }
    }

    // Método per tancar la connexió
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

