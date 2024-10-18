package cat.enmarxa.incidentmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServeiIncidencia {

    // Mètode per crear una nova incidència
    public void crearIncidencia(String descripcio, String tipus, String prioritat) throws SQLException {
        String consulta = "INSERT INTO incidencies (tipus, prioritat, descripcio, id_usuari, data_creacio) VALUES (?, ?, ?, ?, NOW())";

        try (Connection connexio = ConnexioBaseDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Assignem els valors als paràmetres de la consulta
            sentencia.setString(1, tipus);
            sentencia.setString(2, prioritat);
            sentencia.setString(3, descripcio);
            sentencia.setString(4, "dummy");

            // Executem la consulta per inserir la nova incidència
            sentencia.executeUpdate();

            // Obtenim l'ID de la nova incidència creada
            ResultSet clausGenerades = sentencia.getGeneratedKeys();
            if (clausGenerades.next()) {
                int idIncidencia = clausGenerades.getInt(1);

                // Associem els actius a la nova incidència
                associarActius(idIncidencia, actius);
            }
        }
    }

    // Mètode per associar actius a una incidència
    private void associarActius(int idIncidencia, List<Integer> actius) throws SQLException {
        String consulta = "INSERT INTO incidencies_actius (id_incidencia, id_actiu) VALUES (?, ?)";

        try (Connection connexio = ConnexioBaseDeDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta)) {

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

        try (Connection connexio = ConnexioBaseDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta)) {

            sentencia.setString(1, idUsuari);
            ResultSet resultats = sentencia.executeQuery();

            // Recorrem els resultats i creem les incidències
            while (resultats.next()) {
            }
        }

        return incidencies;
    }

    // Mètode per obtenir totes les incidències creades per tots els usuaris
    public List<Incidencia> obtenirTotesLesIncidencies() throws SQLException {
        String consulta = "SELECT * FROM incidencies";
        List<Incidencia> incidencies = new ArrayList<>();

        try (Connection connexio = ConnexioBaseDeDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta)) {

            ResultSet resultats = sentencia.executeQuery();

            // Recorrem els resultats i creem les incidències
            while (resultats.next()) {
                Incidencia incidencia = new Incidencia();
                incidencia.setIdIncidencia(resultats.getInt("id_incidencia"));
                incidencia.setTipus(resultats.getString("tipus"));
                incidencia.setPrioritat(resultats.getString("prioritat"));
                incidencia.setDescripcio(resultats.getString("descripcio"));
                incidencia.setDataCreacio(resultats.getDate("data_creacio"));
                incidencies.add(incidencia);
            }
        }

     // Mètode per llistar totes les incidències creades de totes els usuaris
        public List<Incidencia> listarIncidencies() {
            List<Incidencia> incidencies = new ArrayList<>();
            String sql = "SELECT id_incidencia, tipus, prioritat, descripcio, id_usuari, data_creacio FROM incidencies";
            ResultSet rs;
    		try {
    			PreparedStatement stmt = conn.prepareStatement(sql);
    			    rs = stmt.executeQuery();
    		} catch (SQLException e) {
    			//
    			e.printStackTrace();
    		}
                while (rs.next()) {
                    // Crear objete Incidencia i afegir-lo a la llista
                    String descripcio = rs.getString("descripcio");
                    String tipus = rs.getString("tipus");
                    String prioritat = rs.getString("prioritat");
                    Incidencia incidencia = new Incidencia(descripcio, tipus, prioritat);
                    incidencies.add(incidencia);
                }
            return incidencies; // Retornar la llista d'incidències
        }
        return incidencies;
    }

	public static cat.enmarxa.incidentmanager.List<Incidencia> llistarIncidencies() {
		// TODO Auto-generated method stub
		return null;
	}

}
