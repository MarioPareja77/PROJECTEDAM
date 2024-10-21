package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiuDAO {

    private Connection connexio; // Connexió a la base de dades

    // Constructor que estableix la connexió a la base de dades
    public ActiuDAO() throws SQLException {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establir la connexió
            String url = "jdbc:mysql://localhost:3306/gestio_incidencies"; // Canvia el nom de la base de dades si és necessari
            String user = "root"; 
            String password = "enmarxa"; 
            this.connexio = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Controlador JDBC no trobat.");
        }
    }
    
    public List<String> obtenirNomTotsElsActius() {
        String consulta = "SELECT nom FROM actius";
        
        List<String> actius = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorremos los resultados y añadimos los nombres a la lista
            while (resultats.next()) {
                String actiu = resultats.getString("nom"); // Obtener el nombre del activo
                actius.add(actiu); // Añadir a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción adecuadamente
        }

        return actius; // Retornar la llista d'actius
    }
    
   // Mètode per crear la taula 'Actius' (únicament si no existia encara)
    public void crearTaulaActius() {
        String consulta = "CREATE TABLE IF NOT EXISTS actius ("
                + "id_actiu INT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(25) NOT NULL, "
                + "tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL, "
                + "area VARCHAR(20) NOT NULL, "
                + "marca VARCHAR(25) NOT NULL, "
                + "data_alta DATE NOT NULL, "
                + "descripcio VARCHAR(255) NOT NULL"
                + ");";  // Cierre de la consulta con el paréntesis y el punto y coma
        // Ús de try-with-resources per preparar i executar la consulta
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Executar la consulta
            sentencia.executeUpdate();
        } catch (SQLException e) {
        }
    }

    // Mètode per crear un nou actiu
    public int crearActiu(int id, String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) throws SQLException {
        String consulta = "INSERT INTO actius (id, nom, tipus, area, marca, data_alta, descripcio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idActiu = -1;

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Assignem els valors als paràmetres de la consulta
            sentencia.setLong(1, id);
            sentencia.setString(2, nom);
            sentencia.setString(3, tipus);
            sentencia.setString(4, area);
            sentencia.setString(5, marca);
            sentencia.setDate(6, new java.sql.Date(dataAlta.getTime())); // Convertir java.util.Date a java.sql.Date
            sentencia.setString(7, descripcio);

            // Executem la consulta per inserir el nou actiu
            sentencia.executeUpdate();

            // Obtenim l'ID del nou actiu creat
            ResultSet clausGenerades = sentencia.getGeneratedKeys();
            if (clausGenerades.next()) {
                idActiu = clausGenerades.getInt(1);
            }
        }

        return idActiu; // Retornar l'ID del nou actiu
    }

    // Mètode per obtenir tots els actius
    public List<Actiu> obtenirTotsElsActius() throws SQLException {
        String consulta = "SELECT * FROM actius";
        List<Actiu> actius = new ArrayList<>();

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu();
                actiu.setId(resultats.getInt("id_actiu"));
                actiu.setNom(resultats.getString("nom"));
                actiu.setTipus(resultats.getString("tipus"));
                actiu.setArea(resultats.getString("area"));
                actiu.setMarca(resultats.getString("marca"));
                actiu.setDataAlta(resultats.getDate("data_alta")); // Suposant que Actiu té un mètode setDataAlta
                actiu.setDescripcio(resultats.getString("descripcio")); // Suposant que Actiu té un mètode setDescripcio
                actius.add(actiu);
            }
        }

        return actius; // Retornar la llista d'actius
        
    }

    // Mètode per obtenir un actiu pel seu ID
    public Actiu obtenirActiuPerId(int id) throws SQLException {
        String consulta = "SELECT * FROM actius WHERE id = ?";
        Actiu actiu = null;

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, id);
            ResultSet resultats = sentencia.executeQuery();

            // Si trobem l'actiu, el creem
            if (resultats.next()) {
                actiu = new Actiu();
                actiu.setId(resultats.getInt(id));
                actiu.setNom(resultats.getString("nom"));
                actiu.setTipus(resultats.getString("tipus"));
                actiu.setArea(resultats.getString("area"));
                actiu.setMarca(resultats.getString("marca"));
                actiu.setDataAlta(resultats.getDate("data_alta")); // Suposant que Actiu té un mètode setDataAlta
                actiu.setDescripcio(resultats.getString("descripcio")); // Suposant que Actiu té un mètode setDescripcio
            }
        }

        return actiu; // Retornar l'actiu trobat o null
    }

    // Método para actualizar un activo
    public void actualitzarActiu(int idActiu, String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) throws SQLException {
        String consulta = "UPDATE actius SET nom = ?, tipus = ?, area = ?, marca = ?, data_alta = ?, descripcio = ? WHERE id_actiu = ?";

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Asignar valores a la consulta
            sentencia.setString(1, nom);
            sentencia.setString(2, tipus);
            sentencia.setString(3, area);
            sentencia.setString(4, marca);
            sentencia.setDate(5, new java.sql.Date(dataAlta.getTime())); // Convertir java.util.Date a java.sql.Date
            sentencia.setString(6, descripcio);
            sentencia.setInt(7, idActiu); // Asignar el id del activo

            // Ejecutar la actualización
            int rowsAffected = sentencia.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Activo actualizado correctamente.");
            } else {
                System.out.println("No se encontró el activo con el ID proporcionado.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            throw e; // Re-lanzar la excepción si es necesario
        }
    }
    
    // Mètode per eliminar un actiu
    public void eliminarActiu(int id) throws SQLException {
        String consulta = "DELETE FROM actius WHERE id = ?";

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate(); // Executem la consulta per eliminar l'actiu
        }
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
