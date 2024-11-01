package cat.enmarxa.incidentmanager;

import java.sql.Connection; // Importa la classe Connection per gestionar connexions a la base de dades
<<<<<<< HEAD
import java.sql.PreparedStatement; // Importa PreparedStatement per executar consultes SQL
import java.sql.ResultSet; // Importa ResultSet per emmagatzemar els resultats d'una consulta SQL
import java.sql.SQLException; // Importa SQLException per gestionar errors relacionats amb SQL
import java.sql.DriverManager; // Importa DriverManager per establir connexions amb la base de dades
=======
import java.sql.DriverManager; // Importa DriverManager per establir connexions amb la base de dades
import java.sql.PreparedStatement; // Importa PreparedStatement per executar consultes SQL
import java.sql.ResultSet; // Importa ResultSet per emmagatzemar els resultats d'una consulta SQL
import java.sql.SQLException; // Importa SQLException per gestionar errors relacionats amb SQL
import java.sql.Statement;
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
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
<<<<<<< HEAD
            String user = "root"; 
            String password = "enmarxa"; 
=======
            String user = "root";
            String password = "enmarxa";
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            this.connexio = DriverManager.getConnection(url, user, password); // Estableix la connexió amb la base de dades
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Imprimir l'error si el controlador no es troba
            throw new SQLException("Controlador JDBC no trobat."); // Llençar excepció si el controlador no es pot trobar
        }
    }
<<<<<<< HEAD
    
    
=======

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

>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
   // Mètode per crear la taula 'Actius' (únicament si no existia encara)
    public void crearTaulaActius() {
        String consulta = "CREATE TABLE IF NOT EXISTS actius (" // Consulta SQL per crear la taula si no existeix
<<<<<<< HEAD
=======
                + "id_actiu INT AUTO_INCREMENT PRIMARY KEY, "
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
                + "nom VARCHAR(25) NOT NULL, "
                + "tipus ENUM('servidor', 'PC', 'pantalla', 'portàtil', 'switch', 'router', 'cablejat', 'ratolí', 'teclat', 'rack', 'software') NOT NULL, "
                + "area VARCHAR(20) NOT NULL, "
                + "marca VARCHAR(25) NOT NULL, "
                + "descripcio VARCHAR(255)"
                + "data_alta DATE NOT NULL, "
<<<<<<< HEAD
                + ");"; // Tancament de la consulta amb el parèntesi i el punt i coma
        
=======
                + "descripcio VARCHAR(255) NOT NULL"
                + ");"; // Cierre de la consulta amb el parèntesi i el punt i coma

>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        // Ús de try-with-resources per preparar i executar la consulta
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Executar la consulta
            sentencia.executeUpdate(); // Executar la creació de la taula
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir error si ocorre
        }
    }

    // Mètode per crear un nou actiu
<<<<<<< HEAD
    public void crearActiu(String nom, String tipus, String area, String marca, String descripcio, java.util.Date dataAlta) throws SQLException {
        String consulta = "INSERT INTO actius (nom, tipus, area, marca, descripcio, data_alta) VALUES (?, ?, ?, ?, ?, NOW())"; // Consulta per inserir un nou actiu
=======
    public int crearActiu(int id, String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) throws SQLException {
        String consulta = "INSERT INTO actius (id_actiu, nom, tipus, area, marca, data_alta, descripcio) VALUES (?, ?, ?, ?, ?, ?, ?)"; // Consulta per inserir un nou actiu
        int idActiu = -1; // Inicialitzar l'ID de l'actiu a -1
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
            // Assignem els valors als paràmetres de la consulta
<<<<<<< HEAD
            sentencia.setString(1, nom); // Estableix el nom
            sentencia.setString(2, tipus); // Estableix el tipus
            sentencia.setString(3, area); // Estableix l'àrea
            sentencia.setString(4, marca); // Estableix la marca
            sentencia.setString(5, descripcio); // Estableix la descripció
=======
            sentencia.setLong(1, id); // Estableix l'ID
            sentencia.setString(2, nom); // Estableix el nom
            sentencia.setString(3, tipus); // Estableix el tipus
            sentencia.setString(4, area); // Estableix l'àrea
            sentencia.setString(5, marca); // Estableix la marca
            sentencia.setDate(6, new java.sql.Date(dataAlta.getTime())); // Convertir java.util.Date a java.sql.Date
            sentencia.setString(7, descripcio); // Estableix la descripció
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

            // Executem la consulta per inserir el nou actiu
            sentencia.executeUpdate();
<<<<<<< HEAD
=======

            // Obtenim l'ID del nou actiu creat
            ResultSet clausGenerades = sentencia.getGeneratedKeys(); // Obtenim les claus generades
            if (clausGenerades.next()) {
                idActiu = clausGenerades.getInt(1); // Assignar l'ID generat
            }
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }
    }

    // Mètode per obtenir tots els actius
