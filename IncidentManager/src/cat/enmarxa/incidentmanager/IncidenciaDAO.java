package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private Connection connexio; // Connexió a la base de dades

    // Constructor que estableix la connexió a la base de dades
    public IncidenciaDAO() throws SQLException {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establir la connexió amb la base de dades utilitzant la URL, usuari i contrasenya
            String url = "jdbc:mysql://localhost:3306/gestio_incidencies";
            String user = "root"; 
            String password = "enmarxa"; 
            this.connexio = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Llançar una excepció si no es troba el controlador JDBC
            throw new SQLException("Controlador JDBC no trobat.");
        }
    }
    
    // Mètode per crear la taula 'Incidències' si no existeix encara
    public void crearTaulaIncidencies() {
        String consulta = "CREATE TABLE IF NOT EXISTS incidencies (" +
                "id_incidencia INT AUTO_INCREMENT PRIMARY KEY, " +
                "tipus ENUM('Incidència', 'Petició', 'Problema', 'Canvi') NOT NULL, " +
                "prioritat ENUM('Baixa', 'Normal', 'Alta', 'Crítica', 'Urgent') NOT NULL, " +
                "descripcio TEXT NOT NULL, " +
                "email_creador VARCHAR(50), " +
                "actiu1 VARCHAR(25), " +
                "actiu2 VARCHAR(25), " +
                "data_creacio DATE NOT NULL, " +
                "estat ENUM('Oberta', 'Treballant', 'Esperant', 'Escalada', 'Resolta', 'Tancada') DEFAULT NULL, " +
                "tecnic_assignat ENUM('tecnic1', 'tecnic2', 'tecnic3', 'tecnic4', 'tecnic5', 'tecnic6', 'tecnic7', 'tecnic8', 'tecnic9', 'tecnic10') DEFAULT 'tecnic1', " +
                "CONSTRAINT fk_usuari_incidencia FOREIGN KEY (email_creador) REFERENCES usuaris(email) ON DELETE CASCADE" +
                ");";  // Tancament de la consulta

        // Utilitzem try-with-resources per preparar i executar la consulta
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Executar la consulta per crear la taula
            sentencia.executeUpdate();
        } catch (SQLException e) {
            // Gestió d'excepcions SQL si hi ha un error
            e.printStackTrace();
        }
    }
    
    // Mètode per crear una nova incidència a la base de dades
    public void crearIncidencia(String tipus, String prioritat, String estat, String descripcio, String email_creador, String actiu1, String actiu2) throws SQLException {
        String consulta = "INSERT INTO incidencies (tipus, prioritat, estat, descripcio, email_creador, actiu1, actiu2, data_creacio) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Assignar els valors als paràmetres de la consulta
            sentencia.setString(1, tipus);
            sentencia.setString(2, prioritat);
            sentencia.setString(3, estat);
            sentencia.setString(4, descripcio);
            sentencia.setString(5, email_creador);
            sentencia.setString(6, actiu1);
            sentencia.setString(7, actiu2);

            // Executar la consulta per inserir la nova incidència
            sentencia.executeUpdate();
        }
    }
    
 // Mètode per obtenir els IDs de totes les incidències
    public List<String> getLlistaIncidenciesID() {
        String consulta = "SELECT id_incidencia FROM incidencies"; // Consulta SQL per obtenir els IDs de les incidències
        List<String> incidencies = new ArrayList<>(); // Inicialitzem la llista per emmagatzemar els IDs

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i afegim els IDs a la llista
            while (resultats.next()) {
                int idIncidencia = resultats.getInt("id_incidencia"); // Obtenir l'ID de la incidència
                String idIncidenciaText = String.valueOf(idIncidencia);
                incidencies.add(idIncidenciaText); // Afegir-lo a la llista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció
        }

        return incidencies; // Retornar la llista de IDs
    }


    // Mètode per obtenir totes les incidències d'un usuari específic
    public List<Incidencia> obtenirIncidenciesUsuari(String email_creador) throws SQLException {
        String consulta = "SELECT * FROM incidencies WHERE email_creador = ?";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
        	// Assignar l'usuari de la incidència al paràmetre de la consulta
            sentencia.setString(1, email_creador);
            ResultSet resultats = sentencia.executeQuery();

            // Recórrer els resultats i crear objectes Incidencia
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencia.setTecnicAssignat(resultats.getString("tecnic_assignat"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista d'incidències de l'usuari
    }
    
    // Mètode per obtenir totes les incidències en un estat específic
    public List<Incidencia> obtenirIncidenciesEstat(String estat) throws SQLException {
        String consulta = "SELECT * FROM incidencies WHERE estat = ?";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Assignar l'estat de la incidència al paràmetre de la consulta
            sentencia.setString(1, estat);
            ResultSet resultats = sentencia.executeQuery();

            // Recórrer els resultats i crear objectes Incidencia
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencia.setEmailCreador(resultats.getString("email_creador"));
                incidencia.setTecnicAssignat(resultats.getString("tecnic_assignat"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista d'incidències en aquest estat
    }
    
    // Mètode per obtenir totes les incidències amb una prioritat específica
    public List<Incidencia> obtenirIncidenciesPrioritat(String prioritat) throws SQLException {
        String consulta = "SELECT * FROM incidencies WHERE prioritat = ?";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
        	// Assignar la prioritat de la incidència al paràmetre de la consulta
            sentencia.setString(1, prioritat);
            ResultSet resultats = sentencia.executeQuery();

            // Recórrer els resultats i crear objectes Incidencia
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencia.setEmailCreador(resultats.getString("email_creador"));
                incidencia.setTecnicAssignat(resultats.getString("tecnic_assignat"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista d'incidències en aquesta prioritat
    }
    
 // Mètode per obtenir totes les incidències d'un tipus específic
    public List<Incidencia> obtenirIncidenciesTipus(String tipus) throws SQLException {
        String consulta = "SELECT * FROM incidencies WHERE tipus = ?";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
        	// Assignar el tipus de la incidència al paràmetre de la consulta
            sentencia.setString(1, tipus);
            ResultSet resultats = sentencia.executeQuery();

            // Recórrer els resultats i crear objectes Incidencia
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencia.setEmailCreador(resultats.getString("email_creador"));
                incidencia.setTecnicAssignat(resultats.getString("tecnic_assignat"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista d'incidències d'aquest tipus
    }


    // Mètode per obtenir totes les incidències creades per tots els usuaris
    public List<Incidencia> obtenirTotesLesIncidencies() throws SQLException {
        String consulta = "SELECT * FROM incidencies";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recórrer els resultats i crear objectes Incidencia
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setEstat(resultats.getString("estat"));
                incidencia.setActiu1(resultats.getString("actiu1"));
                incidencia.setActiu2(resultats.getString("actiu2"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencia.setEmailCreador(resultats.getString("email_creador"));
                incidencia.setTecnicAssignat(resultats.getString("tecnic_assignat"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista de totes les incidències
    }

    // Mètode per llistar totes les incidències creades
    public List<Incidencia> listarIncidencies() throws SQLException {
        List<Incidencia> incidencies = new ArrayList<>();
        String sql = "SELECT id_incidencia, tipus, prioritat, descripcio, id_usuari, actiu1, actiu2, data_creacio, email_creador, tecnic_assignat FROM incidencies";
        
        try (PreparedStatement stmt = connexio.prepareStatement(sql);
             ResultSet resultats = stmt.executeQuery()) {

            // Recórrer els resultats i crear objectes Incidencia
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setActiu1(resultats.getString("actiu1"));
                incidencia.setActiu2(resultats.getString("actiu2"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencia.setEmailCreador(resultats.getString("email_creador"));
                incidencia.setTecnicAssignat(resultats.getString("tecnic_assignat"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista d'incidències
    }

    // Mètode per eliminar una incidència basant-se en el seu ID
    public void eliminarIncidencia(int id_incidencia) throws SQLException {
        String consulta = "DELETE FROM incidencies WHERE id_incidencia = ?"; // Consulta per eliminar un actiu

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, id_incidencia); // Estableix el nom a la consulta
            sentencia.executeUpdate(); // Executem la consulta per eliminar l'incidència
        }
    }
    
    // Mètode per modificar una nova incidència existent
    public void modificarIncidencia(int id_incidencia, String tipus, String prioritat, String descripcio, String actiu1, String actiu2, String estat, String tecnic_assignat) throws SQLException {
        
        String consulta = "UPDATE incidencies SET tipus = ?, prioritat = ?, descripcio = ?, actiu1 = ?, actiu2 = ?, estat = ?, tecnic_assignat = ? WHERE id_incidencia = ?"; // Consulta per actualitzar un actiu
        
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Assignar els valors als paràmetres de la consulta
            sentencia.setString(1, tipus);
            sentencia.setString(2, prioritat);
            sentencia.setString(3, descripcio);
            sentencia.setString(4, actiu1);
            sentencia.setString(5, actiu2);
            sentencia.setString(6, estat);
            sentencia.setString(7, tecnic_assignat);
            sentencia.setInt(8, id_incidencia);

            // Executar l'actualització
            int rowsAffected = sentencia.executeUpdate(); // Obtenir el nombre de files afectades
            if (rowsAffected > 0) {
                System.out.println("Incidència actualitzada correctament."); // Confirmar l'actualització
            } else {
                System.out.println("No es va trobar la incidència amb l'ID proporcionat."); // Indicar que no es va trobar la incidència
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció aquí
            throw e; //
        }
        }
    
    // Mètode per tancar la connexió a la base de dades
    public void tancarConnexio() {
        if (connexio != null) {
            try {
                // Tancar la connexió si està oberta
                connexio.close();
            } catch (SQLException e) {
                // Gestió de l'excepció si hi ha un error en tancar la connexió
                e.printStackTrace();
            }
        }
    }
}
