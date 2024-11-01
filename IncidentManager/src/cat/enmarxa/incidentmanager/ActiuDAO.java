package cat.enmarxa.incidentmanager;

import java.sql.Connection; // Importa la classe Connection per gestionar connexions a la base de dades
import java.sql.PreparedStatement; // Importa PreparedStatement per executar consultes SQL
import java.sql.ResultSet; // Importa ResultSet per emmagatzemar els resultats d'una consulta SQL
import java.sql.SQLException; // Importa SQLException per gestionar errors relacionats amb SQL
import java.sql.DriverManager; // Importa DriverManager per establir connexions amb la base de dades
import java.util.ArrayList; // Importa ArrayList per utilitzar una llista dinàmica
import java.util.List; // Importa List per utilitzar tipus de dades de llista

/**
 * La classe ActiuDAO és un DAO (Data Access Object) encarregat de contactar directament amb la taula 'actius' de la BD (MySQL) i es trucada des de ServeiActiu. Aquesta classe pertany a la capa de persistència o 'Model' dins del patró de disseny MVC.
 */
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
            this.connexio = DriverManager.getConnection(url, user, password); // Estableix la connexió amb la base de dades
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Imprimir l'error si el controlador no es troba
            throw new SQLException("Controlador JDBC no trobat."); // Llençar excepció si el controlador no es pot trobar
        }
    }
    
    
   // Mètode per crear la taula 'Actius' (únicament si no existia encara)
    public void crearTaulaActius() {
        String consulta = "CREATE TABLE IF NOT EXISTS actius (" // Consulta SQL per crear la taula si no existeix
                + "nom VARCHAR(25) NOT NULL, "
                + "tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL, "
                + "area VARCHAR(20) NOT NULL, "
                + "marca VARCHAR(25) NOT NULL, "
                + "descripcio VARCHAR(255)"
                + "data_alta DATE NOT NULL, "
                + ");"; // Tancament de la consulta amb el parèntesi i el punt i coma
        
        // Ús de try-with-resources per preparar i executar la consulta
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Executar la consulta
            sentencia.executeUpdate(); // Executar la creació de la taula
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir error si ocorre
        }
    }

    // Mètode per crear un nou actiu
    public void crearActiu(String nom, String tipus, String area, String marca, String descripcio, java.util.Date dataAlta) throws SQLException {
        String consulta = "INSERT INTO actius (nom, tipus, area, marca, descripcio, data_alta) VALUES (?, ?, ?, ?, ?, NOW())"; // Consulta per inserir un nou actiu

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Assignem els valors als paràmetres de la consulta
            sentencia.setString(1, nom); // Estableix el nom
            sentencia.setString(2, tipus); // Estableix el tipus
            sentencia.setString(3, area); // Estableix l'àrea
            sentencia.setString(4, marca); // Estableix la marca
            sentencia.setString(5, descripcio); // Estableix la descripció

            // Executem la consulta per inserir el nou actiu
            sentencia.executeUpdate();
        }
    }

    // Mètode per obtenir tots els actius
    public List<Actiu> llistarActius() throws SQLException {
        String consulta = "SELECT * FROM actius"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actius.add(actiu); // Afegir l'actiu a la llista
            }
        }

        return actius; // Retornar la llista d'actius
    }
    
 // Mètode per obtenir el noms de tots els actius
    public List<String> getLlistaActius() {
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

    // Mètode per obtenir un actiu pel seu nom
    public Actiu obtenirActiuPerNom(String nom) throws SQLException {
        String consulta = "SELECT * FROM actius WHERE nom = ?"; // Consulta per obtenir un actiu pel seu nom
        Actiu actiu = null; // Inicialitzar l'actiu a null

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            ResultSet resultats = sentencia.executeQuery(); // Executar la consulta

            // Si trobem l'actiu, definim la resposta que donarem (objecte Actiu)
            if (resultats.next()) {
                actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
            }
        }

        return actiu; // Retornar l'actiu trobat o null
    }
    
    

    // Mètode per obtenir tots els actius d'un àrea concreta
    public List<Actiu> obtenirActiusArea(String area) throws SQLException {
        String consulta = "SELECT * FROM actius where area = ?"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
        		sentencia.setString(1, area);
        		ResultSet resultats = sentencia.executeQuery();
            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actius.add(actiu); // Afegir l'actiu a la llista
            }
        }

        return actius; // Retornar la llista d'actius
    }
    
    // Mètode per obtenir tots els actius d'una marca específica
    public List<Actiu> obtenirActiusMarca(String marca) throws SQLException {
        String consulta = "SELECT * FROM actius where marca = ?"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
        		sentencia.setString(1, marca);
        		ResultSet resultats = sentencia.executeQuery();
            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actius.add(actiu); // Afegir l'actiu a la llista
            }
        }

        return actius; // Retornar la llista d'actius
    }
    
    
    // Mètode per obtenir tots els actius d'un tipus específic
    public List<Actiu> obtenirActiusTipus(String tipus) throws SQLException {
        String consulta = "SELECT * FROM actius where tipus = ?"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
        		sentencia.setString(1, tipus);
        		ResultSet resultats = sentencia.executeQuery();
            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu(); // Crear un nou objecte Actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actius.add(actiu); // Afegir l'actiu a la llista
            }
        }

        return actius; // Retornar la llista d'actius
    }

    // Mètode per modificar un actiu
    public void modificarActiu(String nom, String tipus, String area, String marca, String descripcio) throws SQLException {
        String consulta = "UPDATE actius SET tipus = ?, area = ?, marca = ?, descripcio = ? WHERE nom = ?"; // Consulta per actualitzar un actiu

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Assignar valors a la consulta
            sentencia.setString(1, tipus); // Estableix el tipus
            sentencia.setString(2, area); // Estableix l'àrea
            sentencia.setString(3, marca); // Estableix la marca
            sentencia.setString(4, descripcio); // Estableix la descripció
            sentencia.setString(5, nom); // Estableix el nom

            // Executar l'actualització
            int rowsAffected = sentencia.executeUpdate(); // Obtenir el nombre de files afectades
            if (rowsAffected > 0) {
                System.out.println("Actiu actualitzat correctament."); // Confirmar l'actualització
            } else {
                System.out.println("No es va trobar l'actiu amb el nom proporcionat."); // Indicar que no es va trobar l'actiu
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció aquí
            throw e; // Re-lançar l'excepció si és necessari
        }
    }
    
    // Mètode per eliminar un actiu basant-se en el seu nom
    public void eliminarActiu(String nom) throws SQLException {
        String consulta = "DELETE FROM actius WHERE nom = ?"; // Consulta per eliminar un actiu

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, nom); // Estableix el nom a la consulta
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