<<<<<<< HEAD
    public List<Actiu> llistarActius() throws SQLException {
=======
    public List<Actiu> obtenirTotsElsActius() throws SQLException {
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        String consulta = "SELECT * FROM actius"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i creem els actius
            while (resultats.next()) {
                Actiu actiu = new Actiu(); // Crear un nou objecte Actiu
<<<<<<< HEAD
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
=======
                actiu.setId(resultats.getInt("id_actiu")); // Estableix l'ID de l'actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
                actius.add(actiu); // Afegir l'actiu a la llista
            }
        }

        return actius; // Retornar la llista d'actius
<<<<<<< HEAD
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
=======
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }

<<<<<<< HEAD
    // Mètode per obtenir un actiu pel seu nom
    public Actiu obtenirActiuPerNom(String nom) throws SQLException {
        String consulta = "SELECT * FROM actius WHERE nom = ?"; // Consulta per obtenir un actiu pel seu nom
=======
    // Mètode per obtenir un actiu pel seu ID
    public Actiu obtenirActiuPerId(int id) throws SQLException {
        String consulta = "SELECT * FROM actius WHERE id_actiu = ?"; // Consulta per obtenir un actiu per ID
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        Actiu actiu = null; // Inicialitzar l'actiu a null

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
<<<<<<< HEAD
=======
            sentencia.setInt(1, id); // Estableix l'ID a la consulta
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            ResultSet resultats = sentencia.executeQuery(); // Executar la consulta

            // Si trobem l'actiu, definim la resposta que donarem (objecte Actiu)
            if (resultats.next()) {
                actiu = new Actiu(); // Crear un nou objecte Actiu
<<<<<<< HEAD
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
=======
                actiu.setId(resultats.getInt("id_actiu")); // Estableix l'ID de l'actiu
                actiu.setNom(resultats.getString("nom")); // Estableix el nom de l'actiu
                actiu.setTipus(resultats.getString("tipus")); // Estableix el tipus de l'actiu
                actiu.setArea(resultats.getString("area")); // Estableix l'àrea de l'actiu
                actiu.setMarca(resultats.getString("marca")); // Estableix la marca de l'actiu
                actiu.setDataAlta(resultats.getDate("data_alta")); // Estableix la data d'alta
                actiu.setDescripcio(resultats.getString("descripcio")); // Estableix la descripció
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            }
        }

        return actiu; // Retornar l'actiu trobat o null
    }
    
    

<<<<<<< HEAD
    // Mètode per obtenir tots els actius d'un àrea concreta
    public List<Actiu> obtenirActiusArea(String area) throws SQLException {
        String consulta = "SELECT * FROM actius where area = ?"; // Consulta per obtenir tots els actius
        List<Actiu> actius = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els actius
=======
    // Mètode per actualitzar un actiu
    public void actualitzarActiu(int idActiu, String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) throws SQLException {
        String consulta = "UPDATE actius SET nom = ?, tipus = ?, area = ?, marca = ?, data_alta = ?, descripcio = ? WHERE id_actiu = ?"; // Consulta per actualitzar un actiu
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
<<<<<<< HEAD
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
=======
            // Assignar valors a la consulta
            sentencia.setString(1, nom); // Estableix el nom
            sentencia.setString(2, tipus); // Estableix el tipus
            sentencia.setString(3, area); // Estableix l'àrea
            sentencia.setString(4, marca); // Estableix la marca
            sentencia.setDate(5, new java.sql.Date(dataAlta.getTime())); // Convertir java.util.Date a java.sql.Date
            sentencia.setString(6, descripcio); // Estableix la descripció
            sentencia.setInt(7, idActiu); // Estableix l'ID de l'actiu
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

<<<<<<< HEAD
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

=======
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            // Executar l'actualització
            int rowsAffected = sentencia.executeUpdate(); // Obtenir el nombre de files afectades
            if (rowsAffected > 0) {
                System.out.println("Actiu actualitzat correctament."); // Confirmar l'actualització
            } else {
<<<<<<< HEAD
                System.out.println("No es va trobar l'actiu amb el nom proporcionat."); // Indicar que no es va trobar l'actiu
=======
                System.out.println("No es va trobar l'actiu amb l'ID proporcionat."); // Indicar que no es va trobar l'actiu
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestionar l'excepció aquí
            throw e; // Re-lançar l'excepció si és necessari
        }
    }
<<<<<<< HEAD
    
    // Mètode per eliminar un actiu basant-se en el seu nom
    public void eliminarActiu(String nom) throws SQLException {
        String consulta = "DELETE FROM actius WHERE nom = ?"; // Consulta per eliminar un actiu
=======

    // Mètode per eliminar un actiu
    public void eliminarActiu(int id) throws SQLException {
        String consulta = "DELETE FROM actius WHERE id_actiu = ?"; // Consulta per eliminar un actiu
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
<<<<<<< HEAD
            sentencia.setString(1, nom); // Estableix el nom a la consulta
=======
            sentencia.setInt(1, id); // Estableix l'ID a la consulta
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
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
