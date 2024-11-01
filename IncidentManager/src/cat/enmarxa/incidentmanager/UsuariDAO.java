package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe UsuariDAO és un DAO (Data Access Object) encarregat de contactar directament amb la taula 'usuaris' de la BD (MySQL) i es trucada des de ServeiUsuari. Aquesta classe pertany a la capa de persistència o 'Model' dins del patró MVC.
 */
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
            String user = "root"; 
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
    
    // Mètode per canviar la contrasenya
    public void modificarContrasenya(String email, String novaContrasenya) throws SQLException {
    	// Hashear la contrasenya i afegir el salt
        String contrasenyaHasheada = BCrypt.hashpw(novaContrasenya, BCrypt.gensalt());
        String query = "UPDATE Usuaris SET contrasenya = ? WHERE email = ?";
        try (PreparedStatement stmt = connexio.prepareStatement(query)) {
            stmt.setString(1, contrasenyaHasheada);
            stmt.setString(2, email);
            stmt.executeUpdate(); // Actualitza el password de l'usuari
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
    public void crearUsuari(String email, String contrasenya, String intentsFallits, String area, String cap, String rol, String comentaris) throws SQLException {
        // Hashear la contrasenya i afegir el salt
        String contrasenyaHasheada = BCrypt.hashpw(contrasenya, BCrypt.gensalt());
        String consulta = "INSERT INTO usuaris (email, contrasenya, intents_fallits, area, cap, rol, comentaris, data_alta) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        int intentsFallitsNum = Integer.parseInt(intentsFallits);
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, email);
            sentencia.setString(2, contrasenyaHasheada);
            sentencia.setInt(3, intentsFallitsNum);
            sentencia.setString(4, area);
            sentencia.setString(5, cap);
            sentencia.setString(6, rol);
            sentencia.setString(7, comentaris);
            sentencia.executeUpdate(); // Executar la inserció de l'usuari
        }
    }

    // Mètode per obtenir tots els usuaris de la base de dades
    public List<Usuari> obtenirTotsElsUsuaris() throws SQLException {
        List<Usuari> usuaris = new ArrayList<>();
        String consulta = "SELECT email, contrasenya, area, cap, rol, intents_fallits, comentaris FROM usuaris";
        try (Statement sentencia = connexio.createStatement(); ResultSet resultats = sentencia.executeQuery(consulta)) {
            while (resultats.next()) {
                // Crear objectes Usuari i afegir-los a la llista
                String email = resultats.getString("email");
                String contrasenya = resultats.getString("contrasenya");
                String area = resultats.getString("area");
                String cap = resultats.getString("cap");
                String rol = resultats.getString("rol");
                int intentsFallits = resultats.getInt("intents_fallits");
                String comentaris = resultats.getString("comentaris");
                usuaris.add(new Usuari(email, contrasenya, area, cap, rol, intentsFallits, comentaris));
            }
        }
        return usuaris; // Retorna la llista d'usuaris
    }
    
    // Mètode per obtenir tots usuaris amb un rol específic
    public List<Usuari> obtenirUsuarisRol(String rol) throws SQLException {
        List<Usuari> usuaris = new ArrayList<>();
        String consulta = "SELECT email, contrasenya, area, cap, rol, intents_fallits, comentaris FROM usuaris where rol = ? ";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) { 
        		// Assignar el rol de l'usuari al paràmetre de la consulta
                sentencia.setString(1, rol);
        		ResultSet resultats = sentencia.executeQuery();
       // Recórrer els resultats i crear objectes Usuari			
        while (resultats.next()) {
                // Crear objectes Usuari i afegir-los a la llista
                String email = resultats.getString("email");
                String contrasenya = resultats.getString("contrasenya");
                String area = resultats.getString("area");
                String cap = resultats.getString("cap");
                int intentsFallits = resultats.getInt("intents_fallits");
                String comentaris = resultats.getString("comentaris");
                usuaris.add(new Usuari(email, contrasenya, area, cap, rol, intentsFallits, comentaris));
            }
        }
        return usuaris; // Retorna la llista d'usuaris
    }
    
    
    // Mètode per obtenir tots els usuaris d'un àrea específica
    public List<Usuari> obtenirUsuarisArea(String area) throws SQLException {
        List<Usuari> usuaris = new ArrayList<>();
        String consulta = "SELECT email, contrasenya, area, cap, rol, intents_fallits, comentaris FROM usuaris where area = ? ";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) { 
        		// Assignar el rol de l'usuari al paràmetre de la consulta
                sentencia.setString(1, area);
        		ResultSet resultats = sentencia.executeQuery();
       // Recórrer els resultats i crear objectes Usuari			
        while (resultats.next()) {
                // Crear objectes Usuari i afegir-los a la llista
                String email = resultats.getString("email");
                String contrasenya = resultats.getString("contrasenya");
                String cap = resultats.getString("cap");
                String rol = resultats.getString("rol");
                int intentsFallits = resultats.getInt("intents_fallits");
                String comentaris = resultats.getString("comentaris");
                usuaris.add(new Usuari(email, contrasenya, area, cap, rol, intentsFallits, comentaris));
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
                    return resultats.getString("rol"); // Retorna el rol de l'usuari escollit
                }
            }
        }
        return null; // Retorna null si no es troba cap usuari
    }
    
    // Mètode per obtenir el mail de tots els usuaris
    public List<String> getLlistatEmailUsuaris() {
        String consulta = "SELECT email FROM usuaris"; // Consulta SQL per obtenir l'email de tots els usuaris
        
        List<String> usuaris = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar l'email dels usuaris

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i afegim els emails a la llista
            while (resultats.next()) {
                String usuari = resultats.getString("email"); // Obtenir l'email de l'usuari
                usuaris.add(usuari); // Afegir a la llista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció adequadament
        }

        return usuaris; // Retornar la llista d'usuaris que són caps
    }
    
    // Mètode per obtenir l'email de tots els usuaris
    
    public List<String> getLlistatEmailCaps() {
        String consulta = "SELECT email FROM usuaris where es_cap = 'S'"; // Consulta SQL per obtenir l'email de tots els usuaris que són caps
        
        List<String> usuaris = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar l'email dels caps

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i afegim els emails a la llista
            while (resultats.next()) {
                String usuari = resultats.getString("email"); // Obtenir l'email del cap
                usuaris.add(usuari); // Afegir a la llista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció adequadament
        }

        return usuaris; // Retornar la llista d'usuaris que són caps
    }
    

    // Mètode per obtenir un usuari a partir del seu email
    public Usuari obtenirUsuariPerEmail(String email) throws SQLException {
        String consulta = "SELECT contrasenya, area, cap, rol, intents_fallits, comentaris FROM usuaris WHERE email = ?";
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
                    String comentaris = resultats.getString("comentaris");
                    return new Usuari(email, contrasenya, area, cap, rol, intentsFallits, comentaris);
                }
            }
        }
        return null; // Retorna null si no es troba l'usuari
    }
    
    

    // Mètode per modificar un usuari existent
    public void modificarUsuari(String email, String contrasenya, String area, String cap, String rol, String comentaris, int intentsFallits) throws SQLException {
    	 // Hashear la contrasenya i afegir el salt
        String contrasenyaHasheada = BCrypt.hashpw(contrasenya, BCrypt.gensalt());
        String consulta = "UPDATE usuaris SET intents_fallits = ?, area = ?, cap = ?, rol = ?, comentaris = ?, contrasenya = ? WHERE email = ?";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, intentsFallits);
            sentencia.setString(2, area);
            sentencia.setString(3, cap);
            sentencia.setString(4, rol);
            sentencia.setString(5, comentaris);
            sentencia.setString(6, contrasenyaHasheada);
            sentencia.setString(7, email);
            sentencia.executeUpdate(); // Executar l'actualització de l'usuari
        }
    }
    
    


    // Mètode per eliminar un usuari de la base de dades basant-se en el seu email
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
