package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private Connection connexio; // Conexión a la base de datos

    // Constructor que establece la conexión a la base de datos
    public IncidenciaDAO() throws SQLException {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            String url = "jdbc:mysql://localhost:3306/gestio_incidencies";
            String user = "root"; 
            String password = "enmarxa"; 
            this.connexio = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Controlador JDBC no trobat.");
        }
    }
    
    // Mètode per crear la taula 'Incidencies' (únicament si no existia encara)
    public void crearTaulaIncidencies() {
    	 String consulta = "CREATE TABLE IF NOT EXISTS incidencies ("
                 + "id_incidencia INT AUTO_INCREMENT PRIMARY KEY, "
                 + "tipus ENUM('Incidència', 'Petició', 'Problema', 'Canvi') NOT NULL, "
                 + "prioritat ENUM('Baixa', 'Normal', 'Alta', 'Crítica', 'Urgent') NOT NULL, "
                 + "descripcio TEXT NOT NULL, "
                 + "email_creador VARCHAR(50), "
                 + "data_creacio DATE NOT NULL, "
                 + "CONSTRAINT fk_usuari_incidencia FOREIGN KEY (email_creador) REFERENCES usuaris(email) ON DELETE CASCADE"
                 + ");";  // Cierre de la consulta con el paréntesis y el punto y coma

         // Uso de try-with-resources para preparar y ejecutar la consulta
         try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
             // Ejecutar la consulta
             sentencia.executeUpdate();
         } catch (SQLException e) {
             // Manejo de la excepción SQL
         }
     }
    
    // Mètode per crear una nova incidència
    public void crearIncidencia(String tipus, String prioritat, String descripcio, String usuari) throws SQLException {
    	
        String consulta = "INSERT INTO incidencies (tipus, prioritat, descripcio, email_creador, data_creacio) VALUES (?, ?, ?, ?, NOW())";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Assignem els valors als paràmetres de la consulta
            sentencia.setString(1, tipus);
            sentencia.setString(2, prioritat);
            sentencia.setString(3, descripcio);
            sentencia.setString(4, usuari);

            // Executem la consulta per inserir la nova incidència
            sentencia.executeUpdate();
          }
      }


    // Mètode per associar actius a una incidència
    public void associarActius(int idIncidencia, List<Integer> actius) throws SQLException {
        String consulta = "INSERT INTO incidencies_actius (id_incidencia, id_actiu) VALUES (?, ?)";
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            for (int idActiu : actius) {
                sentencia.setInt(1, idIncidencia);
                sentencia.setInt(2, idActiu);
                sentencia.addBatch();  // Afegim al batch per executar totes les associacions alhora
            }

            sentencia.executeBatch();  // Executem el batch
        }
    }

    // Mètode per obtenir el llistat d'incidències d'un usuari específic
    public List<Incidencia> obtenirIncidenciesPerUsuari(String idUsuari) throws SQLException {
        String consulta = "SELECT * FROM incidencies WHERE id_usuari = ?";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, idUsuari);
            ResultSet resultats = sentencia.executeQuery();

            // Recorrem els resultats i creem les incidències
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencies.add(incidencia);
            }
        }

        return incidencies;
    }

    // Mètode per obtenir totes les incidències creades per tots els usuaris
    public List<Incidencia> obtenirTotesLesIncidencies() throws SQLException {
        String consulta = "SELECT * FROM incidencies";
        List<Incidencia> incidencies = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i creem les incidències
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencies.add(incidencia);
            }
        }

        return incidencies;
    }

    // Mètode per llistar totes les incidències creades de totes els usuaris
    public List<Incidencia> listarIncidencies() throws SQLException {
        List<Incidencia> incidencies = new ArrayList<>();
        String sql = "SELECT id_incidencia, tipus, prioritat, descripcio, id_usuari, data_creacio FROM incidencies";
        
        try (PreparedStatement stmt = connexio.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setId(rs.getInt("id_incidencia"));
                incidencia.setTipus(rs.getString("tipus"));
                incidencia.setPrioritat(rs.getString("prioritat"));
                incidencia.setDescripcio(rs.getString("descripcio"));
                incidencia.setDataCreacio(rs.getDate("data_creacio"));
                incidencies.add(incidencia);
            }
        }

        return incidencies; // Retornar la llista d'incidències
    }

    // Mètode per tancar la connexió
    public void tancarConnexio() {
        if (connexio != null) {
            try {
                connexio.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
