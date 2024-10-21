package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServeiActiu {

    private ActiuDAO actiuDAO; // Objeto DAO para gestionar la base de datos

    // Método Constructor
    public ServeiActiu() {
        try {
            this.actiuDAO = new ActiuDAO(); // Inicializamos el DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones en la inicialización
        }
    }

    // Método para crear un nuevo activo
    public boolean crearActiu(String nom, String tipus, String area, String marca, java.util.Date dataAlta, String descripcio) {
        try {
            // Delegar la operación al DAO
        	int id = -1;
            actiuDAO.crearActiu(id, nom, tipus, area, marca, dataAlta, descripcio);
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            // Puedes agregar un mensaje de error personalizado si es necesario.
        }
        return false;
    }
    

    // Método para obtener el listado de todos los activos
    public List<Actiu> obtenirTotsElsActius() {
        try {
            // Delegar la operación al DAO
            return actiuDAO.obtenirTotsElsActius(); // Método que obtiene todos los activos de la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }
    
    // Método para obtener el listado de todos los activos (solo el nombre)
    public List<String> obtenirNomTotsElsActius() {
        // Delegar la operación al DAO
		return actiuDAO.obtenirNomTotsElsActius(); // Método que obtiene todos los activos de la base de datos
    }

    // Método para obtener un activo específico por su ID
    public Actiu obtenirActiuPerId(int idActiu) {
        try {
            return actiuDAO.obtenirActiuPerId(idActiu); // Obtener un activo por su ID
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return null; // Devolver null en caso de error
        }
    }

 // Método para actualizar un activo
    public void actualitzarActiu(int idActiu, String nom, String tipus, String area, String marca, Date dataAlta, String descripcio) {
        try {
            // Asegúrate de que el método 'actualitzarActiu' del DAO recibe los parámetros correctos
            actiuDAO.actualitzarActiu(idActiu, nom, tipus, area, marca, dataAlta, descripcio); // Método para actualizar un activo
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
        }
    }

    // Método para eliminar un activo
    public void eliminarActiu(int idActiu) {
        try {
            actiuDAO.eliminarActiu(idActiu); // Método para eliminar un activo de la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
        }
    }
}
