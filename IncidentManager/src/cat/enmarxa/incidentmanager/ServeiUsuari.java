package cat.enmarxa.incidentmanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServeiUsuari {

    private UsuariDAO usuariDAO; // Objeto DAO para gestionar la base de datos

    // Método Constructor
    public ServeiUsuari() {
        try {
            this.usuariDAO = new UsuariDAO(); // Inicializamos el DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones en la inicialización
        }
    }

    // Método para crear un nuevo usuario
    public void crearUsuari(String email, String contrasenya, String area, String cap, String rol) {
        try {
            // Delegar la operación al DAO
            usuariDAO.crearUsuari(email, contrasenya, area, cap, rol);
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            // Puedes agregar un mensaje de error personalizado si es necesario.
        }
    }

    // Método para obtener el listado de todos los usuarios
    public List<Usuari> obtenirTotsElsUsuaris() {
        try {
            // Delegar la operación al DAO
            return usuariDAO.obtenirTotsElsUsuaris(); // Método que obtiene todos los usuarios de la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
        }
    }

    // Método para obtener un usuario específico por su email
    public Usuari obtenirUsuariPerEmail(String email) {
        try {
            return usuariDAO.obtenirUsuariPerEmail(email); // Obtener un usuario por su email
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return null; // Devolver null en caso de error
        }
    }

    // Método para actualizar un usuario
    public void actualitzarUsuari(String email, String contrasenya, String area, String cap, String rol) {
        try {
            usuariDAO.actualitzarUsuari(email, contrasenya, area, cap, rol); // Método para actualizar un usuario
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
        }
    }
    
    // Método per obtenir el rol d'un usuari
    public String obtenirRolUsuari(String email) {
        try {
            return usuariDAO.obtenirRolUsuari(email); 
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
            return null;
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuari(String email) {
        try {
            usuariDAO.eliminarUsuari(email); // Método para eliminar un usuario de la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción aquí
        }
    }
}
