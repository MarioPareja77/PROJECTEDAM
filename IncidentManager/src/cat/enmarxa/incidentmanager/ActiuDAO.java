package cat.enmarxa.incidentmanager;

import java.sql.Connection; // Importa la classe Connection per gestionar connexions a la base de dades
import java.sql.DriverManager; // Importa DriverManager per establir connexions amb la base de dades
import java.sql.PreparedStatement; // Importa PreparedStatement per executar consultes SQL
import java.sql.ResultSet; // Importa ResultSet per emmagatzemar els resultats d'una consulta SQL
import java.sql.SQLException; // Importa SQLException per gestionar errors relacionats amb SQL
import java.sql.Statement;
import java.util.ArrayList; // Importa ArrayList per utilitzar una llista dinàmica
import java.util.List; // Importa List per utilitzar tipus de dades de llista

public class ActiuDAO {

    private Connection connexio; // Connexió a la base de dades

    // Constructor que estableix la connexió a la base de dades
    public ActiuDAO() throws SQLException {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establir la connexió
            String url = "jdbc:mysql://localhost:3306/gestio_incidencies"; // Canvia el nom de la base de dades si és necessari
            String user = "Admin";
            String password = "enmarxa";
            this.connexio = DriverManager.getConnection(url, user, password); // Estableix la connexió amb la base de dades
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Imprimir l'error si el controlador no es troba
            throw new SQLException("Controlador JDBC no trobat."); // Llençar excepció si el controlador no es pot trobar
        }
    }

    // Mètode per obtenir els noms de tots els actius
    public List<String> obtenirNomTotsElsActius() {
        String consulta = "SELECT nom FROM actius"; // Consulta SQL per obtenir els noms dels actius

        List<String> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els noms

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i afegim els noms a la llista
            while (resultats.next()) {
                String actiu = resultats.getString("nom"); // Obtenir el nom de l'actiu
                actius.add(actiu); // Afegir a la llista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció adequadament
        }

        return actius; // Retornar la llista d'actius
    }

   // Mètode per crear la taula 'Actius' (únicament si no existia encara)
    public void crearTaulaActius() {
        String consulta = "CREATE TABLE IF NOT EXISTS actius (" // Consulta SQL per crear la taula si no existeix
                + "id_actiu INT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(25) NOT NULL, "
                + "tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL, "
                + "area VARCHAR(20) NOT NULL, "
                + "marca VARCHAR(25) NOT NULL, "
                + "data_alta DATE NOT NULL, "
                + "descripcio VARCHAR(255) NOT NULL"
                + ");"; // Cierre de la consulta amb el parèntesi i el punt i coma

        // Ús de try-with-resources per preparar i executar la consulta
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Executar la consulta
            sentencia.executeUpdate(); // Executar la creació de la taula
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir error si ocorre
        }
    }

    // Mètode per crear un nou actiu
    public int crearActiu(int id, String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) throws SQLException {
        String consulta = "INSERT INTO actius (id_actiu, nom, tipus, area, marca, data_alta, descripcio) VALUES (?, ?, ?, ?, ?, ?, ?)"; // Consulta per inserir un nou actiu
        int idActiu = -1; // Inicialitzar l'ID de l'actiu a -1

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
            // Assignem els valors als paràmetres de la consulta
            sentencia.setLong(1, id); // Estableix l'ID
            sentencia.setString(2, nom); // Estableix el nom
            sentencia.setString(3, tipus); // Estableix el tipus
            sentencia.setString(4, area); // Estableix l'àrea
            sentencia.setString(5, marca); // Estableix la marca
            sentencia.setDate(6, new java.sql.Date(dataAlta.getTime())); // Convertir java.util.Date a java.sql.Date
            sentencia.setString(7, descripcio); // Estableix la descripció

            // Executem la consulta per inserir el nou actiu
            sentencia.executeUpdate();

            // Obtenim l'ID del nou actiu creat
            ResultSet clausGenerades = sentencia.getGeneratedKeys(); // Obtenim les claus generades
            if (clausGenerades.next()) {
                idActiu = clausGenerades.getInt(1); // Assignar l'ID generat
            }
        }

        return idActiu; // Retornar l'ID del nou actiu
    }

    // Mètode per obtenir tots els actius
    public List<Actiu> obtenirTotsElsActius() throws SQLException {
        String consulta = "SELECT * FROM actius"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setId(resultats.getInt("id_actiu")); // Estableix l'ID de l'actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actius.add(actiu); // Afegir l'actiu a la llista
            }
        }

        return actius; // Retornar la llista d'actius
    }

    // Mètode per obtenir un actiu pel seu ID
    public Actiu obtenirActiuPerId(int id) throws SQLException {
        String consulta = "SELECT * FROM actius WHERE id_actiu = ?"; // Consulta per obtenir un actiu per ID
        Actiu actiu = null; // Inicialitzar l'actiu a null

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, id); // Estableix l'ID a la consulta
            ResultSet resultats = sentencia.executeQuery(); // Executar la consulta

            // Si trobem l'actiu, el creem
            if (resultats.next()) {
                actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setId(resultats.getInt("id_actiu")); // Estableix l'ID de l'actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
            }
        }

        return actiu; // Retornar l'actiu trobat o null
    }

    // Mètode per actualitzar un actiu
    public void actualitzarActiu(int idActiu, String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) throws SQLException {
        String consulta = "UPDATE actius SET nom = ?, tipus = ?, area = ?, marca = ?, data_alta = ?, descripcio = ? WHERE id_actiu = ?"; // Consulta per actualitzar un actiu

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Assignar valors a la consulta
            sentencia.setString(1, nom); // Estableix el nom
            sentencia.setString(2, tipus); // Estableix el tipus
            sentencia.setString(3, area); // Estableix l'àrea
            sentencia.setString(4, marca); // Estableix la marca
            sentencia.setDate(5, new java.sql.Date(dataAlta.getTime())); // Convertir java.util.Date a java.sql.Date
            sentencia.setString(6, descripcio); // Estableix la descripció
            sentencia.setInt(7, idActiu); // Estableix l'ID de l'actiu

            // Executar l'actualització
            int rowsAffected = sentencia.executeUpdate(); // Obtenir el nombre de files afectades
            if (rowsAffected > 0) {
                System.out.println("Actiu actualitzat correctament."); // Confirmar l'actualització
            } else {
                System.out.println("No es va trobar l'actiu amb l'ID proporcionat."); // Indicar que no es va trobar l'actiu
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció aquí
            throw e; // Re-lançar l'excepció si és necessari
        }
    }

    // Mètode per eliminar un actiu
    public void eliminarActiu(int id) throws SQLException {
        String consulta = "DELETE FROM actius WHERE id_actiu = ?"; // Consulta per eliminar un actiu

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, id); // Estableix l'ID a la consulta
            sentencia.executeUpdate(); // Executem la consulta per eliminar l'actiu
        }
    }

    // Mètode per tancar la connexió
    public void tancarConnexio() {
        if (connexio != null) {
            try {
                connexio.close(); // Tancar la connexió
            } catch (SQLException e) {
                e.printStackTrace(); // Gestionar errors en tancar la connexió
            }
        }
    }
}
