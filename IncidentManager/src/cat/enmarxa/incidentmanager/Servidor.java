package cat.enmarxa.incidentmanager;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    // Mapa para almacenar el ID de la sesión y el usuario (email)
    private static Map<String, String> sessionsActives = new HashMap<>();
	//private static Map<String, String> sessionsActives = new ConcurrentHashMap<>();
    private static List<Socket> socketsActius = new ArrayList<>(); // Lista de sockets activos
    
    public static void main(String[] args) {
        final int port = 12345;

        try (FileWriter fw = new FileWriter("servidor.log", true);
             PrintWriter logWriter = new PrintWriter(fw);
             ServerSocket servidor = new ServerSocket(port)) {
             
            System.out.println("Servidor escuchando en el puerto " + port);
            logWriter.println("Servidor escuchando en el puerto " + port);
            logWriter.flush(); // Asegúrate de que se escriba en el archivo

            while (true) {
                // Aceptar conexiones de los clientes
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());
                logWriter.println("Cliente conectado desde " + socket.getInetAddress());
                logWriter.flush();

                // Crear hilo para aceptar múltiples clientes
                new Thread(new FilClient(socket, logWriter)).start();
            }
        } catch (BindException e) {
            System.err.println("Error: El puerto " + port + " ya está en uso. Finalizando programa.");
            System.exit(1); // Finaliza el programa con un código de error
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para cerrar todas las conexiones con el servidor
    public static void TancarConnexioServidor() {
        synchronized (socketsActius) {
            for (Socket socket : socketsActius) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        System.out.println("Conexión cerrada: " + socket.getInetAddress());
                    }
                } catch (IOException e) {
                    System.err.println("Error cerrando la conexión: " + e.getMessage());
                }
            }
            socketsActius.clear(); // Limpiar la lista de sockets activos
        }
    }

    // Clase interna que maneja las conexiones de los clientes en un hilo
    static class FilClient implements Runnable {
        private Socket socketClient;
        private PrintWriter logWriter;
        private ServeiLogin serveiLogin;

        public FilClient(Socket socketClient, PrintWriter logWriter) {
            this.socketClient = socketClient;
            this.logWriter = logWriter;

            // Inicializar ServeiLogin
            try {
                this.serveiLogin = new ServeiLogin();
            } catch (SQLException e) {
                System.err.println("Error al inicializar ServeiLogin: " + e.getMessage());
                e.printStackTrace();
            }
        }

        
        @Override
        public void run() {
            // Añadir el socket a la lista de sockets activos
            synchronized (socketsActius) {
                socketsActius.add(socketClient);
            }

            try (
                DataInputStream entrada = new DataInputStream(socketClient.getInputStream());
                DataOutputStream sortida = new DataOutputStream(socketClient.getOutputStream());
            ) {
                // Leer las credenciales enviadas por el cliente
                String email = entrada.readUTF();
                String contrasenya = entrada.readUTF();

                // Comprobar autenticación
                if (serveiLogin.autenticar(email, contrasenya)) {
                    int comptadorIntents = serveiLogin.obtenirIntentsFallits(email);
                    if (comptadorIntents >= 5) {
                        // Cuenta bloqueada por múltiples intentos fallidos
                        System.out.println("La cuenta " + email + " está bloqueada por intentos fallidos.");
                        logWriter.println("La cuenta " + email + " está bloqueada por intentos fallidos.");
                        sortida.writeUTF("Compte bloquejat");
                    } else {
                        // Autenticación exitosa
                        String idSessio = UUID.randomUUID().toString();

                        // Sincronizar el acceso al mapa de sesiones activas
                        synchronized (sessionsActives) {
                            sessionsActives.put(idSessio, email); // Guardar sesión activa
                        }

                        System.out.println("Sesiones activas: " + sessionsActives);
                        sortida.writeUTF("Autenticación exitosa. ID de sesión: " + idSessio);
                        logWriter.println("Usuario autenticado: " + email + " | ID de sesión: " + idSessio);
                        
                        // Reiniciar el contador de intentos fallidos después de un login exitoso
                        serveiLogin.restablirIntentsFallits(email); // Asegúrate de tener este método en ServeiLogin
                    }
                } else {
                    serveiLogin.augmentarIntentsFallits(email);
                    int comptadorIntents = serveiLogin.obtenirIntentsFallits(email);
                    if (comptadorIntents < 5) {
                        // No se ha bloqueado la cuenta todavía
                        sortida.writeUTF(String.valueOf(comptadorIntents));
                        System.out.println("Intentos fallidos restantes: " + comptadorIntents);
                        logWriter.println("Autenticación fallida. Usuario o contraseña incorrectos para: " + email);
                    } else {
                        // Cuenta bloqueada
                        System.out.println("La cuenta " + email + " está bloqueada por intentos fallidos.");
                        logWriter.println("La cuenta " + email + " está bloqueada por intentos fallidos.");
                        sortida.writeUTF("Compte bloquejat");
                    }
                }

            } catch (IOException e) {
                System.err.println("Error durante la conexión con el cliente: " + e.getMessage());
                logWriter.println("Error durante la conexión con el cliente: " + e.getMessage());
            } finally {
                // Eliminar el socket de la lista de sockets activos y cerrar la conexión
                synchronized (socketsActius) {
                    socketsActius.remove(socketClient);
                }
                try {
                    socketClient.close();
                } catch (IOException e) {
                    System.err.println("Error cerrando el socket: " + e.getMessage());
                }
            }
        }
    }	
	  // Mètode per obtenir les sessions actives
    public Map<String, String> obtenirSessionsActives() {
        // Retornar el mapa de sessions actives
    	synchronized (sessionsActives) {
        	return sessionsActives;
    }
    	
    }
}
