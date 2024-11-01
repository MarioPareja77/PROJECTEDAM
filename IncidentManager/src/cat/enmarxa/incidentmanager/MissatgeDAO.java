package cat.enmarxa.incidentmanager;

import java.sql.Connection; // Importa la classe Connection per gestionar connexions a la base de dades
import java.sql.PreparedStatement; // Importa PreparedStatement per executar consultes SQL
import java.sql.ResultSet; // Importa ResultSet per emmagatzemar els resultats d'una consulta SQL
import java.sql.SQLException; // Importa SQLException per gestionar errors relacionats amb SQL
import java.sql.DriverManager; // Importa DriverManager per establir connexions amb la base de dades
import java.util.ArrayList; // Importa ArrayList per utilitzar una llista dinàmica
import java.util.List; // Importa List per utilitzar tipus de dades de llista

/**
 * La classe MissatgeDAO és un DAO (Data Access Object) encarregat de contactar directament amb la taula 'missatges' de la BD (MySQL) i es trucada des de ServeiMissatge. Aquesta classe pertany a la capa de persistència o 'Model' dins del patró MVC.
 */
public class MissatgeDAO {

    private Connection connexio; // Connexió a la base de dades

    // Constructor que estableix la connexió a la base de dades
    public MissatgeDAO() throws SQLException {
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

    // Mètode per crear la taula 'Missatges' (únicament si no existia encara)
    public void crearTaulaMissatges() {
        String consulta = "CREATE TABLE IF NOT EXISTS Missatges (" // Consulta SQL per crear la taula si no existeix
                + "ID_missatge INT PRIMARY KEY AUTO_INCREMENT, " // Clau primària
                + "email_de VARCHAR(50) NOT NULL, " // Correu electrònic del remitent
                + "email_per VARCHAR(50) NOT NULL, " // Correu electrònic del destinatari
                + "data_creacio DATE, " // Data de creació del missatge
                + "missatge TEXT" // Contingut del missatge
                + ");"; // Tancament de la consulta amb el parèntesi i el punt i coma

        // Aquí es pot afegir el codi per executar la consulta, per exemple:
        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            // Executar la consulta
            sentencia.executeUpdate(); // Executar la creació de la taula
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir error si ocorre
        }
    }

    // Mètode per crear un nou missatge
    public void crearMissatge(String emailDe, String emailPer, String missatge) throws SQLException {
        String consulta = "INSERT INTO Missatges (email_de, email_per, data_creacio, missatge) VALUES (?, ?, NOW(), ?)"; // Consulta per inserir un nou missatge

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Assignem els valors als paràmetres de la consulta
            sentencia.setString(1, emailDe); // Estableix el correu electrònic del remitent
            sentencia.setString(2, emailPer); // Estableix el correu electrònic del destinatari
            sentencia.setString(3, missatge); // Estableix el contingut del missatge

            // Executem la consulta per inserir el nou missatge
            sentencia.executeUpdate();
        }
    }

    // Mètode per obtenir tots els missatges
    public List<Missatge> llistarMissatges() throws SQLException {
        String consulta = "SELECT * FROM Missatges"; // Consulta per obtenir tots els missatges
        List<Missatge> missatges = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els missatges

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i creem els missatges
            while (resultats.next()) {
                Missatge missatge = new Missatge(); // Crear un nou objecte Missatge
                missatge.setId(resultats.getInt("ID_missatge")); // Estableix l'identificador del missatge
                missatge.setEmailDe(resultats.getString("email_de")); // Estableix el correu electrònic del remitent
                missatge.setEmailPer(resultats.getString("email_per")); // Estableix el correu electrònic del destinatari
                missatge.setDataCreacio(resultats.getDate("data_creacio")); // Estableix la data de creació
                missatge.setContingut(resultats.getString("missatge")); // Estableix el contingut del missatge
                missatges.add(missatge); // Afegir el missatge a la llista
            }
        }

        return missatges; // Retornar la llista de missatges
    }

    // Mètode per obtenir tots els IDs dels missatges
    public List<Integer> obtenirIdsMissatges() throws SQLException {
        String consulta = "SELECT ID_missatge FROM Missatges"; // Consulta per obtenir els IDs dels missatges
        List<Integer> ids = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els IDs

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta);
             ResultSet resultats = sentencia.executeQuery()) {

            // Recorrem els resultats i afegim els IDs a la llista
            while (resultats.next()) {
                ids.add(resultats.getInt("ID_missatge")); // Afegir ID a la llista
            }
        }

        return ids; // Retornar la llista d'IDs
    }

    // Mètode per obtenir tots els missatges d'un correu electrònic (remitent) concret
    public List<Missatge> obtenirMissatgesEmailDe(String emailDe) throws SQLException {
        String consulta = "SELECT * FROM Missatges WHERE email_per = ?"; // Consulta per obtenir tots els missatges d'un remitent concret
        List<Missatge> missatges = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els missatges

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, emailDe); // Assignem el correu electrònic al paràmetre de la consulta
            ResultSet resultats = sentencia.executeQuery(); // Executem la consulta

            // Recorrem els resultats i creem els missatges
            while (resultats.next()) {
                Missatge missatge = new Missatge(); // Crear un nou objecte Missatge
                missatge.setId(resultats.getInt("ID_missatge")); // Estableix el correu electrònic del remitent
                missatge.setEmailDe(resultats.getString("email_de")); // Estableix el correu electrònic del remitent
                missatge.setDataCreacio(resultats.getDate("data_creacio")); // Estableix la data de creació
                missatge.setContingut(resultats.getString("missatge")); // Estableix el contingut del missatge
                missatges.add(missatge); // Afegir el missatge a la llista
            }
        }

        return missatges; // Retornar la llista de missatges
    }

    // Mètode per obtenir tots els missatges d'un correu electrònic (destinatari) concret
    public List<Missatge> obtenirMissatgesEmailPer(String emailPer) throws SQLException {
        String consulta = "SELECT * FROM Missatges WHERE email_de = ?"; // Consulta per obtenir tots els missatges d'un destinatari concret
        List<Missatge> missatges = new ArrayList<>(); // Inicialitzar la llista per emmagatzemar els missatges

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, emailPer); // Assignem el correu electrònic al paràmetre de la consulta
            ResultSet resultats = sentencia.executeQuery(); // Executem la consulta

            // Recorrem els resultats i creem els missatges
            while (resultats.next()) {
                Missatge missatge = new Missatge(); // Crear un nou objecte Missatge
                missatge.setId(resultats.getInt("ID_missatge")); // Estableix el correu electrònic del remitent
                missatge.setEmailPer(resultats.getString("email_per")); // Estableix el correu electrònic del destinatari
                missatge.setDataCreacio(resultats.getDate("data_creacio")); // Estableix la data de creació
                missatge.setContingut(resultats.getString("missatge")); // Estableix el contingut del missatge
                missatges.add(missatge); // Afegir el missatge a la llista
            }
        }

        return missatges; // Retornar la llista de missatges
    }

    // Mètode per eliminar un missatge basant-se en el seu ID
    public void eliminarMissatge(int idMissatge) throws SQLException {
        String consulta = "DELETE FROM Missatges WHERE ID_missatge = ?"; // Consulta per eliminar un missatge

        try (PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setInt(1, idMissatge); // Estableix l'ID del missatge a la consulta
            sentencia.executeUpdate(); // Executem la consulta per eliminar el missatge
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

